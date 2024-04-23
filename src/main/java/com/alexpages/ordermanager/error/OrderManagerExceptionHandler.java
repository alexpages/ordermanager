package com.alexpages.ordermanager.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
	
	@ExceptionHandler(OrderManagerException403.class)
	public ResponseEntity<Object> handleExceptions(OrderManagerException403 e, WebRequest webRequest) 
	{
		OrderManagerException exception = new OrderManagerException(e.getMessage());
        exception.setStatus(HttpStatus.FORBIDDEN);
        exception.setTimestamp(LocalDateTime.now());
        exception.setThrowable(e.getCause());
		return new ResponseEntity<>(exception, HttpStatus.FORBIDDEN);
	}
		
	@Override
	public ResponseEntity<Object> handleHttpMessageNotReadable(	HttpMessageNotReadableException ex, HttpHeaders headers,
            													HttpStatus status, WebRequest request) 
	{
		OrderManagerException exception = new OrderManagerException(ex.getMessage());
        exception.setStatus(HttpStatus.BAD_REQUEST);
        exception.setTimestamp(LocalDateTime.now());
        exception.setThrowable(ex.getCause());
		return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
	}
	
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(	MethodArgumentNotValidException ex, HttpHeaders headers, 
																	HttpStatus status, WebRequest request) 
	{
		OrderManagerException exception = new OrderManagerException(ex.getMessage());
        exception.setStatus(HttpStatus.BAD_REQUEST);
        exception.setTimestamp(LocalDateTime.now());
        exception.setThrowable(ex.getCause());
		return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
	}
	
}
