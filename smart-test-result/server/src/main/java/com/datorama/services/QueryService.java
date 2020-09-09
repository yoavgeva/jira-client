package com.datorama.services;

import java.util.ArrayList;
import java.util.List;

import com.datorama.str.enums.FilterTypes;
import com.datorama.str.models.rules.RuleFilterModel;

public interface QueryService {

	default List<RuleFilterModel> typeFilters(List<RuleFilterModel> filterModelList, FilterTypes filterType) {
		List<RuleFilterModel> dataFilters = new ArrayList<>();
		filterModelList.forEach(
				it -> {
					if (it.getFilterType().equals(filterType.getValue())) {
						dataFilters.add(it);
					}
				});
		return dataFilters;
	}
}
