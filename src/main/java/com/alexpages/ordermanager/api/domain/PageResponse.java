package com.alexpages.ordermanager.api.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PageResponse
 */

public class PageResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer totalPages;

  private Long totalElements;

  private Integer size;

  private Integer number;

  private Integer numberOfElements;

  private Boolean last;

  private Boolean first;

  private Boolean empty;

  private Sort sort;

  public PageResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PageResponse(Integer totalPages, Long totalElements, Integer size, Integer number, Integer numberOfElements, Boolean last, Boolean first, Boolean empty, Sort sort) {
    this.totalPages = totalPages;
    this.totalElements = totalElements;
    this.size = size;
    this.number = number;
    this.numberOfElements = numberOfElements;
    this.last = last;
    this.first = first;
    this.empty = empty;
    this.sort = sort;
  }

  public PageResponse totalPages(Integer totalPages) {
    this.totalPages = totalPages;
    return this;
  }

  /**
   * Número total de páginas existentes
   * @return totalPages
  */
  @NotNull 
  @JsonProperty("totalPages")
  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  public PageResponse totalElements(Long totalElements) {
    this.totalElements = totalElements;
    return this;
  }

  /**
   * Número total de registros existentes
   * @return totalElements
  */
  @NotNull 
  @JsonProperty("totalElements")
  public Long getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(Long totalElements) {
    this.totalElements = totalElements;
  }

  public PageResponse size(Integer size) {
    this.size = size;
    return this;
  }

  /**
   * Número total de registros por página
   * @return size
  */
  @NotNull 
  @JsonProperty("size")
  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public PageResponse number(Integer number) {
    this.number = number;
    return this;
  }

  /**
   * Número de registro de la primera página
   * @return number
  */
  @NotNull 
  @JsonProperty("number")
  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public PageResponse numberOfElements(Integer numberOfElements) {
    this.numberOfElements = numberOfElements;
    return this;
  }

  /**
   * Número de registros de la página devuelta
   * @return numberOfElements
  */
  @NotNull 
  @JsonProperty("numberOfElements")
  public Integer getNumberOfElements() {
    return numberOfElements;
  }

  public void setNumberOfElements(Integer numberOfElements) {
    this.numberOfElements = numberOfElements;
  }

  public PageResponse last(Boolean last) {
    this.last = last;
    return this;
  }

  /**
   * true si es la última página
   * @return last
  */
  @NotNull 
  @JsonProperty("last")
  public Boolean getLast() {
    return last;
  }

  public void setLast(Boolean last) {
    this.last = last;
  }

  public PageResponse first(Boolean first) {
    this.first = first;
    return this;
  }

  /**
   * true si es la primera página
   * @return first
  */
  @NotNull 
  @JsonProperty("first")
  public Boolean getFirst() {
    return first;
  }

  public void setFirst(Boolean first) {
    this.first = first;
  }

  public PageResponse empty(Boolean empty) {
    this.empty = empty;
    return this;
  }

  /**
   * true si la página devuelta no contiene registros
   * @return empty
  */
  @NotNull 
  @JsonProperty("empty")
  public Boolean getEmpty() {
    return empty;
  }

  public void setEmpty(Boolean empty) {
    this.empty = empty;
  }

  public PageResponse sort(Sort sort) {
    this.sort = sort;
    return this;
  }

  /**
   * Get sort
   * @return sort
  */
  @NotNull @Valid 
  @JsonProperty("sort")
  public Sort getSort() {
    return sort;
  }

  public void setSort(Sort sort) {
    this.sort = sort;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PageResponse pageResponse = (PageResponse) o;
    return Objects.equals(this.totalPages, pageResponse.totalPages) &&
        Objects.equals(this.totalElements, pageResponse.totalElements) &&
        Objects.equals(this.size, pageResponse.size) &&
        Objects.equals(this.number, pageResponse.number) &&
        Objects.equals(this.numberOfElements, pageResponse.numberOfElements) &&
        Objects.equals(this.last, pageResponse.last) &&
        Objects.equals(this.first, pageResponse.first) &&
        Objects.equals(this.empty, pageResponse.empty) &&
        Objects.equals(this.sort, pageResponse.sort);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalPages, totalElements, size, number, numberOfElements, last, first, empty, sort);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PageResponse {\n");
    sb.append("    totalPages: ").append(toIndentedString(totalPages)).append("\n");
    sb.append("    totalElements: ").append(toIndentedString(totalElements)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    numberOfElements: ").append(toIndentedString(numberOfElements)).append("\n");
    sb.append("    last: ").append(toIndentedString(last)).append("\n");
    sb.append("    first: ").append(toIndentedString(first)).append("\n");
    sb.append("    empty: ").append(toIndentedString(empty)).append("\n");
    sb.append("    sort: ").append(toIndentedString(sort)).append("\n");
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

