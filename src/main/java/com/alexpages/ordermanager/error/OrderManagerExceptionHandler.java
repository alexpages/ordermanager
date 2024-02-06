package com.alexpages.ordermanager.error;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class OrderManagerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(OrderManagerException404.class)
	public ResponseEntity<Object> handleExceptions(OrderManagerException404 e, WebRequest webRequest)
	{
		OrderManagerException exception = new OrderManagerException(e.getMessage());
		return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(OrderManagerException500.class)
	public ResponseEntity<Object> handleExceptions(OrderManagerException500 e, WebRequest webRequest)
	{
		OrderManagerException exception = new OrderManagerException(e.getMessage());
		return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(OrderManagerException409.class)
	public ResponseEntity<Object> handleExceptions(OrderManagerException409 e, WebRequest webRequest) 
	{
		OrderManagerException exception = new OrderManagerException(e.getMessage());
		return new ResponseEntity<>(exception, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(OrderManagerException400.class)
	public ResponseEntity<Object> handleExceptions(OrderManagerException400 e, WebRequest webRequest) 
	{
		OrderManagerException exception = new OrderManagerException(e.getMessage());
		return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
															   HttpStatus status, WebRequest request){
		OrderManagerException exception = new OrderManagerException("Validation failed for the request: [" + ex.getMessage() + "]");
		return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers,
																	  HttpStatus status, WebRequest request){
		OrderManagerException exception = new OrderManagerException("The specified request method is not supported: [" + ex.getMessage() + "]");
		return new ResponseEntity<>(exception, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
														HttpStatus status, WebRequest request){
		OrderManagerException exception = new OrderManagerException("Parameter type is not valid for this request: [" + ex.getMessage() + "]");
		return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
	}
}
