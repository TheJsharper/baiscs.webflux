package com.jsharper.basics.webflux;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import com.jsharper.basics.webflux.dto.Response;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class GetSingleResponseTest extends BaseIntegrationTests {

	@Autowired
	private WebClient webClient;

	@Test
	public void squareReactiveBlock() {

		Response response = this.webClient.get().uri("reactive-math/square/{input}", 5).retrieve()
				.bodyToMono(Response.class).block();

		Assertions.assertEquals(response.getOutput(), 25);

	}

	@Test
	public void squareReactiveTest() {

		Mono<Response> output = this.webClient.get().uri("reactive-math/square/{input}", 5).retrieve()
				.bodyToMono(Response.class);

		StepVerifier.create(output).expectNextMatches(actual -> actual.getOutput() == 25).expectComplete().verify();

	}

	@Test
	public void squareReactiveFluxWith10NextTest() {

		Flux<Response> output = this.webClient.get().uri("reactive-math/table/{input}", 5).retrieve()
				.bodyToFlux(Response.class);

		StepVerifier.create(output).expectSubscription().expectNextMatches(actual -> actual.getOutput() == 5)
				.expectNextMatches(actual -> actual.getOutput() == 10)
				.expectNextMatches(actual -> actual.getOutput() == 15)
				.expectNextMatches(actual -> actual.getOutput() == 20)
				.expectNextMatches(actual -> actual.getOutput() == 25)
				.expectNextMatches(actual -> actual.getOutput() == 30)
				.expectNextMatches(actual -> actual.getOutput() == 35)
				.expectNextMatches(actual -> actual.getOutput() == 40)
				.expectNextMatches(actual -> actual.getOutput() == 45)
				.expectNextMatches(actual -> actual.getOutput() == 50).thenCancel().verify();
	}

	@Test
	public void squareReactiveFluxMultiplicationTest() {

		Flux<Response> output = this.webClient.get().uri("reactive-math/table/{input}/stream", 5).retrieve()
				.bodyToFlux(Response.class);

		StepVerifier.create(output).expectSubscription().expectNextCount(10).thenCancel().verify();

	}

	@Test
	public void squareReactiveFluxMultiplicationOutputTest() {

		Flux<Response> output = this.webClient.get().uri("reactive-math/table/{input}/stream", 5).retrieve()
				.bodyToFlux(Response.class);

		StepVerifier.create(output).expectSubscription().expectNextMatches(actual -> actual.getOutput() == 5)
				.expectNextMatches(actual -> actual.getOutput() == 10)
				.expectNextMatches(actual -> actual.getOutput() == 15)
				.expectNextMatches(actual -> actual.getOutput() == 20)
				.expectNextMatches(actual -> actual.getOutput() == 25)
				.expectNextMatches(actual -> actual.getOutput() == 30)
				.expectNextMatches(actual -> actual.getOutput() == 35)
				.expectNextMatches(actual -> actual.getOutput() == 40)
				.expectNextMatches(actual -> actual.getOutput() == 45)
				.expectNextMatches(actual -> actual.getOutput() == 50).expectComplete().verify();

	}

	@Test
	public void squareReactiveFluxMultiplicationOutputTest2() {

		Flux<Response> output = this.webClient.get().uri("reactive-math/table/{input}/stream-list", 5).retrieve()
				.bodyToFlux(Response.class);

		StepVerifier.create(output).expectSubscription()
		.expectNextMatches(actual -> actual.getOutput() == 0)
		.expectNextMatches(actual -> actual.getOutput() == 5)
				.expectNextMatches(actual -> actual.getOutput() == 10)
				.expectNextMatches(actual -> actual.getOutput() == 15)
				.expectNextMatches(actual -> actual.getOutput() == 20)
				.expectNextMatches(actual -> actual.getOutput() == 25)
				.expectNextMatches(actual -> actual.getOutput() == 30)
				.expectNextMatches(actual -> actual.getOutput() == 35)
				.expectNextMatches(actual -> actual.getOutput() == 40)
				.expectNextMatches(actual -> actual.getOutput() == 45)
				.expectNextMatches(actual -> actual.getOutput() == 50).expectComplete().verify();

	}
}
