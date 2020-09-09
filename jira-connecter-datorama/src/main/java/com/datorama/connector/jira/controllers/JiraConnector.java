package com.datorama.connector.jira.controllers;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.datorama.connector.jira.exceptions.BadRequestException;
import com.datorama.connector.jira.models.*;
import com.datorama.connector.jira.services.*;
import com.datorama.connector.jira.utils.TokenUtils;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Issue;

@RestController
public class JiraConnector {
	private static final String JIRA_URL = "https://jira.datorama.net/";
	private static final String LOGIN_PATH = "/login";
	private static final String ACCOUNT_PATH = "/account";
	private static final String PROFILES_PATH = "/profiles";
	private static final String SON_PROFILES_PATH = "/son-profiles";
	private static final String SUB_PROFILE_PATH = "/sub-profiles";
	private static final String AUTH2_PATH = "/auth-tokens";
	private static final String AUTH_URI_PATH = "/auth-uri";
	// Synchronized requests
	private static final String DATA_PATH = "/data";
	// Polling requests
	private static final String START_JOB_PATH = "/start-job";
	private static final String CHECK_JOB_PATH = "/check-job";
	private static final String GET_DATA_PATH = "/get-job-data";
	//manual mapping
	private static final String CUSTOM_MAPPING_PATH = "/custom-mapping";
	private final JiraDataExtractor jiraDataExtractor;
	private final S3Service s3Service;
	private final JiraToDatorama jiraToDatorama;
	private final PropertiesDatastreamFileHandler propertiesDatastreamFileHandler;
	private final JQLAdapter jqlAdapter;
	Logger logger = LogManager.getLogger(JiraConnector.class);
	@Value("${trust.store.password}")
	private String trustPassword;

	@Autowired
	public JiraConnector(JiraDataExtractor jiraDataExtractor, S3Service service, JiraToDatorama jiraToDatorama,
			PropertiesDatastreamFileHandler propertiesDatastreamFileHandler, JQLAdapter jqlAdapter) {
		this.jiraDataExtractor = jiraDataExtractor;
		this.s3Service = service;
		this.jiraToDatorama = jiraToDatorama;
		this.propertiesDatastreamFileHandler = propertiesDatastreamFileHandler;
		this.jqlAdapter = jqlAdapter;
	}

	public GetDataResponse getDataByPolling(HttpHeaders httpHeaders, DataRequest dataRequest) throws Exception {
		return null;
	}

	@RequestMapping(value = LOGIN_PATH, method = RequestMethod.POST)
	public ResponseEntity<UserPassResponse> login(@RequestBody UserPassRequest userPassRequest) {
		UserPassResponse response = new UserPassResponse();
		response.success = false;
		logger.debug(userPassRequest);
		if (!StringUtils.isEmpty(userPassRequest.getPassword()) && !StringUtils.isEmpty(userPassRequest.getUsername())) {
			response.success = true;
			String token = TokenUtils.createToken(userPassRequest.getUsername(), userPassRequest.getPassword());
			response.token = token;
			logger.trace("endpoint: login, status: success, token:{}", token);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		logger.error("endpoint: login, status: failure");
		UserPassResponse.AdditionalParam param = new UserPassResponse.AdditionalParam();
		param.name = "description";
		param.value = "invalid jira credentials";
		response.additionalParams.add(param);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = DATA_PATH, method = RequestMethod.POST)
	public ResponseEntity<GetDataResponse> data(@RequestHeader(value = "token") String token, @RequestBody DataRequest dataRequest) {
		if (StringUtils.isEmpty(token) || StringUtils.isEmpty((CharSequence) dataRequest.dsJsonConfig.get("jql"))) {
			throw new BadRequestException();
		}
		String jql = dataRequest.dsJsonConfig.get("jql").toString();
		Optional<Properties> properties = propertiesDatastreamFileHandler.dataFromPropertiesInCloud(token, jql);
		String jqlModified = jqlAdapter.jqlModify(jql, properties);
		logger.info("jql: {}", jqlModified);
		BasicCredentials basicCredentials = TokenUtils.getCredentials(token).get();
		List<Issue> jiraIssues = jiraDataExtractor.searchAllIssuesByJql(jqlModified, basicCredentials);
		File file = jiraToDatorama.getConvertedFile(properties, jiraIssues);
		GetDataResponse response = new GetDataResponse();
		response.file = file;
		response.fileType = FileType.JSON;
		response.compressResult = true;
		return new ResponseEntity<>(response, HttpStatus.OK);
	}


}
