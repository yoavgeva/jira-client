package com.datorama.rp.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Page {
	@JsonProperty(value = "number")
	int number;
	@JsonProperty(value = "size")
	int size;
	@JsonProperty(value = "totalElements")
	int totalElements;
	@JsonProperty(value = "totalPages")
	int totalPages;

	public int getNumber() {
		return number;
	}

	public int getSize() {
		return size;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	@Override public String toString() {
		return "Page{" +
				"number=" + number +
				", size=" + size +
				", totalElements=" + totalElements +
				", totalPages=" + totalPages +
				'}';
	}
}
