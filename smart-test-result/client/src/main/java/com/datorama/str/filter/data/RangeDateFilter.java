package com.datorama.str.filter.data;

import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;

import com.datorama.str.enums.Fields;
import com.datorama.str.enums.Indices;
import com.datorama.str.exceptions.SmartTestResultClientException;
import com.datorama.str.models.Range;
import com.datorama.str.models.rules.RuleFilterModel;

/**
 * Filter for dates fields.
 */
public class RangeDateFilter implements DataFilter {
	private Date oldestDate;
	private Date newestDate;
	private final Indices index;
	private final Fields field;

	public RangeDateFilter(Fields field, Indices index) {
		this.index = index;
		this.field = field;
	}

	@Override public RuleFilterModel filter() {
		if (!ObjectUtils.allNotNull(oldestDate, newestDate, index, field)) {
			throw new SmartTestResultClientException("Variables must not be null/empty.");
		}
		RuleFilterModel ruleFilterSTR = filterTypeModel();
		ruleFilterSTR.setField(field.getFieldName());
		ruleFilterSTR.setIndexName(index.getValue());
		Range range = new Range();
		range.setNewestDate(newestDate);
		range.setOldestDate(oldestDate);
		ruleFilterSTR.setRange(range);
		return ruleFilterSTR;
	}

	public RangeDateFilter tillNow(Date oldestDate) {
		this.oldestDate = oldestDate;
		this.newestDate = new Date(System.currentTimeMillis());
		return this;
	}

	public RangeDateFilter rangeOfDates(Date oldestDate, Date newestDate) {
		this.oldestDate = oldestDate;
		this.newestDate = newestDate;
		return this;
	}
}
