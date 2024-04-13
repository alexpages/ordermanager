package com.alexpages.ordermanager.error;

import java.time.LocalDateTime;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class OrderManagerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleExceptions(AccessDeniedException e, WebRequest webRequest)
	{
		OrderManagerException exception = new OrderManagerException(e.getMessage());
        exception.setStatus(HttpStatus.UNAUTHORIZED);
        exception.setTimestamp(LocalDateTime.now());
        exception.setThrowable(e.getCause());
		return new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(OrderManagerException404.class)
	public ResponseEntity<Object> handleExceptions(OrderManagerException404 e, WebRequest webRequest)
	{
		OrderManagerException exception = new OrderManagerException(e.getMessage());
        exception.setStatus(HttpStatus.NOT_FOUND);
        exception.setTimestamp(LocalDateTime.now());
        exception.setThrowable(e.getCause());
		return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(OrderManagerException500.class)
	public ResponseEntity<Object> handleExceptions(OrderManagerException500 e, WebRequest webRequest)
	{
		OrderManagerException exception = new OrderManagerException(e.getMessage());
        exception.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        exception.setTimestamp(LocalDateTime.now());
        exception.setThrowable(e.getCause());
		return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(OrderManagerException409.class)
	public ResponseEntity<Object> handleExceptions(OrderManagerException409 e, WebRequest webRequest) 
	{
		OrderManagerException exception = new OrderManagerException(e.getMessage());
        exception.setStatus(HttpStatus.CONFLICT);
        exception.setTimestamp(LocalDateTime.now());
        exception.setThrowable(e.getCause());
		return new ResponseEntity<>(exception, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(OrderManagerException400.class)
	public ResponseEntity<Object> handleExceptions(OrderManagerException400 e, WebRequest webRequest) 
	{
		OrderManagerException exception = new OrderManagerException(e.getMessage());
        exception.setStatus(HttpStatus.BAD_REQUEST);
        exception.setTimestamp(LocalDateTime.now());
        exception.setThrowable(e.getCause());
		return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers,
															   HttpStatus status, WebRequest request){
		OrderManagerException exception = new OrderManagerException("Validation failed for the request: [" + e.getMessage() + "]");
        exception.setStatus(HttpStatus.BAD_REQUEST);
        exception.setTimestamp(LocalDateTime.now());
        exception.setThrowable(e.getCause());
		return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpHeaders headers,
																	  HttpStatus status, WebRequest request){
		OrderManagerException exception = new OrderManagerException("The specified request method is not supported: [" + e.getMessage() + "]");
        exception.setStatus(HttpStatus.BAD_REQUEST);
        exception.setTimestamp(LocalDateTime.now());
        exception.setThrowable(e.getCause());
		return new ResponseEntity<>(exception, HttpStatus.METHOD_NOT_ALLOWED);
	}

	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e, HttpHeaders headers,
														HttpStatus status, WebRequest request){
		OrderManagerException exception = new OrderManagerException("Parameter type is not valid for this request: [" + e.getMessage() + "]");
        exception.setStatus(HttpStatus.BAD_REQUEST);
        exception.setTimestamp(LocalDateTime.now());
        exception.setThrowable(e.getCause());
		return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
	}
	
	protected ResponseEntity<Object> handleBadAuthorization(BadCredentialsException e, HttpHeaders headers, HttpStatus status, WebRequest request){
		
		OrderManagerException exception = new OrderManagerException("Authentication failed");
		exception.setStatus(HttpStatus.UNAUTHORIZED);
		exception.setTimestamp(LocalDateTime.now());
		exception.setThrowable(e.getCause());
		return new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED);
	}
}
