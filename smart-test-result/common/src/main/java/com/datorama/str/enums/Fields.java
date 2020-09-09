package com.datorama.str.enums;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.datorama.str.CommonConstants;

public enum Fields {
  ID(CommonConstants.ID, String.class),
  ITEM_ID(CommonConstants.ITEM_ID, String.class),
  START_TIME(CommonConstants.START_TIME, Date.class),
  END_TIME(CommonConstants.END_TIME, Date.class),
  NAME(CommonConstants.NAME, String.class),
  STATUS(CommonConstants.STATUS, String.class),
  ENVIRONMENT(CommonConstants.ENVIRONMENT, String.class),
  DESCRIPTION(CommonConstants.DESCRIPTION, String.class),
  TAGS(CommonConstants.TAGS, String.class),
  ATTRIBUTES(CommonConstants.ATTRIBUTES, List.class),
  PARAMETERS(CommonConstants.PARAMETERS, List.class),
  OCCURRENCE(CommonConstants.OCCURRENCE, List.class),
  TYPE(CommonConstants.TYPE, String.class);

  private final String fieldName;
  private final Class clz;

  Fields(String fieldName, Class clz) {
    this.fieldName = fieldName;
    this.clz = clz;
  }

  public static Optional<Fields> getEnum(String value) {
    for (int i = 0; i < Fields.values().length; i++) {
      if (value != null && value.equals(Fields.values()[i].getFieldName())) {
        return Optional.of(Fields.values()[i]);
      }
    }
    return Optional.empty();
  }

  public String getFieldName() {
    return fieldName;
  }

  public Class getClz() {
    return clz;
  }
}
