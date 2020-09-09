package com.datorama.str.filter.occurence;

import com.datorama.str.enums.Indices;
import com.datorama.str.enums.rules.RulesCondition;
import com.datorama.str.models.Occurrence;
import com.datorama.str.models.rules.RuleFilterModel;

/** Filter for new tests only. */
public class NewTestOccurrenceFilter implements OccurrenceFilter {
  private final int lowerThanOrEquals;

  public NewTestOccurrenceFilter(int lowerThanOrEquals) {
    this.lowerThanOrEquals = lowerThanOrEquals;
  }

  @Override
  public RuleFilterModel filter() {
    RuleFilterModel ruleFilterModel = filterTypeModel();
    ruleFilterModel.setIndexName(Indices.TEST_ITEM.getValue());
    ruleFilterModel.setCondition(RulesCondition.ITEMS_SIZE.getValue());
    Occurrence occurrence = new Occurrence();
    occurrence.setOccurrenceCount(lowerThanOrEquals);
    occurrence.setCondition(RulesCondition.LOWER_THAN_OR_EQUALS.getValue());
    ruleFilterModel.setOccurrence(occurrence);
    return ruleFilterModel;
  }
}
