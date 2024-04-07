package com.alexpages.ordermanager.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.alexpages.ordermanager.api.domain.PageResponse;
import com.alexpages.ordermanager.api.domain.PaginationBody;
import com.alexpages.ordermanager.error.OrderManagerException400;

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
	
	@Test
	void test_validatePaginationData_case1() {
		PaginationBody paginationBody = new PaginationBody();
		paginationBody.setPage(new BigDecimal(0));
		paginationBody.setSize(new BigDecimal(1));
		assertThrows(OrderManagerException400.class, () -> PageableUtils.validatePaginationData(paginationBody));
	}
	
	@Test
	void test_validatePaginationData_case2() {
		PaginationBody paginationBody = new PaginationBody();
		paginationBody.setPage(new BigDecimal(2));
		paginationBody.setSize(new BigDecimal(0));
		assertThrows(OrderManagerException400.class, () -> PageableUtils.validatePaginationData(paginationBody));
	}
	
	@Test 
	void test_getPaginationResponse_paged() 
	{
		Pageable pageable = PageRequest.of(1, 30);
		Page<String> page = new PageImpl<String>(getResultList(), pageable, 10);
		PageResponse paginationResponse = PageableUtils.getPaginationResponse(page, pageable);
		assertAll(
			() -> assertNotNull(paginationResponse),
			() -> assertEquals(2, paginationResponse.getNumber()),
			() -> assertEquals(30, paginationResponse.getSize()),
			() -> assertEquals(2, paginationResponse.getTotalPages()),
			() -> assertEquals(31, paginationResponse.getTotalElements())
		);
	}
	@Test 
	void test_getPaginationResponse_unpaged() 
	{
		Pageable pageable = Pageable.unpaged();
		Page<String> page = new PageImpl<String>(getResultList(), pageable, 10);
		PageResponse paginationResponse = PageableUtils.getPaginationResponse(page, pageable);
		assertNull(paginationResponse);
	}
	
	private void assertPagination(Pageable pageable, int pageNumber, int pageSize)
	{
		assertAll(
				() -> assertNotNull(pageable),
				() -> assertEquals(pageable.getPageNumber(), pageNumber),
				() -> assertEquals(pageable.getPageSize(), pageSize));
	}
	
	private List<String> getResultList()
	{
		return Arrays.asList("");
	}
}