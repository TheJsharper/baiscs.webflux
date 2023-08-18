package com.jsharper.basics.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import com.jsharper.basics.webflux.dto.MultiplyRequestDto;
import com.jsharper.basics.webflux.dto.Response;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class PostSingleReponseTest extends BaseIntegrationTests {

	@Autowired
	private WebClient webClient;

	@Test
	public void postTest() {
		Mono<Response> response = this.webClient.post().uri("reactive-math/multiply").bodyValue(buildRequestDto(5, 2))
				.retrieve().bodyToMono(Response.class).doOnNext(System.out::println);

		StepVerifier.create(response).expectNextCount(1).verifyComplete();
	}

	@Test
	public void postMultiplyOutputTest() {
		Mono<Response> response = this.webClient.post().uri("reactive-math/multiply").bodyValue(buildRequestDto(5, 2))
				.retrieve().bodyToMono(Response.class).doOnNext(System.out::println);

		StepVerifier.create(response).expectNextMatches(actual -> actual.getOutput() == 5 * 2)

				.verifyComplete();
	}

	private MultiplyRequestDto buildRequestDto(int a, int b) {

		MultiplyRequestDto dto = new MultiplyRequestDto();
		dto.setFirst(a);
		dto.setSecond(b);
		return dto;
	}
}
