package com.alexpages.ordermanager.api.domain;

import java.net.URI;
import java.util.Objects;
import com.alexpages.ordermanager.api.domain.Action;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * OrderInputAudit
 */

public class OrderInputAudit implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long orderId;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate startDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate endDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate actionDate;

  private Action action;

  public OrderInputAudit orderId(Long orderId) {
    this.orderId = orderId;
    return this;
  }

  /**
   * id of the order to which the action was performed
   * @return orderId
  */
  
  @JsonProperty("orderId")
  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public OrderInputAudit startDate(LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * Start date for the audit period
   * @return startDate
  */
  @Valid 
  @JsonProperty("startDate")
  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public OrderInputAudit endDate(LocalDate endDate) {
    this.endDate = endDate;
    return this;
  }

  /**
   * End date for the audit period
   * @return endDate
  */
  @Valid 
  @JsonProperty("endDate")
  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public OrderInputAudit actionDate(LocalDate actionDate) {
    this.actionDate = actionDate;
    return this;
  }

  /**
   * Date of the action
   * @return actionDate
  */
  @Valid 
  @JsonProperty("actionDate")
  public LocalDate getActionDate() {
    return actionDate;
  }

  public void setActionDate(LocalDate actionDate) {
    this.actionDate = actionDate;
  }

  public OrderInputAudit action(Action action) {
    this.action = action;
    return this;
  }

  /**
   * Get action
   * @return action
  */
  @Valid 
  @JsonProperty("action")
  public Action getAction() {
    return action;
  }

  public void setAction(Action action) {
    this.action = action;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderInputAudit orderInputAudit = (OrderInputAudit) o;
    return Objects.equals(this.orderId, orderInputAudit.orderId) &&
        Objects.equals(this.startDate, orderInputAudit.startDate) &&
        Objects.equals(this.endDate, orderInputAudit.endDate) &&
        Objects.equals(this.actionDate, orderInputAudit.actionDate) &&
        Objects.equals(this.action, orderInputAudit.action);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId, startDate, endDate, actionDate, action);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderInputAudit {\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    actionDate: ").append(toIndentedString(actionDate)).append("\n");
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
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

