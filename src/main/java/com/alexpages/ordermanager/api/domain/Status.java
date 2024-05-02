package com.alexpages.ordermanager.api.domain;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Status of the order
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public enum Status {
  
  UNASSIGNED("UNASSIGNED"),
  
  TAKEN("TAKEN"),
  
  COMPLETED("COMPLETED");

  private String value;

  Status(String value) {
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
  public static Status fromValue(String value) {
    for (Status b : Status.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

