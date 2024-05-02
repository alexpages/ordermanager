package com.alexpages.ordermanager.error;

import java.time.LocalDateTime;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
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
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleExceptions(ConstraintViolationException e, WebRequest webRequest) 
	{
		OrderManagerException exception = new OrderManagerException(e.getMessage());
        exception.setStatus(HttpStatus.BAD_REQUEST);
        exception.setTimestamp(LocalDateTime.now());
        exception.setThrowable(e.getCause());
		return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleExceptions(MethodArgumentTypeMismatchException e, WebRequest webRequest) 
	{
		OrderManagerException exception = new OrderManagerException(e.getMessage());
        exception.setStatus(HttpStatus.BAD_REQUEST);
        exception.setTimestamp(LocalDateTime.now());
        exception.setThrowable(null);	//e.getMessage is enough, no need to throw entire stack trace
		return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
	}
			
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(	HttpMessageNotReadableException e, HttpHeaders headers,
            														HttpStatus status, WebRequest request) 
	{
		OrderManagerException exception = new OrderManagerException(e.getMessage());
        exception.setStatus(HttpStatus.BAD_REQUEST);
        exception.setTimestamp(LocalDateTime.now());
        exception.setThrowable(null); 	//e.getMessage is enough, no need to throw entire stack trace
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
	}
	
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(	MethodArgumentNotValidException e, HttpHeaders headers, 
																	HttpStatus status, WebRequest request) 
	{
		OrderManagerException exception = new OrderManagerException(e.getMessage());
        exception.setStatus(HttpStatus.BAD_REQUEST);
        exception.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(	NoHandlerFoundException e, HttpHeaders headers, 
																	HttpStatus status, WebRequest request )
	{
		OrderManagerException exception = new OrderManagerException(e.getMessage());
        exception.setStatus(HttpStatus.NOT_FOUND);
        exception.setTimestamp(LocalDateTime.now());
        exception.setThrowable(e.getCause());
		return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
	}

}