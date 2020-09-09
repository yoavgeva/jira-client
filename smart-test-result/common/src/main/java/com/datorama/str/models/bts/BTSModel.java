package com.datorama.str.models.bts;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BTSModel {
	private boolean success;
	private String error;

	public String getError() {
		return error;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
