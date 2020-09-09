package com.datorama.str.exceptions;

public class SmartTestResultClientException extends RuntimeException {
	public SmartTestResultClientException(String message) {
		super(message);
	}

	public SmartTestResultClientException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
