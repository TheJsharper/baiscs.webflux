package com.jsharper.basics.webflux;

import java.net.URI;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class QueryParamsTest extends BaseIntegrationTests {

	@Autowired
	private WebClient webClient;

	@Test
	public void queryParamsTest() {
		Flux<Integer> queryResponse = webClient.get().uri(this.getURI(10, 5)).retrieve().bodyToFlux(Integer.class)
				.doOnNext(System.out::println);

		StepVerifier.create(queryResponse).expectNextCount(2).expectComplete().verify();
	}

	@Test
	public void queryParamsWithSessionTokenTest() {

		WebClient localWebClient = WebClient.builder().baseUrl("http://localhost:8080").filter(this::sessionToken)
				.build();

		Flux<Integer> queryResponse = localWebClient.get().uri(this.getURI(10, 5)).retrieve().bodyToFlux(Integer.class)
				.doOnNext(System.out::println);

		StepVerifier.create(queryResponse).expectNextCount(2).expectComplete().verify();
	}

	@Test
	public void queryParamsWithOAuthTest() {

		WebClient localWebClient = WebClient.builder().baseUrl("http://localhost:8080").filter(this::sessionTokenOAUTH)
				.build();

		Flux<Integer> queryResponse = localWebClient.get().uri(this.getURI(10, 5)).retrieve().bodyToFlux(Integer.class)
				.doOnNext(System.out::println);

		StepVerifier.create(queryResponse).expectNextCount(2).expectComplete().verify();
	}

	private Function<UriBuilder, URI> getURI(int count, int page) {
		return (builder) -> builder.host("localhost").port(8080).path("jobs/search").queryParam("count", count)
				.queryParam("page", page).build();

	}

	private Mono<ClientResponse> sessionToken(ClientRequest req, ExchangeFunction ex) {

		ClientRequest clientRequest = ClientRequest.from(req)
				.headers(h -> h.setBasicAuth("some-username", "some-password")).build();

		return ex.exchange(clientRequest);

	}

	private Mono<ClientResponse> sessionTokenOAUTH(ClientRequest req, ExchangeFunction ex) {

		ClientRequest withBasics = ClientRequest.from(req).headers(h -> h.setBasicAuth("username", "password")).build();

		ClientRequest withOAuth = ClientRequest.from(req).headers(h -> h.setBearerAuth("some-bear-tokken")).build();

		ClientRequest clientRequest = req.attribute("auth").map(v -> v.equals("basic") ? withBasics : withOAuth)
				.orElse(req);

		return ex.exchange(clientRequest);

	}
}
