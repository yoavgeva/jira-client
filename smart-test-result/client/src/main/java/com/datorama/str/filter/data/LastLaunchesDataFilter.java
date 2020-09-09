package com.datorama.str.filter.data;

import com.datorama.str.enums.Fields;
import com.datorama.str.enums.Indices;
import com.datorama.str.exceptions.SmartTestResultClientException;
import com.datorama.str.models.Range;
import com.datorama.str.models.rules.RuleFilterModel;

/**
 * Filter for latest launches to query by.
 */
public class LastLaunchesDataFilter implements DataFilter {
	private final Indices index = Indices.LAUNCH;
	private final int numberOfLaunches;

	public LastLaunchesDataFilter(int numberOfLaunches) {
		this.numberOfLaunches = numberOfLaunches;
	}

	@Override public RuleFilterModel filter() {
		if (numberOfLaunches <= 0) {
			throw new SmartTestResultClientException("Need to be positive number.");
		}
		RuleFilterModel model = filterTypeModel();
		model.setField(Fields.START_TIME.getFieldName());
		model.setIndexName(index.getValue());
		Range range = new Range();
		range.setOccurrenceCount(numberOfLaunches);
		model.setRange(range);
		return model;
	}
}
