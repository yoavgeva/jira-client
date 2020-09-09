package com.datorama.str.filter.occurence;

import com.datorama.str.enums.FilterTypes;
import com.datorama.str.filter.RuleFilter;
import com.datorama.str.models.rules.RuleFilterModel;

public interface OccurrenceFilter extends RuleFilter {

	default RuleFilterModel filterTypeModel(){
		RuleFilterModel model = new RuleFilterModel();
		model.setFilterType(FilterTypes.OCCURRENCE.getValue());
		return model;
	}

}
