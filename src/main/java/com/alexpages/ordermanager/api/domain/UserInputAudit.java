package com.alexpages.ordermanager.api.domain;

import java.net.URI;
import java.util.Objects;
import com.alexpages.ordermanager.api.domain.Action;
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
 * UserInputAudit
 */

public class UserInputAudit implements Serializable {

  private static final long serialVersionUID = 1L;

  private String username;

  private Integer userId;

  /**
   * Gets or Sets role
   */
  public enum RoleEnum {
    USER("USER"),
    
    ADMIN("ADMIN");

    private String value;

    RoleEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static RoleEnum fromValue(String value) {
      for (RoleEnum b : RoleEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private RoleEnum role;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate actionDate;

  private Action action;

  public UserInputAudit username(String username) {
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

  public UserInputAudit userId(Integer userId) {
    this.userId = userId;
    return this;
  }

  /**
   * user identifier
   * @return userId
  */
  
  @JsonProperty("userId")
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public UserInputAudit role(RoleEnum role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   * @return role
  */
  
  @JsonProperty("role")
  public RoleEnum getRole() {
    return role;
  }

  public void setRole(RoleEnum role) {
    this.role = role;
  }

  public UserInputAudit actionDate(LocalDate actionDate) {
    this.actionDate = actionDate;
    return this;
  }

  /**
   * Date of the action
   * @return actionDate
  */
  @Valid 
  @JsonProperty("actionDate")
  public LocalDate getActionDate() {
    return actionDate;
  }

  public void setActionDate(LocalDate actionDate) {
    this.actionDate = actionDate;
  }

  public UserInputAudit action(Action action) {
    this.action = action;
    return this;
  }

  /**
   * Get action
   * @return action
  */
  @Valid 
  @JsonProperty("action")
  public Action getAction() {
    return action;
  }

  public void setAction(Action action) {
    this.action = action;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserInputAudit userInputAudit = (UserInputAudit) o;
    return Objects.equals(this.username, userInputAudit.username) &&
        Objects.equals(this.userId, userInputAudit.userId) &&
        Objects.equals(this.role, userInputAudit.role) &&
        Objects.equals(this.actionDate, userInputAudit.actionDate) &&
        Objects.equals(this.action, userInputAudit.action);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, userId, role, actionDate, action);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserInputAudit {\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    actionDate: ").append(toIndentedString(actionDate)).append("\n");
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
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

