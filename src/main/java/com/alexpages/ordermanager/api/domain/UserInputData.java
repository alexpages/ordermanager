package com.alexpages.ordermanager.api.domain;

import java.net.URI;
import java.util.Objects;
import com.alexpages.ordermanager.api.domain.PaginationBody;
import com.alexpages.ordermanager.api.domain.UserInputDataInputSearch;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Search criteria to obtain users information
 */

public class UserInputData implements Serializable {

  private static final long serialVersionUID = 1L;

  private UserInputDataInputSearch inputSearch;

  private PaginationBody paginationBody;

  public UserInputData inputSearch(UserInputDataInputSearch inputSearch) {
    this.inputSearch = inputSearch;
    return this;
  }

  /**
   * Get inputSearch
   * @return inputSearch
  */
  @Valid 
  @JsonProperty("inputSearch")
  public UserInputDataInputSearch getInputSearch() {
    return inputSearch;
  }

  public void setInputSearch(UserInputDataInputSearch inputSearch) {
    this.inputSearch = inputSearch;
  }

  public UserInputData paginationBody(PaginationBody paginationBody) {
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
    UserInputData userInputData = (UserInputData) o;
    return Objects.equals(this.inputSearch, userInputData.inputSearch) &&
        Objects.equals(this.paginationBody, userInputData.paginationBody);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inputSearch, paginationBody);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserInputData {\n");
    sb.append("    inputSearch: ").append(toIndentedString(inputSearch)).append("\n");
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

