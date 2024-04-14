package com.alexpages.ordermanager.api.domain;

import java.net.URI;
import java.util.Objects;
import com.alexpages.ordermanager.api.domain.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * OrderInputDataInputSearch
 */

@JsonTypeName("OrderInputData_inputSearch")
public class OrderInputDataInputSearch implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long orderId;

  private Status status;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate startCreationDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate endCreationDate;

  public OrderInputDataInputSearch orderId(Long orderId) {
    this.orderId = orderId;
    return this;
  }

  /**
   * id of order request
   * @return orderId
  */
  
  @JsonProperty("orderId")
  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public OrderInputDataInputSearch status(Status status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  @Valid 
  @JsonProperty("status")
  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public OrderInputDataInputSearch startCreationDate(LocalDate startCreationDate) {
    this.startCreationDate = startCreationDate;
    return this;
  }

  /**
   * Start date to be considered when filtering orders
   * @return startCreationDate
  */
  @Valid 
  @JsonProperty("startCreationDate")
  public LocalDate getStartCreationDate() {
    return startCreationDate;
  }

  public void setStartCreationDate(LocalDate startCreationDate) {
    this.startCreationDate = startCreationDate;
  }

  public OrderInputDataInputSearch endCreationDate(LocalDate endCreationDate) {
    this.endCreationDate = endCreationDate;
    return this;
  }

  /**
   * End date to be considered when filtering orders
   * @return endCreationDate
  */
  @Valid 
  @JsonProperty("endCreationDate")
  public LocalDate getEndCreationDate() {
    return endCreationDate;
  }

  public void setEndCreationDate(LocalDate endCreationDate) {
    this.endCreationDate = endCreationDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderInputDataInputSearch orderInputDataInputSearch = (OrderInputDataInputSearch) o;
    return Objects.equals(this.orderId, orderInputDataInputSearch.orderId) &&
        Objects.equals(this.status, orderInputDataInputSearch.status) &&
        Objects.equals(this.startCreationDate, orderInputDataInputSearch.startCreationDate) &&
        Objects.equals(this.endCreationDate, orderInputDataInputSearch.endCreationDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId, status, startCreationDate, endCreationDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderInputDataInputSearch {\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    startCreationDate: ").append(toIndentedString(startCreationDate)).append("\n");
    sb.append("    endCreationDate: ").append(toIndentedString(endCreationDate)).append("\n");
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

