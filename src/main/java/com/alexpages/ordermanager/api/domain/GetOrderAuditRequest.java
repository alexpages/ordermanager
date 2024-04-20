package com.alexpages.ordermanager.api.domain;

import java.net.URI;
import java.util.Objects;
import com.alexpages.ordermanager.api.domain.OrderInputAudit;
import com.alexpages.ordermanager.api.domain.PaginationBody;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * GetOrderAuditRequest
 */

@JsonTypeName("getOrderAudit_request")
public class GetOrderAuditRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private OrderInputAudit orderInputAudit;

  private PaginationBody paginationBody;

  public GetOrderAuditRequest orderInputAudit(OrderInputAudit orderInputAudit) {
    this.orderInputAudit = orderInputAudit;
    return this;
  }

  /**
   * Get orderInputAudit
   * @return orderInputAudit
  */
  @Valid 
  @JsonProperty("orderInputAudit")
  public OrderInputAudit getOrderInputAudit() {
    return orderInputAudit;
  }

  public void setOrderInputAudit(OrderInputAudit orderInputAudit) {
    this.orderInputAudit = orderInputAudit;
  }

  public GetOrderAuditRequest paginationBody(PaginationBody paginationBody) {
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
    GetOrderAuditRequest getOrderAuditRequest = (GetOrderAuditRequest) o;
    return Objects.equals(this.orderInputAudit, getOrderAuditRequest.orderInputAudit) &&
        Objects.equals(this.paginationBody, getOrderAuditRequest.paginationBody);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderInputAudit, paginationBody);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetOrderAuditRequest {\n");
    sb.append("    orderInputAudit: ").append(toIndentedString(orderInputAudit)).append("\n");
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

