package com.jsharper.basics.webflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsharper.basics.webflux.dto.Response;
import com.jsharper.basics.webflux.service.ReactiveMathService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathController {

	@Autowired
	private ReactiveMathService mathService;

	@GetMapping("square/{input}")
	public Mono<Response> findSquare(@PathVariable int input) {

		return this.mathService.findSquare(input);
	}

	@GetMapping("table/{input}")
	public Flux<Response> multiplicationTable(@PathVariable int input) {

		return this.mathService.multiplicationTable(input);
	}

	@GetMapping(value = "table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Response> multiplicationTableStream(@PathVariable int input) {

		return this.mathService.multiplicationTable(input);
	}
	@GetMapping(value = "table/{input}/stream-list", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Response> multiplicationTableListStream(@PathVariable int input) {
		
		return this.mathService.multiplicationTableList(input);
	}

}
