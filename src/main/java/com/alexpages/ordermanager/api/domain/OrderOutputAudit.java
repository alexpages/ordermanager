package com.alexpages.ordermanager.api.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * OrderOutputAudit
 */

public class OrderOutputAudit implements Serializable {

  private static final long serialVersionUID = 1L;

  @Valid
  private List<@Valid OrderAudit> orders;

  private PageResponse pageResponse;

  public OrderOutputAudit orders(List<@Valid OrderAudit> orders) {
    this.orders = orders;
    return this;
  }

  public OrderOutputAudit addOrdersItem(OrderAudit ordersItem) {
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
  public List<@Valid OrderAudit> getOrders() {
    return orders;
  }

  public void setOrders(List<@Valid OrderAudit> orders) {
    this.orders = orders;
  }

  public OrderOutputAudit pageResponse(PageResponse pageResponse) {
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
    OrderOutputAudit orderOutputAudit = (OrderOutputAudit) o;
    return Objects.equals(this.orders, orderOutputAudit.orders) &&
        Objects.equals(this.pageResponse, orderOutputAudit.pageResponse);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orders, pageResponse);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderOutputAudit {\n");
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

