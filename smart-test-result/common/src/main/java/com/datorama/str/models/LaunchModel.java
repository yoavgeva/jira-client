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

//TODO maybe add reporter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LaunchModel {
	@JsonProperty(CommonConstants.STATUS)
	private String status;
	@JsonProperty(CommonConstants.NAME)
	private String name;
	@JsonProperty(CommonConstants.ITEM_ID) //TODO adjust naming for more unique
	private String itemId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	@JsonProperty(CommonConstants.START_TIME)
	private Date startTime;
	@JsonFormat (shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	@JsonProperty(CommonConstants.END_TIME)
	private Date endTime;
	@JsonProperty(CommonConstants.ENVIRONMENT)
	private String environment;
	@JsonProperty(CommonConstants.DESCRIPTION)
	private String description;
	@JsonProperty(CommonConstants.TAGS)
	private Set<String> tags;
	@JsonProperty(CommonConstants.ID)
	private String id;


	private List<Attribute> attributes;
	@JsonProperty(CommonConstants.TYPE)
	private String type;

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	@JsonGetter(CommonConstants.ATTRIBUTES)
	public List<Attribute> getAttributes() {
		return ObjectUtils.defaultIfNull(attributes, Collections.emptyList());
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
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

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}



	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public Set<String> getTags() {
		return tags;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("LaunchSTR{");
		sb.append("status='").append(status).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append(", id='").append(itemId).append('\'');
		sb.append(", startTime=").append(startTime);
		sb.append(", endTime=").append(endTime);
		sb.append(", environment='").append(environment).append('\'');
		sb.append(", description='").append(description).append('\'');
		sb.append(", tags=").append(tags);
		sb.append(", attributes=").append(attributes);
		sb.append(", type='").append(type).append('\'');
		sb.append('}');
		return sb.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
    this.id = id;
	}
}
