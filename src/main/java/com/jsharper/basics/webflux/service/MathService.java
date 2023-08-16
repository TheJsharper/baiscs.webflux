package com.jsharper.basics.webflux.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.jsharper.basics.webflux.dto.Response;
import com.jsharper.basics.webflux.utils.MathUtils;

@Service
public class MathService {

	
	public Response findSquare(int input) {
		
		return new Response(input*input);
	}
	
	public List<Response> multiplicationTable(int input) {
		return IntStream.rangeClosed(0, 10).peek(i -> MathUtils.sleepCurr(1)).mapToObj((i) -> new Response(i * input))
				.peek(i -> System.out.println("Math-Service proccessing: " + i)).collect(Collectors.toList());
	}

	
}
