package com.jsharper.basics.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class CalculatorTest extends BaseIntegrationTests {

	@Autowired
	private WebClient webClient;

	private static final String FORMAT = "%d %s %d = %s";
	private static final int A = 10;

	@Test
	public void calculatorTest() {

		Flux<String> result = Flux.range(1, 5).flatMap(b -> Flux.just("+", "-", "*", "/").flatMap(op -> calc(op, b)))
				.doOnNext(System.out::println);
		StepVerifier.create(result).expectNextCount(20).expectComplete().verify();

	}

	private Mono<String> calc(String op, int b) {
		return this.webClient.get().uri("fn-calc/{a}/{b}", A, b).headers(h -> h.set("OP", op)).retrieve()
				.bodyToMono(String.class).map(r -> String.format(FORMAT, A, op, b, r));
	}
}
