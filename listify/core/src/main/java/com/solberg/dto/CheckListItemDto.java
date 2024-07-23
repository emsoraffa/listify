package com.solberg.dto;

public class CheckListItemDto {
  private Long id;
  private String text;
  private boolean checked;

  // Constructors
  public CheckListItemDto(Long id, String text, boolean checked) {
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
}
