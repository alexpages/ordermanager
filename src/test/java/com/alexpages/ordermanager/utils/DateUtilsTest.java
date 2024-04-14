package com.alexpages.ordermanager.utils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DateUtilsTest
{
	@Test 
	void test_newInstance() 
	throws Exception 
	{
		 Constructor<DateUtils> newDateUtils = DateUtils.class.getDeclaredConstructor();
		 newDateUtils.setAccessible(true);
		 assertThrows(InvocationTargetException.class, () -> newDateUtils.newInstance());
	}
	
	@Test 
	void test_dateToLocalDateTime() 
	{
		assertAll(
			() -> assertNull(DateUtils.dateToLocalDateTime(null)),
			() -> assertNotNull(DateUtils.dateToLocalDateTime(new Date()))
		);
	}
	
	@Test 
	void test_localDateTimeToString() 
	{
		assertAll(
			() -> assertNull(DateUtils.localDateTimeToString(null)),
			() -> assertNotNull(DateUtils.localDateTimeToString(LocalDateTime.now()))
		);
	}
}