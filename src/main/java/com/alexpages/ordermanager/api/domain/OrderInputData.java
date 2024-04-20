package com.alexpages.ordermanager.api.domain;

import java.net.URI;
import java.util.Objects;
import com.alexpages.ordermanager.api.domain.OrderInputDataInputSearch;
import com.alexpages.ordermanager.api.domain.PaginationBody;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * PostOrdersBodyRequest with params to filter
 */

public class OrderInputData implements Serializable {

  private static final long serialVersionUID = 1L;

  private OrderInputDataInputSearch inputSearch;

  private PaginationBody paginationBody;

  public OrderInputData inputSearch(OrderInputDataInputSearch inputSearch) {
    this.inputSearch = inputSearch;
    return this;
  }

  /**
   * Get inputSearch
   * @return inputSearch
  */
  @Valid 
  @JsonProperty("inputSearch")
  public OrderInputDataInputSearch getInputSearch() {
    return inputSearch;
  }

  public void setInputSearch(OrderInputDataInputSearch inputSearch) {
    this.inputSearch = inputSearch;
  }

  public OrderInputData paginationBody(PaginationBody paginationBody) {
    this.paginationBody = paginationBody;
    return this;
  }

  /**
   * Get paginationBody
   * @return paginationBody
  */
  @Valid 
  @JsonProperty("paginationBody")
  public PaginationBody getPaginationBody() {
    return paginationBody;
  }

  public void setPaginationBody(PaginationBody paginationBody) {
    this.paginationBody = paginationBody;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderInputData orderInputData = (OrderInputData) o;
    return Objects.equals(this.inputSearch, orderInputData.inputSearch) &&
        Objects.equals(this.paginationBody, orderInputData.paginationBody);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inputSearch, paginationBody);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderInputData {\n");
    sb.append("    inputSearch: ").append(toIndentedString(inputSearch)).append("\n");
    sb.append("    paginationBody: ").append(toIndentedString(paginationBody)).append("\n");
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

