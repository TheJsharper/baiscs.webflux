package com.jsharper.basics.webflux.service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
		return Flux.range(1, 10)
				// .doOnNext(i -> MathUtils.sleepCurr(1))
				.delayElements(Duration.ofSeconds(2))
				.doOnNext(i -> System.out.println("Reactive-Math-Service processing" + i))
				.map(i -> new Response(i * input));
	}

	public Flux<Response> multiplicationTableList(int input) {

		List<Response> list = IntStream.rangeClosed(0, 10).peek(i -> MathUtils.sleepCurr(1))
				.mapToObj((i) -> new Response(i * input))
				.peek(i -> System.out.println("Math-Service proccessing: " + i)).collect(Collectors.toList());

		return Flux.fromIterable(list);
	}
}
