package com.datorama.occurrence;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.datorama.str.enums.rules.RulesCondition;

public class ConditionPredicate {

  private static boolean predicateInt(Integer value, RulesCondition condition, Integer value2) {
    switch (condition) {
      case LOWER_THAN:
        return value < value2;
      case EQUALS:
        return value == value2;
      case NOT_EQUALS:
        return value != value2;
      case LOWER_THAN_OR_EQUALS:
        return value <= value2;
      case GREATER_THAN:
        return value > value2;
      case GREATER_THAN_OR_EQUALS:
        return value >= value2;
      default:
        throw new HttpClientErrorException(
            HttpStatus.BAD_REQUEST,
            String.format("Rule condition unsupported -> %s", condition.toString()));
    }
  }

  private static boolean predicateLong(Long value, RulesCondition condition, Long value2) {
    switch (condition) {
      case LOWER_THAN:
        return value < value2;
      case EQUALS:
        return value == value2;
      case NOT_EQUALS:
        return value != value2;
      case LOWER_THAN_OR_EQUALS:
        return value <= value2;
      case GREATER_THAN:
        return value > value2;
      case GREATER_THAN_OR_EQUALS:
        return value >= value2;
      default:
        throw new HttpClientErrorException(
                HttpStatus.BAD_REQUEST,
                String.format("Rule condition unsupported -> %s", condition.toString()));
    }
  }

  private static boolean predicateDate(Date value, RulesCondition condition, Date value2) {

    switch (condition) {
      case LOWER_THAN:
        return value.compareTo(value2) < 0;
      case EQUALS:
        return value.compareTo(value2) == 0;
      case NOT_EQUALS:
        return value.compareTo(value2) != 0;
      case LOWER_THAN_OR_EQUALS:
        return value.compareTo(value2) <= 0;
      case GREATER_THAN:
        return value.compareTo(value2) > 0;
      case GREATER_THAN_OR_EQUALS:
        return value.compareTo(value2) >= 0;
      default:
        throw new HttpClientErrorException(
            HttpStatus.BAD_REQUEST,
            String.format("Rule condition unsupported -> %s", condition.toString()));
    }
  }

  public static boolean predicate(Object value, RulesCondition condition, Object value2) {
    if (value instanceof Integer) {
      return predicateInt((Integer) value, condition, (Integer) value2);
    }
    if (value instanceof Long) {
      return predicateLong((Long) value, condition, (Long) value2);
    }
    if (value instanceof Date) {
      return predicateDate((Date) value, condition, (Date) value2);
    }
    throw new HttpClientErrorException(
        HttpStatus.BAD_REQUEST,
        String.format(
            "object type unsupported -> 1st: %s 2nd: %s", value.getClass(), value2.getClass()));
  }
}
