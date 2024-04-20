package com.alexpages.ordermanager.api.domain;

import java.net.URI;
import java.util.Objects;
import com.alexpages.ordermanager.api.domain.Coordinates;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * PosOrderBodyRequest with buy to articles
 */

public class OrderPostRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private Coordinates coordinates;

  private String description;

  public OrderPostRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public OrderPostRequest(Coordinates coordinates, String description) {
    this.coordinates = coordinates;
    this.description = description;
  }

  public OrderPostRequest coordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
    return this;
  }

  /**
   * Get coordinates
   * @return coordinates
  */
  @NotNull @Valid 
  @JsonProperty("coordinates")
  public Coordinates getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  public OrderPostRequest description(String description) {
    this.description = description;
    return this;
  }

  /**
   * description of the order
   * @return description
  */
  @NotNull 
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderPostRequest orderPostRequest = (OrderPostRequest) o;
    return Objects.equals(this.coordinates, orderPostRequest.coordinates) &&
        Objects.equals(this.description, orderPostRequest.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(coordinates, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderPostRequest {\n");
    sb.append("    coordinates: ").append(toIndentedString(coordinates)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

