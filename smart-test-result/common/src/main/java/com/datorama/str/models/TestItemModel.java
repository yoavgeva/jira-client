package com.datorama.str.models;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;

import com.datorama.str.CommonConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestItemModel {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	@JsonProperty(CommonConstants.START_TIME)
	private Date startTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	@JsonProperty(CommonConstants.END_TIME)
	private Date endTime;
	@JsonProperty(CommonConstants.ID)
	private String itemId;
	@JsonProperty(CommonConstants.NAME)
	private String name;
	@JsonProperty(CommonConstants.DESCRIPTION)
	private String description;
	@JsonProperty(CommonConstants.STATUS)
	private String status;
	@JsonProperty(CommonConstants.PARAMETERS)
	private List<TestParameters> parameters;
	@JsonProperty(CommonConstants.TYPE)
	private String type;
	@JsonProperty(CommonConstants.TAGS)
	private Set<String> tags;
	private List<Attribute> attributes;
	@JsonProperty(CommonConstants.RETRIES)
	private List<TestItemModel> retries;

	public List<TestItemModel> getRetries() {
		return retries;
	}

	public void setRetries(List<TestItemModel> retries) {
		this.retries = retries;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public List<TestParameters> getParameters() {
		return parameters;
	}

	public void setParameters(List<TestParameters> parameters) {
		this.parameters = parameters;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	@JsonGetter("attributes")
	public List<Attribute> getAttributes() {
		return ObjectUtils.defaultIfNull(attributes, Collections.emptyList());
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("TestItemSTR{");
		sb.append("startTime=").append(startTime);
		sb.append(", endTime=").append(endTime);
		sb.append(", itemId='").append(itemId).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append(", description='").append(description).append('\'');
		sb.append(", status='").append(status).append('\'');
		sb.append(", parameter=").append(parameters);
		sb.append(", type='").append(type).append('\'');
		sb.append(", tags=").append(tags);
		sb.append(", attribute=").append(getAttributes());
		sb.append(", retries=").append(retries);
		sb.append('}');
		return sb.toString();
	}
}
