package com.datorama.str.filter.occurence;

import org.apache.commons.lang3.ObjectUtils;

import com.datorama.str.enums.Fields;
import com.datorama.str.enums.Indices;
import com.datorama.str.enums.rules.RulesCondition;
import com.datorama.str.exceptions.SmartTestResultClientException;
import com.datorama.str.models.Occurrence;
import com.datorama.str.models.rules.RuleFilterModel;

/** Conditional for different fields for occurrence filter. */
public class ConditionOccurrenceFilter implements OccurrenceFilter {
  private final int occurrenceCount;
  private RulesCondition condition;
  private String value;
  private Fields field;

  public ConditionOccurrenceFilter(RulesCondition condition,int occurrenceCount) {
    this.occurrenceCount = occurrenceCount;
    this.condition = condition;
  }

  @Override
  public RuleFilterModel filter() {
    if (!ObjectUtils.allNotNull(condition, value, field)) {
      throw new SmartTestResultClientException("Variables must not be null/empty.");
    }
    RuleFilterModel model = filterTypeModel();
    model.setIndexName(Indices.TEST_ITEM.getValue());
    model.setCondition(condition.getValue());
    model.setField(field.getFieldName());
    model.setValue(value);
    Occurrence occurrence = new Occurrence();
    occurrence.setOccurrenceCount(occurrenceCount);
    occurrence.setCondition(condition.getValue());
    model.setOccurrence(occurrence);
    return model;
  }

  public void filterEquals(Fields field, String value) {
    this.field = field;
    this.value = value;
  }
}
