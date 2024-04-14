package com.alexpages.ordermanager.utils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ListUtilsTest
{
	@Test 
	void test_newInstance() 
	throws Exception 
	{
		 Constructor<ListUtils> newListUtils = ListUtils.class.getDeclaredConstructor();
		 newListUtils.setAccessible(true);
		 assertThrows(InvocationTargetException.class, () -> newListUtils.newInstance());
	}
	
	@Test 
	void test_isBlank() 
	{
		assertAll(
			() -> assertTrue(ListUtils.isBlank(null)),
			() -> assertTrue(ListUtils.isBlank(new ArrayList<Object>())),
			() -> assertFalse(ListUtils.isBlank(Arrays.asList(new Object())))
		);
	}
	
	@Test 
	void test_toString() 
	{
		assertAll(
			() -> assertNull(ListUtils.toString(null)),
			() -> assertEquals("{}", ListUtils.toString(new ArrayList<String>())),
			() -> assertEquals("{test1}", ListUtils.toString(Arrays.asList("test1"))),
			() -> assertEquals("{test1, test2}", ListUtils.toString(Arrays.asList("test1", "test2"))),
			() -> assertEquals("{1, 2}", ListUtils.toString(Arrays.asList(1, 2)))
		);
	}
}