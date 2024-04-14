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
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * OrderAudit
 */

public class OrderAudit implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private Long orderId;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate actionDate;

  private Action action;

  public OrderAudit id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier for the order audit
   * @return id
  */
  
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public OrderAudit orderId(Long orderId) {
    this.orderId = orderId;
    return this;
  }

  /**
   * Unique identifier for the order that has had any action performed
   * @return orderId
  */
  
  @JsonProperty("orderId")
  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public OrderAudit actionDate(LocalDate actionDate) {
    this.actionDate = actionDate;
    return this;
  }

  /**
   * Date when the action was performed
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

  public OrderAudit action(Action action) {
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
    OrderAudit orderAudit = (OrderAudit) o;
    return Objects.equals(this.id, orderAudit.id) &&
        Objects.equals(this.orderId, orderAudit.orderId) &&
        Objects.equals(this.actionDate, orderAudit.actionDate) &&
        Objects.equals(this.action, orderAudit.action);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, orderId, actionDate, action);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderAudit {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
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

