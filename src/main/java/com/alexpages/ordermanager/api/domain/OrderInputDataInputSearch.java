package com.alexpages.ordermanager.api.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

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
   * Order request identifier
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

