package com.alexpages.ordermanager.api.domain;

import java.net.URI;
import java.util.Objects;
import com.alexpages.ordermanager.api.domain.PageResponse;
import com.alexpages.ordermanager.api.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * UserOuputData
 */

public class UserOuputData implements Serializable {

  private static final long serialVersionUID = 1L;

  @Valid
  private List<@Valid User> users;

  private PageResponse pageResponse;

  public UserOuputData users(List<@Valid User> users) {
    this.users = users;
    return this;
  }

  public UserOuputData addUsersItem(User usersItem) {
    if (this.users == null) {
      this.users = new ArrayList<>();
    }
    this.users.add(usersItem);
    return this;
  }

  /**
   * Get users
   * @return users
  */
  @Valid 
  @JsonProperty("users")
  public List<@Valid User> getUsers() {
    return users;
  }

  public void setUsers(List<@Valid User> users) {
    this.users = users;
  }

  public UserOuputData pageResponse(PageResponse pageResponse) {
    this.pageResponse = pageResponse;
    return this;
  }

  /**
   * Get pageResponse
   * @return pageResponse
  */
  @Valid 
  @JsonProperty("pageResponse")
  public PageResponse getPageResponse() {
    return pageResponse;
  }

  public void setPageResponse(PageResponse pageResponse) {
    this.pageResponse = pageResponse;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserOuputData userOuputData = (UserOuputData) o;
    return Objects.equals(this.users, userOuputData.users) &&
        Objects.equals(this.pageResponse, userOuputData.pageResponse);
  }

  @Override
  public int hashCode() {
    return Objects.hash(users, pageResponse);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserOuputData {\n");
    sb.append("    users: ").append(toIndentedString(users)).append("\n");
    sb.append("    pageResponse: ").append(toIndentedString(pageResponse)).append("\n");
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

