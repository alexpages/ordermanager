package com.alexpages.ordermanager.api.domain;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Pagination fields for request body
 */

public class PaginationBody implements Serializable {

  private static final long serialVersionUID = 1L;

  private BigDecimal page;

  private BigDecimal size;

  public PaginationBody page(BigDecimal page) {
    this.page = page;
    return this;
  }

  /**
   * Page key
   * minimum: 1
   * @return page
  */
  @Valid @DecimalMin("1") 
  @JsonProperty("page")
  public BigDecimal getPage() {
    return page;
  }

  public void setPage(BigDecimal page) {
    this.page = page;
  }

  public PaginationBody size(BigDecimal size) {
    this.size = size;
    return this;
  }

  /**
   * Page size
   * minimum: 1
   * @return size
  */
  @Valid @DecimalMin("1") 
  @JsonProperty("size")
  public BigDecimal getSize() {
    return size;
  }

  public void setSize(BigDecimal size) {
    this.size = size;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaginationBody paginationBody = (PaginationBody) o;
    return Objects.equals(this.page, paginationBody.page) &&
        Objects.equals(this.size, paginationBody.size);
  }

  @Override
  public int hashCode() {
    return Objects.hash(page, size);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaginationBody {\n");
    sb.append("    page: ").append(toIndentedString(page)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

