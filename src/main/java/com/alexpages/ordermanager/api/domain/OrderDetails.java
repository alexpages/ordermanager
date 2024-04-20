package com.alexpages.ordermanager.api.domain;

import java.net.URI;
import java.util.Objects;
import com.alexpages.ordermanager.api.domain.Status;
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
 * OrderDetails
 */

public class OrderDetails implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String description;

  private Integer distance;

  private Status status;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate creationDate;

  private Long version;

  public OrderDetails id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier for the order
   * @return id
  */
  
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public OrderDetails description(String description) {
    this.description = description;
    return this;
  }

  /**
   * description of the order
   * @return description
  */
  
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public OrderDetails distance(Integer distance) {
    this.distance = distance;
    return this;
  }

  /**
   * distance between origin and destination coordinates
   * @return distance
  */
  
  @JsonProperty("distance")
  public Integer getDistance() {
    return distance;
  }

  public void setDistance(Integer distance) {
    this.distance = distance;
  }

  public OrderDetails status(Status status) {
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

  public OrderDetails creationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * Date of creation of the order
   * @return creationDate
  */
  @Valid 
  @JsonProperty("creationDate")
  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }

  public OrderDetails version(Long version) {
    this.version = version;
    return this;
  }

  /**
   * Version of the order
   * @return version
  */
  
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderDetails orderDetails = (OrderDetails) o;
    return Objects.equals(this.id, orderDetails.id) &&
        Objects.equals(this.description, orderDetails.description) &&
        Objects.equals(this.distance, orderDetails.distance) &&
        Objects.equals(this.status, orderDetails.status) &&
        Objects.equals(this.creationDate, orderDetails.creationDate) &&
        Objects.equals(this.version, orderDetails.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description, distance, status, creationDate, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderDetails {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    distance: ").append(toIndentedString(distance)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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

