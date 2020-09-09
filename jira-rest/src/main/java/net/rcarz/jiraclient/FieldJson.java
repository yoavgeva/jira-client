package net.rcarz.jiraclient;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class FieldJson extends Resource {
	private String name;
	private boolean custom;
	private String id;
	private boolean orderable;
	private boolean navigable;
	private boolean searchable;
	private List<String> clauseNames;
	private SchemaJson schema;

	/**
	 * Creates a new JIRA resource.
	 *
	 * @param restclient REST client instance
	 */
	protected FieldJson(RestClient restclient, JSONObject json) {
		super(restclient);

		if (json != null)
		deserialise(json);
	}

	private void deserialise(JSONObject json) {
		Map map = json;
	}

	private class SchemaJson {
		private String type;
		private String custom;
		private int customId;
	}
}
