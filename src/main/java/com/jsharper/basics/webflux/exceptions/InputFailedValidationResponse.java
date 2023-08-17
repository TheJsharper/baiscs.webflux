package com.jsharper.basics.webflux.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@Getter
@Setter
public class InputFailedValidationResponse {

	private int errorCode;
	private int input;
	private String message;
}
