package com.jsharper.basics.webflux.config;

import java.util.function.BiFunction;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Service
public class CalculatorHandler {

	public Mono<ServerResponse> additionHandler(ServerRequest req) {
		return process(req, (a, b) -> ServerResponse.ok().bodyValue(a + b));
	}

	public Mono<ServerResponse> substractionHandler(ServerRequest req) {
		return process(req, (a, b) -> ServerResponse.ok().bodyValue(a - b));
	}

	public Mono<ServerResponse> multiplicationHandler(ServerRequest req) {
		return process(req, (a, b) -> ServerResponse.ok().bodyValue(a * b));
	}

	public Mono<ServerResponse> divisionHandler(ServerRequest req) {
		return process(req, (a, b) -> {
			return b != 0 ? ServerResponse.ok().bodyValue(a / b)
					: ServerResponse.badRequest().bodyValue("b can NOT be 0");
		});
	}

	private Mono<ServerResponse> process(ServerRequest req,
			BiFunction<Integer, Integer, Mono<ServerResponse>> opLogic) {

		int a = getValue(req, "a");
		int b = getValue(req, "b");

		return opLogic.apply(a, b);
	}

	private int getValue(ServerRequest req, String key) {
		return Integer.parseInt(req.pathVariable(key));
	}
}
