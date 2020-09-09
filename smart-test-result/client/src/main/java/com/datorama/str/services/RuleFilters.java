package com.datorama.str.services;

import java.util.List;

import com.datorama.str.filter.data.DataFilter;
import com.datorama.str.filter.occurence.OccurrenceFilter;

public class RuleFilters {
  private List<DataFilter> dataFilterList;
  private List<OccurrenceFilter> occurrenceFilterList;

  public RuleFilters(List<DataFilter> dataFilterList, List<OccurrenceFilter> occurrenceFilterList) {
    this.dataFilterList = dataFilterList;
    this.occurrenceFilterList = occurrenceFilterList;
  }

  protected List<DataFilter> getDataFilterList() {
    return dataFilterList;
  }

  protected List<OccurrenceFilter> getOccurrenceFilterList() {
    return occurrenceFilterList;
  }
}
