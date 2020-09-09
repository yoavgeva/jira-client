package com.datorama.str.filter.data;

import org.apache.commons.lang3.ObjectUtils;

import com.datorama.str.enums.Fields;
import com.datorama.str.enums.Indices;
import com.datorama.str.enums.rules.RulesCondition;
import com.datorama.str.exceptions.SmartTestResultClientException;
import com.datorama.str.models.Attribute;
import com.datorama.str.models.rules.RuleFilterModel;

/**
 * Filter by item attributes (added by the user/client)
 */
public class AttributeDataFilter implements DataFilter {
	private Indices index;
	private String key;
	private String value;
	private RulesCondition condition;
	private Fields fields = Fields.ATTRIBUTES;

	public AttributeDataFilter(Indices index,String key, RulesCondition condition, String value) {
		this.key = key;
		this.value = value;
		this.index = index;
		this.condition = condition;
	}
	@Override public RuleFilterModel filter() {
		if (!ObjectUtils.allNotNull(index, key, value)) {
			throw new SmartTestResultClientException("Variables must not be null/empty.");
		}
		RuleFilterModel ruleFilterSTR = filterTypeModel();
		ruleFilterSTR.setIndexName(index.getValue());
		ruleFilterSTR.setField(fields.getFieldName());
		ruleFilterSTR.setCondition(condition.getValue());
		Attribute attributeSTR = new Attribute();
		attributeSTR.setKey(key);
		attributeSTR.setValue(value);
		ruleFilterSTR.setAttributes(attributeSTR);
		return ruleFilterSTR;
	}
}
