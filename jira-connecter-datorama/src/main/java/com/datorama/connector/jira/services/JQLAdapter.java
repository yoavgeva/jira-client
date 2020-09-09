package com.datorama.connector.jira.services;

import java.util.Optional;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.datorama.connector.jira.common.PropertiesKeys;

@Service
public class JQLAdapter {
	private final String JIRA_CREATED_DATE = "createdDate";
	private final String JIRA_UPDATED_DATE = "updatedDate";
	Logger logger = LogManager.getLogger(JQLAdapter.class);

	public String jqlModify(String jql, Optional<Properties> properties) {
		String jqlModified = jql;
		if (isDateFieldsPresentInJql(jql)) {
			return jqlModified;
		}
		//if properties file exits
		if (properties.isPresent()) {
			String date = properties.get().getProperty(PropertiesKeys.LAST_POLL_DATA_DATE_PROPERTY);
			if (date == null) {
				return jqlModified;
			}
			return addDates(jql, date);
		}
		//if first time datastream without file in cloud
		return jqlModified;
	}

	private boolean isDateFieldsPresentInJql(String jql) {
		return jql.contains(JIRA_CREATED_DATE) || jql.contains(JIRA_UPDATED_DATE);
	}

	private String addDates(String jql, String date) {
		return String.format("%s and (%s > '%s' or %s > '%s')", jql, JIRA_CREATED_DATE, date, JIRA_UPDATED_DATE, date);
	}

}
