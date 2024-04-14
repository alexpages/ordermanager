package com.alexpages.ordermanager.error;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class OrderManagerExceptionHandlerTest {

    private OrderManagerExceptionHandler exceptionHandler = new OrderManagerExceptionHandler();
    
    @Test
    void testHandleExceptions_404() {
        OrderManagerException404 exception = new OrderManagerException404("Not found");
        ResponseEntity<Object> responseEntity = exceptionHandler.handleExceptions(exception, null);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testHandleExceptions_500() {
        OrderManagerException500 exception = new OrderManagerException500("Internal Server Error");
        ResponseEntity<Object> responseEntity = exceptionHandler.handleExceptions(exception, null);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void testHandleExceptions_409() {
        OrderManagerException409 exception = new OrderManagerException409("Conflict");
        ResponseEntity<Object> responseEntity = exceptionHandler.handleExceptions(exception, null);
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

    @Test
    void testHandleExceptions_400() {
        OrderManagerException400 exception = new OrderManagerException400("Bad Request");
        ResponseEntity<Object> responseEntity = exceptionHandler.handleExceptions(exception, null);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }  
    
}
