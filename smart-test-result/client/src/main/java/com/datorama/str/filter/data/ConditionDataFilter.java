package com.datorama.str.filter.data;

import org.apache.commons.lang3.ObjectUtils;

import com.datorama.str.enums.Fields;
import com.datorama.str.enums.Indices;
import com.datorama.str.enums.rules.RulesCondition;
import com.datorama.str.exceptions.SmartTestResultClientException;
import com.datorama.str.models.rules.RuleFilterModel;

/** Data filter based on condition. */
public class ConditionDataFilter implements DataFilter {
  private final Indices index;
  private RulesCondition condition;
  private String value;
  private Fields field;

  public ConditionDataFilter(Indices index) {
    this.index = index;
  }

  @Override
  public RuleFilterModel filter() {
    if (!ObjectUtils.allNotNull(index, condition, value, field)) {
      throw new SmartTestResultClientException("Variables must not be null/empty.");
    }
    RuleFilterModel ruleFilter = filterTypeModel();
    ruleFilter.setIndexName(index.getValue());
    ruleFilter.setField(field.getFieldName());
    ruleFilter.setCondition(condition.getValue());
    ruleFilter.setValue(value);
    return ruleFilter;
  }

  public void filter(Fields field, RulesCondition condition, String value) {
    this.field = field;
    this.condition = condition;
    this.value = value;
  }
}
