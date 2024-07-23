package com.solberg.dto;

import com.fasterxml.jackson.annotation.JsonProperty; // Import Jackson annotation

public class CheckListItemDto {
  private String text;
  private boolean checked;

  public CheckListItemDto() {
  }

  public CheckListItemDto(String name, boolean state) {
    text = name;
    checked = state;
  }

  @JsonProperty("text")
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @JsonProperty("checked")
  public boolean isChecked() {
    return checked;
  }

  public void setChecked(boolean checked) {
    this.checked = checked;
  }

  @Override
  public String toString() {
    return "CheckListItemDto{" +
        "text='" + text + '\'' +
        ", checked=" + checked +
        '}';
  }
}
