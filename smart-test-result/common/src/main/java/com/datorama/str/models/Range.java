package com.datorama.str.models;

import java.util.Date;

import com.datorama.str.CommonConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Range {
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  @JsonProperty(CommonConstants.OLDEST_DATE)
  private Date oldestDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  @JsonProperty(CommonConstants.NEWEST_DATE)
  private Date newestDate;

  @JsonProperty(CommonConstants.OCCURRENCE_COUNT)
  private int occurrenceCount;

  public Date getOldestDate() {
    return oldestDate;
  }

  public void setOldestDate(Date oldestDate) {
    this.oldestDate = oldestDate;
  }

  public Date getNewestDate() {
    return newestDate;
  }

  public void setNewestDate(Date newestDate) {
    this.newestDate = newestDate;
  }

  public int getOccurrenceCount() {
    return occurrenceCount;
  }

  public void setOccurrenceCount(int occurrenceCount) {
    this.occurrenceCount = occurrenceCount;
  }
}
