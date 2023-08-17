package com.jsharper.basics.webflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsharper.basics.webflux.dto.Response;
import com.jsharper.basics.webflux.exceptions.InputValidationException;
import com.jsharper.basics.webflux.service.ReactiveMathService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathValidationController {

	@Autowired
	private ReactiveMathService mathService;

	@GetMapping("square/{input}/throwable")
	public Mono<Response> findSquare(@PathVariable int input) {

		if (input < 10 || input > 20)
			throw new InputValidationException(input);

		return this.mathService.findSquare(input);
	}

	@GetMapping("square/{input}/mono-error")
	public Mono<Response> monoRrror(@PathVariable int input) {

		return Mono.just(input).handle((value, sink) -> {
			if (value >= 10 && value <= 20)
				sink.next(value);
			else
				sink.error(new InputValidationException(input));

		}).cast(Integer.class).flatMap(value -> this.mathService.findSquare(input));

	}
}
