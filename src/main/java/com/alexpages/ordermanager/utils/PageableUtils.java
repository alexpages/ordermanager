package com.alexpages.ordermanager.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.alexpages.ordermanager.api.domain.PageResponse;
import com.alexpages.ordermanager.api.domain.PaginationBody;
import com.alexpages.ordermanager.error.OrderManagerException400;

public class PageableUtils {
	
	private PageableUtils() {
		 throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}
	
	public static Pageable getPageable(PaginationBody paginationBody)
	{
		Pageable pageable = null;
		if(paginationBody == null) {
			pageable = Pageable.unpaged();
		} else {
			validatePaginationData(paginationBody);
			int pageIndex = paginationBody.getPage().intValueExact() - 1;
			pageable = PageRequest.of(pageIndex, paginationBody.getSize().intValueExact());
		}
		return pageable;
	}
	
	public static void validatePaginationData(PaginationBody paginationBody) 
	{
		if (paginationBody.getPage().intValue() < 1) {
			throw new OrderManagerException400("Page number must start with 1");
		}
		if (paginationBody.getSize().intValue() <= 0) {
			throw new OrderManagerException400("Limit should be a positive integer higher than 0");
		}	
	}
	
	public static PageResponse getPaginationResponse(Page<?> page, Pageable pageable)
	{
		PageResponse paginationResponse = null;
		if(pageable.isPaged())
		{
			paginationResponse = new PageResponse();
			Integer pageNumer = page.getNumber() + 1;
			paginationResponse.setNumber(pageNumer);
			paginationResponse.setSize(page.getSize());
			paginationResponse.setTotalPages(page.getTotalPages());
			paginationResponse.setTotalElements(page.getTotalElements());
		}
		return paginationResponse;
	}
}