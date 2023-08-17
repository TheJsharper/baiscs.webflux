package com.jsharper.basics.webflux.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationHandler {

	@ExceptionHandler(InputValidationException.class)
	public ResponseEntity<InputFailedValidationResponse> handlerException(InputValidationException ex) {
		InputFailedValidationResponse response = new InputFailedValidationResponse();
		response.setErrorCode(ex.getErrocode());
		response.setInput(ex.getInput());
		response.setMessage(ex.getMessage());
		return ResponseEntity.badRequest().body(response);
	}

}
