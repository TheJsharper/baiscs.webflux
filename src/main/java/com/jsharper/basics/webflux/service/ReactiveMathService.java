package com.jsharper.basics.webflux.service;

import org.springframework.stereotype.Service;

import com.jsharper.basics.webflux.dto.Response;
import com.jsharper.basics.webflux.utils.MathUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveMathService {

	public Mono<Response> findSquare(int input) {
		return Mono.fromSupplier(() -> input * input).map(Response::new);
	}

	public Flux<Response> multiplicationTable(int input) {
		return Flux.range(1, 10).doOnNext(i -> MathUtils.sleepCurr(1))
				.doOnNext(i -> System.out.println("Reactive-Math-Service processing" + i))
				.map(i -> new Response(i * input));
	}
}
