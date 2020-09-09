package com.datorama.rp.filter;

import java.util.HashMap;
import java.util.Map;

public class QueryMapReportPortal {
	private final Map<String, String> map;

	public QueryMapReportPortal() {
		this.map = new HashMap<>();
	}

	public QueryMapReportPortal addFilter(FilterCriteria filterCriteria, Condition condition, String value) {
		map.put(String.format("filter.%s.%s", condition.getValue(), filterCriteria.name()), value);
		return this;
	}

	public QueryMapReportPortal addQueryPage(QueryPageReportPortal queryPageRP) {
		map.putAll(queryPageRP.toMap());
		return this;
	}

	public QueryMapReportPortal addFilter(String filterCriteria, Condition condition, String value) {
		map.put(String.format("filter.%s.%s", condition.getValue(), filterCriteria), value);
		return this;
	}

	/**
	 * @return an ImmutableMap of the created query
	 */
	public Map<String, String> toMap() {
		return map;
	}
}
