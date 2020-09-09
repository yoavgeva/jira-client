package com.datorama.connector.jira.models;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetDataResponse {
	public DataRowsResponse dataRowsResponse = new DataRowsResponse();
	public File file = null;
	public FileType fileType;
	public boolean compressResult = false;
}
