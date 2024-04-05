package com.alexpages.ordermanager.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import com.alexpages.ordermanager.api.domain.PaginationBody;

@ExtendWith(MockitoExtension.class)
public class PageableUtilsTest
{
	@Test 
	void test_newInstance() 
	throws Exception 
	{
		 Constructor<PageableUtils> newPageable = PageableUtils.class.getDeclaredConstructor();
		 newPageable.setAccessible(true);
		 assertThrows(InvocationTargetException.class, () -> newPageable.newInstance());
	}
	@Test
	void test_getPageable_null() 
	{
		Pageable pageable = PageableUtils.getPageable(null);
		assertTrue(pageable.isUnpaged());
	}
	@Test
	void test_getPageable_notNull() 
	{
		
		PaginationBody paginationBody = new PaginationBody();
		paginationBody.setPage(new BigDecimal(3));
		paginationBody.setSize(new BigDecimal(5));
		
		Pageable pageable = PageableUtils.getPageable(paginationBody);
		assertPagination(pageable, 2, 5);
	}
	
	private void assertPagination(Pageable pageable, int pageNumber, int pageSize)
	{
		assertAll(
				() -> assertNotNull(pageable),
				() -> assertEquals(pageable.getPageNumber(), pageNumber),
				() -> assertEquals(pageable.getPageSize(), pageSize));
	}
}