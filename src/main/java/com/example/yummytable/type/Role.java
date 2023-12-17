package com.example.yummytable.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public enum Role {
  LOGIN("ROLE_LOGIN"),
  LOGOUT("ROLE_LOGOUT");
  private String value;
}
