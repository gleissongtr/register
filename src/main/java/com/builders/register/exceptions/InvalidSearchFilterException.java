package com.builders.register.exceptions;

public class InvalidSearchFilterException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidSearchFilterException(String msg) {
		super(msg);
	}
	
	public InvalidSearchFilterException(String msg, Throwable cause) {
		super(msg);
	}
}
