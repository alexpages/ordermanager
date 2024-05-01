package com.alexpages.ordermanager.api.domain;

import java.net.URI;
import java.util.Objects;
import com.alexpages.ordermanager.api.domain.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
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
 * UserInputDataInputSearch
 */

@JsonTypeName("UserInputData_inputSearch")
public class UserInputDataInputSearch implements Serializable {

  private static final long serialVersionUID = 1L;

  private String username;

  private Long userId;

  private Role role;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate creationDate;

  public UserInputDataInputSearch username(String username) {
    this.username = username;
    return this;
  }

  /**
   * name of the user
   * @return username
  */
  
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public UserInputDataInputSearch userId(Long userId) {
    this.userId = userId;
    return this;
  }

  /**
   * user identifier
   * @return userId
  */
  
  @JsonProperty("userId")
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public UserInputDataInputSearch role(Role role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   * @return role
  */
  @Valid 
  @JsonProperty("role")
  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public UserInputDataInputSearch creationDate(LocalDate creationDate) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserInputDataInputSearch userInputDataInputSearch = (UserInputDataInputSearch) o;
    return Objects.equals(this.username, userInputDataInputSearch.username) &&
        Objects.equals(this.userId, userInputDataInputSearch.userId) &&
        Objects.equals(this.role, userInputDataInputSearch.role) &&
        Objects.equals(this.creationDate, userInputDataInputSearch.creationDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, userId, role, creationDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserInputDataInputSearch {\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
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

