package com.alexpages.ordermanager.error;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OrderManagerException {

	private final String error; //message

}
