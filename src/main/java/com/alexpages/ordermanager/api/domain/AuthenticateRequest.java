package com.alexpages.ordermanager.api.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request body for client authentication
 */

public class AuthenticateRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String username;

  private String password;

  public AuthenticateRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AuthenticateRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public AuthenticateRequest username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Username value of the user
   * @return username
  */
  @NotNull 
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public AuthenticateRequest password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Password value of the user
   * @return password
  */
  @NotNull 
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthenticateRequest authenticateRequest = (AuthenticateRequest) o;
    return Objects.equals(this.username, authenticateRequest.username) &&
        Objects.equals(this.password, authenticateRequest.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthenticateRequest {\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
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

