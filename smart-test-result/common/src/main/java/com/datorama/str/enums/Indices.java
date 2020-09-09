package com.datorama.str.enums;

import com.datorama.str.CommonConstants;

public enum Indices {
  TEST_ITEM(CommonConstants.TEST_ITEM),
  LAUNCH(CommonConstants.LAUNCH);

  private final String name;

  Indices(String name) {
    this.name = name;
  }

  public String getValue() {
    return name;
  }
}
