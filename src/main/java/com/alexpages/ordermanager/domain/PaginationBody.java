package com.alexpages.ordermanager.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import javax.validation.Valid;

@Valid
@Data
public class PaginationBody implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@JsonProperty("page")
	private BigDecimal page;

	@JsonProperty("size")
	private BigDecimal size;

	public PaginationBody page(BigDecimal page) {
		this.page = page;
		return this;
	}

	public PaginationBody size(BigDecimal size) {
		this.size = size;
		return this;
	}

}
