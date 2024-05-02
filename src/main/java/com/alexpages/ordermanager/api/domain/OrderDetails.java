package com.alexpages.ordermanager.api.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * OrderDetails
 */

public class OrderDetails implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String description;

  private String time;

  private Integer distance;

  private Status status;

  private String origin;

  private String destination;

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
   * Description of the order
   * @return description
  */
  
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public OrderDetails time(String time) {
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

  public OrderDetails origin(String origin) {
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

  public OrderDetails destination(String destination) {
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
        Objects.equals(this.time, orderDetails.time) &&
        Objects.equals(this.distance, orderDetails.distance) &&
        Objects.equals(this.status, orderDetails.status) &&
        Objects.equals(this.origin, orderDetails.origin) &&
        Objects.equals(this.destination, orderDetails.destination) &&
        Objects.equals(this.creationDate, orderDetails.creationDate) &&
        Objects.equals(this.version, orderDetails.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description, time, distance, status, origin, destination, creationDate, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderDetails {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    time: ").append(toIndentedString(time)).append("\n");
    sb.append("    distance: ").append(toIndentedString(distance)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    origin: ").append(toIndentedString(origin)).append("\n");
    sb.append("    destination: ").append(toIndentedString(destination)).append("\n");
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

