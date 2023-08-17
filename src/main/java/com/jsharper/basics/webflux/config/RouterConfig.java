package com.jsharper.basics.webflux.config;

import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.jsharper.basics.webflux.exceptions.InputFailedValidationResponse;
import com.jsharper.basics.webflux.exceptions.InputValidationException;

import reactor.core.publisher.Mono;

@Configuration
public class RouterConfig {

	@Autowired
	private RequestHandler requestHandler;

	@Bean
	public RouterFunction<ServerResponse> serverResponseRouterfn() {

		return RouterFunctions.route().GET("fn-router/square/{input}", requestHandler::squareHandler)
				.GET("fn-router/table/{input}", requestHandler::tableHandler)
				.GET("fn-router/table/{input}/stream", requestHandler::tableStreamHandler)
				.POST("fn-router/multiply", requestHandler::multiplyHandler)
				.GET("fn-router/square/{input}/throwable", requestHandler::squareWithValidationHandler)
				.onError(InputValidationException.class, exceptionHandler()).build();
	}

	private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
		return (err, req) -> {
			InputValidationException ex = (InputValidationException) err;
			InputFailedValidationResponse response = new InputFailedValidationResponse();
			response.setInput(ex.getInput());
			response.setMessage(ex.getMessage());
			response.setErrorCode(ex.getErrocode());
			System.out.println("===>" +response);
			return ServerResponse.badRequest().bodyValue(response);
		};
	}
}
