package com.alexpages.ordermanager.api.domain;

import java.net.URI;
import java.util.Objects;
import com.alexpages.ordermanager.api.domain.OrderDetails;
import com.alexpages.ordermanager.api.domain.PageResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * OrderOutputData
 */

public class OrderOutputData implements Serializable {

  private static final long serialVersionUID = 1L;

  @Valid
  private List<@Valid OrderDetails> orders;

  private PageResponse pageResponse;

  public OrderOutputData orders(List<@Valid OrderDetails> orders) {
    this.orders = orders;
    return this;
  }

  public OrderOutputData addOrdersItem(OrderDetails ordersItem) {
    if (this.orders == null) {
      this.orders = new ArrayList<>();
    }
    this.orders.add(ordersItem);
    return this;
  }

  /**
   * Get orders
   * @return orders
  */
  @Valid 
  @JsonProperty("orders")
  public List<@Valid OrderDetails> getOrders() {
    return orders;
  }

  public void setOrders(List<@Valid OrderDetails> orders) {
    this.orders = orders;
  }

  public OrderOutputData pageResponse(PageResponse pageResponse) {
    this.pageResponse = pageResponse;
    return this;
  }

  /**
   * Get pageResponse
   * @return pageResponse
  */
  @Valid 
  @JsonProperty("pageResponse")
  public PageResponse getPageResponse() {
    return pageResponse;
  }

  public void setPageResponse(PageResponse pageResponse) {
    this.pageResponse = pageResponse;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderOutputData orderOutputData = (OrderOutputData) o;
    return Objects.equals(this.orders, orderOutputData.orders) &&
        Objects.equals(this.pageResponse, orderOutputData.pageResponse);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orders, pageResponse);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderOutputData {\n");
    sb.append("    orders: ").append(toIndentedString(orders)).append("\n");
    sb.append("    pageResponse: ").append(toIndentedString(pageResponse)).append("\n");
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

