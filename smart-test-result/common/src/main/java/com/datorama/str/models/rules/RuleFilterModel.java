package com.datorama.str.models.rules;

import com.datorama.str.CommonConstants;
import com.datorama.str.models.Attribute;
import com.datorama.str.models.Occurrence;
import com.datorama.str.models.Range;
import com.datorama.str.models.TestParameters;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RuleFilterModel {
  private String field;
  private String condition;
  private String value;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty(CommonConstants.INDEX_NAME)
  private String indexName;

  @JsonProperty(CommonConstants.FILTER_TYPE)
  private String filterType;

  @JsonProperty(CommonConstants.ATTRIBUTES)
  private Attribute attributes;

  @JsonProperty(CommonConstants.PARAMETERS)
  private TestParameters parameters;

  @JsonProperty(CommonConstants.RANGE)
  private Range range;

  @JsonProperty(CommonConstants.OCCURRENCE)
  private Occurrence occurrence;

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getIndexName() {
    return indexName;
  }

  public void setIndexName(String indexName) {
    this.indexName = indexName;
  }

  public Attribute getAttributes() {
    return attributes;
  }

  public void setAttributes(Attribute attributes) {
    this.attributes = attributes;
  }

  public TestParameters getParameters() {
    return parameters;
  }

  public void setParameters(TestParameters parameters) {
    this.parameters = parameters;
  }

  public Range getRange() {
    return range;
  }

  public void setRange(Range range) {
    this.range = range;
  }

  public String getFilterType() {
    return filterType;
  }

  public void setFilterType(String filterType) {
    this.filterType = filterType;
  }

  public Occurrence getOccurrence() {
    return occurrence;
  }

  public void setOccurrence(Occurrence occurrence) {
    this.occurrence = occurrence;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("RuleFilterModel{");
    sb.append("field='").append(field).append('\'');
    sb.append(", condition='").append(condition).append('\'');
    sb.append(", value='").append(value).append('\'');
    sb.append(", indexName='").append(indexName).append('\'');
    sb.append(", filterType='").append(filterType).append('\'');
    sb.append(", attributes=").append(attributes);
    sb.append(", parameters=").append(parameters);
    sb.append(", range=").append(range);
    sb.append(", occurrence=").append(occurrence);
    sb.append('}');
    return sb.toString();
  }
}
