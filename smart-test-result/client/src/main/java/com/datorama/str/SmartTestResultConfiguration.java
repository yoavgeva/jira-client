package com.datorama.str;

import java.io.Serializable;

import com.datorama.str.exceptions.SmartTestResultClientException;

public class SmartTestResultConfiguration implements Serializable {

	private String baseURL;
	private String token;
	private static volatile SmartTestResultConfiguration configuration;
	private boolean showLogs = false;
	private SmartTestResultConfiguration(String baseUrl,String token,boolean showLogs) {
		if (configuration != null) {
			throw new SmartTestResultClientException("Use getInstance() method to get the single instance of this class.");
		}
		this.baseURL =baseUrl;
		this.token=token;
		this.showLogs = showLogs;
	}

	public static SmartTestResultConfiguration create(String baseUrl, String token,boolean showLogs) {
		if (configuration == null) {
			synchronized (SmartTestResultConfiguration.class) {
				if (configuration == null) {
					configuration = new SmartTestResultConfiguration(baseUrl, token,showLogs);
				}
			}
		}
		return configuration;
	}



	/**
	 * for issues with serialization.
	 *
	 * @return same instance, to deny creation of a new one.
	 */
	protected SmartTestResultConfiguration readResolve() {
		return getInstance();
	}

	protected static SmartTestResultConfiguration getInstance() {
		if (configuration == null) {
			throw new SmartTestResultClientException("Use create() method to create the single instance of this class.");
		}
		return configuration;
	}

	protected String getBaseURL() {
		return baseURL;
	}

	protected String getToken() {
		return token;
	}

	protected void setShowLogs(boolean showLogs) {
		this.showLogs = showLogs;
	}

	protected boolean isShowLogs() {
		return showLogs;
	}
}
