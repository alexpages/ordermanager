package com.alexpages.ordermanager.api.domain;

import java.net.URI;
import java.util.Objects;
import com.alexpages.ordermanager.api.domain.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * OrderPostResponse
 */

public class OrderPostResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private String origin;

  private String destination;

  private String time;

  private Integer distance;

  private Status status;

  private Long orderId;

  public OrderPostResponse origin(String origin) {
    this.origin = origin;
    return this;
  }

  /**
   * Origin address in human readable format
   * @return origin
  */
  
  @JsonProperty("origin")
  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public OrderPostResponse destination(String destination) {
    this.destination = destination;
    return this;
  }

  /**
   * Destination address in human readable format
   * @return destination
  */
  
  @JsonProperty("destination")
  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public OrderPostResponse time(String time) {
    this.time = time;
    return this;
  }

  /**
   * Time in minutes and human readable format
   * @return time
  */
  
  @JsonProperty("time")
  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public OrderPostResponse distance(Integer distance) {
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

  public OrderPostResponse status(Status status) {
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

  public OrderPostResponse orderId(Long orderId) {
    this.orderId = orderId;
    return this;
  }

  /**
   * id of order creation
   * @return orderId
  */
  
  @JsonProperty("orderId")
  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderPostResponse orderPostResponse = (OrderPostResponse) o;
    return Objects.equals(this.origin, orderPostResponse.origin) &&
        Objects.equals(this.destination, orderPostResponse.destination) &&
        Objects.equals(this.time, orderPostResponse.time) &&
        Objects.equals(this.distance, orderPostResponse.distance) &&
        Objects.equals(this.status, orderPostResponse.status) &&
        Objects.equals(this.orderId, orderPostResponse.orderId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(origin, destination, time, distance, status, orderId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderPostResponse {\n");
    sb.append("    origin: ").append(toIndentedString(origin)).append("\n");
    sb.append("    destination: ").append(toIndentedString(destination)).append("\n");
    sb.append("    time: ").append(toIndentedString(time)).append("\n");
    sb.append("    distance: ").append(toIndentedString(distance)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
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

