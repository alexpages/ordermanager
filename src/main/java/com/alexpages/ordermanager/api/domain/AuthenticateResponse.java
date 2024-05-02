package com.alexpages.ordermanager.api.domain;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response body from authentication
 */

public class AuthenticateResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private String jwt;

  public AuthenticateResponse jwt(String jwt) {
    this.jwt = jwt;
    return this;
  }

  /**
   * Client temporary JWT
   * @return jwt
  */
  
  @JsonProperty("jwt")
  public String getJwt() {
    return jwt;
  }

  public void setJwt(String jwt) {
    this.jwt = jwt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthenticateResponse authenticateResponse = (AuthenticateResponse) o;
    return Objects.equals(this.jwt, authenticateResponse.jwt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(jwt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthenticateResponse {\n");
    sb.append("    jwt: ").append(toIndentedString(jwt)).append("\n");
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

