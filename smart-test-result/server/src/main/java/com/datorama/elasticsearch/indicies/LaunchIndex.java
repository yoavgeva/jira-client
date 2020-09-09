package com.datorama.elasticsearch.indicies;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.datorama.str.CommonConstants;
import com.datorama.str.enums.Fields;
import com.datorama.str.models.Attribute;

/**
 * Need to update {@link Fields} with field name and field.
 */
@Document(indexName = "launch")
public class LaunchIndex {
	@Id
	private String id;
	@Field(name = CommonConstants.ITEM_ID)
	private String itemId;
	@Field(type = FieldType.Date, name = CommonConstants.START_TIME, format = DateFormat.date_optional_time)
	private Date startTime;
	@Field(type = FieldType.Date, name = CommonConstants.END_TIME, format = DateFormat.date_optional_time)
	private Date endTime;
	private String name;
	private String status;
	private String environment;
	private String description;
	private Set<String> tags;
	private List<Attribute> attributes;

	public String getId() {
		return id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
}
