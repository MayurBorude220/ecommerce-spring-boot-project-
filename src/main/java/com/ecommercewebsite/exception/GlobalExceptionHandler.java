package com.ecommercewebsite.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	/* if id not found inservice controller */
	@ExceptionHandler(ResourseNotFoundException.class)
	public String HandlerResourceNotFoundException(ResourseNotFoundException ecxe)
	{
		return ecxe.getMessage();
	}
}
