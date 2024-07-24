
package com.solberg.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckListItemDto {
  private Long id;
  private String text;
  private boolean checked;

  // No-argument constructor
  public CheckListItemDto() {
  }

  // Constructor with all parameters
  @JsonCreator
  public CheckListItemDto(
      @JsonProperty("id") Long id,
      @JsonProperty("text") String text,
      @JsonProperty("checked") boolean checked) {
    this.id = id;
    this.text = text;
    this.checked = checked;
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public boolean isChecked() {
    return checked;
  }

  public void setChecked(boolean checked) {
    this.checked = checked;
  }

  @Override
  public String toString() {
    return "CheckListItemDto{" +
        "id=" + id +
        ", text='" + text + '\'' +
        ", checked=" + checked +
        '}';
  }
}
