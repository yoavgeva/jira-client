package com.datorama.str.filter.data;

import com.datorama.str.enums.FilterTypes;
import com.datorama.str.filter.RuleFilter;
import com.datorama.str.models.rules.RuleFilterModel;

public interface DataFilter extends RuleFilter {

	default RuleFilterModel filterTypeModel(){
		RuleFilterModel model = new RuleFilterModel();
		model.setFilterType(FilterTypes.DATA.getValue());
		return model;
	}
}
