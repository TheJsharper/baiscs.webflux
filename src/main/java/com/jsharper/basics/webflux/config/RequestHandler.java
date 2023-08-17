package com.jsharper.basics.webflux.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.jsharper.basics.webflux.dto.MultiplyRequestDto;
import com.jsharper.basics.webflux.dto.Response;
import com.jsharper.basics.webflux.exceptions.InputValidationException;
import com.jsharper.basics.webflux.service.ReactiveMathService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RequestHandler {

	@Autowired
	private ReactiveMathService reactiveMathService;

	public Mono<ServerResponse> squareHandler(ServerRequest serverRequest) {
		Integer input = Integer.valueOf(serverRequest.pathVariable("input"));
		Mono<Response> response = this.reactiveMathService.findSquare(input);

		return ServerResponse.ok().body(response, Response.class);

	}

	public Mono<ServerResponse> tableHandler(ServerRequest serverRequest) {
		Integer input = Integer.valueOf(serverRequest.pathVariable("input"));
		Flux<Response> response = this.reactiveMathService.multiplicationTable(input);

		return ServerResponse.ok().body(response, Response.class);

	}

	public Mono<ServerResponse> tableStreamHandler(ServerRequest serverRequest) {
		Integer input = Integer.valueOf(serverRequest.pathVariable("input"));
		Flux<Response> response = this.reactiveMathService.multiplicationTable(input);

		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(response, Response.class);

	}

	public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest) {
		Mono<MultiplyRequestDto> bodyToMono = serverRequest.bodyToMono(MultiplyRequestDto.class);
		Mono<Integer> dto = bodyToMono.map((p) -> p.getFirst() * p.getSecond());

		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(dto.map(Response::new),
				Response.class);

	}

	public Mono<ServerResponse> squareWithValidationHandler(ServerRequest serverRequest) {
		Integer input = Integer.valueOf(serverRequest.pathVariable("input"));

		if (input < 10 || input > 20) {
			//InputFailedValidationResponse response = new InputFailedValidationResponse();

			return   Mono.error(new InputValidationException(input));// ServerResponse.badRequest().bodyValue(response);
		}
		Mono<Response> response = this.reactiveMathService.findSquare(input);

		return ServerResponse.ok().body(response, Response.class);

	}

}
