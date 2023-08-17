package com.jsharper.basics.webflux.exceptions;

public class InputValidationException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MSG ="allowed range is 10-20";
	private static final int erroCode = 100;
	private  final int input;
	public InputValidationException(final int input) {
		super(MSG);
		this.input = input;
	}
	public  int getErrocode() {
		return erroCode;
	}
	public int getInput() {
		return input;
	}
	
	

}
