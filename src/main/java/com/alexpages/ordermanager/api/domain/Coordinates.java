package com.alexpages.ordermanager.api.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Coordinates of the order
 */

public class Coordinates implements Serializable {

  private static final long serialVersionUID = 1L;

  @Valid
  private List<String> origin;

  @Valid
  private List<String> destination;

  public Coordinates origin(List<String> origin) {
    this.origin = origin;
    return this;
  }

  public Coordinates addOriginItem(String originItem) {
    if (this.origin == null) {
      this.origin = new ArrayList<>();
    }
    this.origin.add(originItem);
    return this;
  }

  /**
   * Array containing [latitude, longitude] for origin
   * @return origin
  */
  
  @JsonProperty("origin")
  public List<String> getOrigin() {
    return origin;
  }

  public void setOrigin(List<String> origin) {
    this.origin = origin;
  }

  public Coordinates destination(List<String> destination) {
    this.destination = destination;
    return this;
  }

  public Coordinates addDestinationItem(String destinationItem) {
    if (this.destination == null) {
      this.destination = new ArrayList<>();
    }
    this.destination.add(destinationItem);
    return this;
  }

  /**
   * Array containing [latitude, longitude] for destination
   * @return destination
  */
  
  @JsonProperty("destination")
  public List<String> getDestination() {
    return destination;
  }

  public void setDestination(List<String> destination) {
    this.destination = destination;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coordinates coordinates = (Coordinates) o;
    return Objects.equals(this.origin, coordinates.origin) &&
        Objects.equals(this.destination, coordinates.destination);
  }

  @Override
  public int hashCode() {
    return Objects.hash(origin, destination);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Coordinates {\n");
    sb.append("    origin: ").append(toIndentedString(origin)).append("\n");
    sb.append("    destination: ").append(toIndentedString(destination)).append("\n");
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

