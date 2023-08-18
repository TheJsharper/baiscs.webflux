package com.jsharper.basics.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.jsharper.basics.webflux.dto.Response;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class BadRequestTest extends BaseIntegrationTests {

	@Autowired
	private WebClient webClient;

	@Test
	public void squareReactiveBadRequestByInput9Test() {
		Mono<Response> response = webClient.get().uri("reactive-math/square/{input}/throwable", 9).retrieve()
				.bodyToMono(Response.class).doOnNext(System.out::println).doOnError(System.err::println);

		StepVerifier.create(response).expectError(WebClientResponseException.BadRequest.class).verify();

	}

	@Test
	public void squareReactiveBadRequestByInput21Test() {
		Mono<Response> response = webClient.get().uri("reactive-math/square/{input}/throwable", 21).retrieve()
				.bodyToMono(Response.class).doOnNext(System.out::println).doOnError(System.err::println);

		StepVerifier.create(response).expectError(WebClientResponseException.BadRequest.class).verify();

	}

	@Test
	public void squareReactiveCanCalculaeInput20Test() {
		Mono<Response> response = webClient.get().uri("reactive-math/square/{input}/throwable", 20).retrieve()
				.bodyToMono(Response.class).doOnNext(System.out::println).doOnError(System.err::println);

		StepVerifier.create(response).expectNextMatches(actual -> actual.getOutput() == (20 * 20)).expectComplete()
				.verify();

	}

	@Test
	public void squareReactiveCanCalculaeInput10Test() {
		Mono<Response> response = webClient.get().uri("reactive-math/square/{input}/throwable", 10).retrieve()
				.bodyToMono(Response.class).doOnNext(System.out::println).doOnError(System.err::println);

		StepVerifier.create(response).expectNextMatches(actual -> actual.getOutput() == (10 * 10)).expectComplete()
				.verify();

	}
}
