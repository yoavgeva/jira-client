package com.datorama.elasticsearch.indicies;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.datorama.str.CommonConstants;
import com.datorama.str.enums.Fields;
import com.datorama.str.models.Attribute;
import com.datorama.str.models.LaunchModel;
import com.datorama.str.models.TestItemModel;
import com.datorama.str.models.TestParameters;

@Document(indexName = "test_item")
public class TestItemIndex {
	@Id
	private String id;
	private String testId;
	@Field(type = FieldType.Date,name = CommonConstants.START_TIME,format = DateFormat.date_optional_time)
	private Date startTime;
	@Field(type = FieldType.Date,name = CommonConstants.END_TIME,format = DateFormat.date_optional_time)
	private Date endTime;
	private String name;
	private String description;
	private String status;
	private List<TestParameters> parameters;
	private LaunchModel launch;
	private String type;
	private Set<String> tags;
	private List<Attribute> attributes;
	private List<TestItemModel> retries;

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
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

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public List<TestItemModel> getRetries() {
		return retries;
	}

	public void setRetries(List<TestItemModel> retries) {
		this.retries = retries;
	}

	public LaunchModel getLaunch() {
		return launch;
	}

	public void setLaunch(LaunchModel launch) {
		this.launch = launch;
	}

	public String getId() {
		return id;
	}

	private Date getFieldDate(Fields field){
		switch (field){
			case START_TIME:
				return getStartTime();
			case END_TIME:
				return getEndTime();
			default:
				return null;
		}
	}

	private String getFieldString(Fields field){
		switch (field){
			case NAME:
				return getName();
			case ITEM_ID:
				return getTestId();
			case DESCRIPTION:
				return getDescription();
			case TYPE:
				return getType();
			case STATUS:
				return getStatus();
			case ID:
				return getId();
			default:
				return null;
		}
	}

	public <T> T getField(Fields field,Class<T> clz){
		if(clz.equals(String.class)){
      		return (T) getFieldString(field);
		}
		if(clz.equals(Date.class)){
			return (T) getFieldDate(field);
		}
		throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,String.format("Field unsupported -> %s",field.getFieldName()));
	}
}
