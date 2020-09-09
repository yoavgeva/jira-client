package com.datorama.str.models;

import java.util.List;

import com.datorama.str.models.rules.RuleFilterModel;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultRequestModel {
  List<RuleFilterModel> ruleFilters;

  public List<RuleFilterModel> getRuleFilters() {
    return ruleFilters;
  }

  public void setRuleFilters(List<RuleFilterModel> ruleFilters) {
    this.ruleFilters = ruleFilters;
  }
}
