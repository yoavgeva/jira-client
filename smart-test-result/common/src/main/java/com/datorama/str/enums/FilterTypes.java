package com.datorama.str.enums;

import com.datorama.str.CommonConstants;

public enum FilterTypes {
  OCCURRENCE(CommonConstants.OCCURRENCE_FILTER),
  DATA(CommonConstants.DATA_FILTER);
  private final String name;

  FilterTypes(String name) {
    this.name = name;
  }

  public String getValue() {
    return name;
  }
}
