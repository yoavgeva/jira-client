package com.datorama.rp.client;

import java.io.Serializable;

import com.datorama.str.exceptions.SmartTestResultClientException;

public class ReportPortalClientConfiguration implements Serializable {
	private static volatile ReportPortalClientConfiguration configuration;
	private final String baseURL;
	private final String token;
	private boolean showLogs = false;

	private ReportPortalClientConfiguration(String baseUrl, String token,boolean showLogs) {
		if (configuration != null) {
			throw new SmartTestResultClientException("Use getInstance() method to get the single instance of this class.");
		}
		this.baseURL = baseUrl;
		this.token = token;
		this.showLogs = showLogs;
	}

	public static ReportPortalClientConfiguration create(String baseUrl, String token,boolean showLogs) {
		if (configuration == null) {
			synchronized (ReportPortalClientConfiguration.class) {
				if (configuration == null) {
					configuration = new ReportPortalClientConfiguration(baseUrl, token,showLogs);
				}
			}
		}
		return configuration;
	}

	protected static ReportPortalClientConfiguration getInstance() {
		if (configuration == null) {
			throw new SmartTestResultClientException("Use create() method to create the single instance of this class.");
		}
		return configuration;
	}

	/**
	 * for issues with serialization.
	 *
	 * @return same instance, to deny creation of a new one.
	 */
	protected ReportPortalClientConfiguration readResolve() {
		return getInstance();
	}

	protected String getBaseURL() {
		return baseURL;
	}

	protected String getToken() {
		return token;
	}

	protected boolean isShowLogs() {
		return showLogs;
	}

	protected void setShowLogs(boolean showLogs) {
		this.showLogs = showLogs;
	}
}
