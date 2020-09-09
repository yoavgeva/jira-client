package com.datorama.rp.filter;

import java.util.HashMap;
import java.util.Map;

public class QueryPageReportPortal {
	private Map<String,String> map;

	public QueryPageReportPortal() {
		this.map = new HashMap<>();
	}

	public QueryPageReportPortal numberOfPage(int number) {
		map.put("page.page", String.valueOf(number));
		return this;
	}

	public QueryPageReportPortal sizeOfPage(int number) {
		map.put("page.size", String.valueOf(number));
		return this;
	}

	public QueryPageReportPortal sortPage(String value) {
		map.put("page.sort", value);
		return this;
	}

	protected Map<? extends String,? extends String> toMap() {
		return map;
	}
}
