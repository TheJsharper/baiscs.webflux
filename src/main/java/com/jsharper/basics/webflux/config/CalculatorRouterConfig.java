package com.jsharper.basics.webflux.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CalculatorRouterConfig {

	@Autowired
	private CalculatorHandler calculatorHandler;

	@Bean
	public RouterFunction<ServerResponse> baseCalculatorLevelRouter() {
		return RouterFunctions.route().path("fn-calc", this::serverResponseRouterfn).build();
	}

	private RouterFunction<ServerResponse> serverResponseRouterfn() {

		return RouterFunctions.route().GET("{a}/{b}", isOperation("+"), calculatorHandler::additionHandler)
				.GET("{a}/{b}", isOperation("-"), calculatorHandler::substractionHandler)
				.GET("{a}/{b}", isOperation("/"), calculatorHandler::divisionHandler)
				.GET("{a}/{b}", isOperation("*"), calculatorHandler::multiplicationHandler)
				.GET("{a}/{b}", req -> ServerResponse.badRequest().bodyValue("OP should be + - * /"))

				.build();
	}

	private RequestPredicate isOperation(String operation) {
		return RequestPredicates
				.headers((headers) -> operation.equals(headers.asHttpHeaders().toSingleValueMap().get("OP")));
	}

}
