package com.alexpages.ordermanager.error;

import lombok.experimental.StandardException;

@StandardException //experimental annotation
public class OrderManagerException500 extends RuntimeException {

	public OrderManagerException500(String message, Throwable cause) {
		super(message, cause);
	}
}
