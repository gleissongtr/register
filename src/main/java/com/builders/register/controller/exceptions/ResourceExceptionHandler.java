package com.builders.register.controller.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.builders.register.exceptions.InvalidSearchFilterException;
import com.builders.register.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		
		StandardError error = StandardError.builder()
				.status(HttpStatus.NOT_FOUND.value())
				.msg(e.getMessage())
				.timestamp(System.currentTimeMillis())
				.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(InvalidSearchFilterException.class)
	public ResponseEntity<StandardError> invalidSearchFilter(InvalidSearchFilterException e, HttpServletRequest request) {
		
		StandardError error = StandardError.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.msg(e.getMessage())
				.timestamp(System.currentTimeMillis())
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
}
