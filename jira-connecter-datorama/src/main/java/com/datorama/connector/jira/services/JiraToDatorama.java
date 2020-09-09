package com.datorama.connector.jira.services;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datorama.connector.jira.exceptions.NotFoundException;
import com.datorama.connector.jira.models.jira.JiraDatoramaLogicModel;
import com.datorama.connector.jira.models.jira.JiraToDatoramaModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.Version;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;

@Service
public class JiraToDatorama {
	private final S3Service s3Service;
	private static final String NONE = "None";
	private static final String JIRA_JSON = "jira.json";

	Logger logger = LogManager.getLogger(JiraToDatorama.class);

	@Autowired
	public JiraToDatorama(S3Service s3Service) {
		this.s3Service = s3Service;
	}

	private JiraToDatoramaModel getJson(String jsonFile) {
		Optional<File> jiraJsonFile = s3Service.downloadFile(jsonFile, jsonFile);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(jiraJsonFile.orElseThrow(() -> new NotFoundException()), JiraToDatoramaModel.class);
		} catch (IOException e) {
			logger.error("Failed fetching {}",jsonFile,e);
			throw new NotFoundException();
		}


	}

	@PostConstruct
	public void init() {
		if (!s3Service.doesFileExist(JIRA_JSON)) {
			File file = new File(
					getClass().getClassLoader().getResource(JIRA_JSON).getFile()
			);
			s3Service.uploadFile(JIRA_JSON,file);
		}
	}

	public File getConvertedFile(Optional<Properties> propertiesOptional, List<Issue> jiraIssues) {
//		if(propertiesOptional.isEmpty()){
//			logger.error("Failed fetching properties");
//			throw new NotFoundException();
//		}
		//String jsonFile = propertiesOptional.get().getProperty(PropertiesKeys.JIRA_JSON_FILE_VERSION);
		JiraToDatoramaModel jiraToDatoramaModel = getJson(JIRA_JSON);
		ObjectMapper mapper = new ObjectMapper();
		File resultFile;
		try {
			resultFile = File.createTempFile("tmp" + new Random().nextInt(), ".json");
		} catch (IOException e) {
			logger.error("Failed creating result file.",e);
			throw new NotFoundException();
		}
		try {
			logger.info("number of jira issues: {}",jiraIssues.size());
			List<Map<String,Object>> issues = listOfConvertedIssues(jiraIssues,jiraToDatoramaModel);
			logger.info("number of issues: {}", issues.size());
			mapper.writeValue(resultFile,issues);
		} catch (IOException e) {
			logger.error("Failed converting issues to json file",e);
			throw new NotFoundException();
		}
		return resultFile;
	}

	private List<Map<String, Object>> listOfConvertedIssues(List<Issue> jiraIssues, JiraToDatoramaModel jiraToDatoramaModel) {
		List<Map<String, Object>> list = new ArrayList<>();
		for (Issue jiraIssue:jiraIssues) {
			Map<String, Object> convertMap = new HashMap<>();
			defaultFields(convertMap, jiraIssue);
			customFields(convertMap, jiraIssue, jiraToDatoramaModel);
			list.add(convertMap);
		}
		return list;
	}

	private void customFields(Map<String, Object> convertMap, Issue jiraIssue, JiraToDatoramaModel jiraToDatoramaModel) {
		for (JiraDatoramaLogicModel model: jiraToDatoramaModel.getFields()) {
			Object customField = jiraIssue.getField(model.getJiraNameKey());
			Object value = NONE;
			if (!(customField instanceof JSONNull) && customField != null) {
				value = getFieldValue(model, customField);
			}
			convertMap.put(model.getDatoramaNameKey(), value);
		}
	}

	private Object getFieldValue(JiraDatoramaLogicModel model, Object customField) {
		if (Date.class.getCanonicalName().equals(model.getTypeValue())) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				return formatter.parse(customField.toString());
			} catch (ParseException e) {
				return NONE;
			}
		}
		if (String.class.getCanonicalName().equals(model.getTypeValue())) {
			if(model.getClassNameValue() != null){
				Class<?> clz;
				try {
					clz = Class.forName(model.getClassNameValue());
				} catch (ClassNotFoundException e) {
					return NONE;
				}
				if (model.getIndexValue() != null) {
					if (NumberUtils.isParsable(model.getIndexValue())) {
						return handleArray(model, (JSONArray) customField, clz);
					}
					return handleMethodWithArgumentInClass(model, customField, clz);
				}
				return handleMethodNoArgsInClass(model, customField, clz);
			}
			return customField.toString();
		}

		//Need to add support when need for other logic possibilities
		return NONE;
	}

	private Object handleMethodNoArgsInClass(JiraDatoramaLogicModel model, Object customField, Class<?> clz) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Object customFieldObjectified = objectMapper.readValue(customField.toString(), clz);
			Method method = clz.getDeclaredMethod(model.getMethodNameValue());
			return method.invoke(customFieldObjectified);
		} catch (JsonProcessingException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			return NONE;
		}
	}

	private Object handleMethodWithArgumentInClass(JiraDatoramaLogicModel model, Object customField, Class<?> clz) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Object customFieldObjectified = objectMapper.readValue(customField.toString(), clz);
			Method method = clz.getDeclaredMethod("get");
			return method.invoke(customFieldObjectified, model.getIndexValue());
		} catch (JsonProcessingException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			return NONE;
		}
	}

	private Object handleArray(JiraDatoramaLogicModel model, JSONArray customField, Class<?> clz) {
		JSONArray jsonArray = customField;
		ObjectMapper objectMapper = new ObjectMapper();
		int numberIndex = Integer.parseInt(model.getIndexValue());
		try {
			Object customFieldObjectified = objectMapper.readValue(jsonArray.get(numberIndex).toString(), clz);
			Method method = clz.getDeclaredMethod(model.getMethodNameValue());
			return method.invoke(customFieldObjectified);
		} catch (JsonProcessingException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e ) {
			return NONE;
		}
	}

	private void defaultFields(Map<String, Object> convertMap, Issue jiraIssue) {
		convertMap.put("key", jiraIssue.getKey());
		try {
			convertMap.put("id", Integer.parseInt(jiraIssue.getId()));
		} catch (Exception ex) {
			convertMap.put("id", 000);
		}
		try {
			convertMap.put("type", jiraIssue.getIssueType().getName());
		} catch (Exception ex) {
			convertMap.put("type", "None");
		}
		try {
			convertMap.put("reporter", jiraIssue.getReporter().getDisplayName());
		} catch (Exception ex) {
			convertMap.put("reporter", "None");
		}
		try {
			convertMap.put("assignee", jiraIssue.getAssignee().getDisplayName());
		} catch (Exception ex) {
			convertMap.put("assignee", "None");
		}
		try {
			convertMap.put("status", jiraIssue.getStatus().getName());
		} catch (Exception ex) {
			convertMap.put("status", "None");
		}
		try {
			convertMap.put("timeEstimate", jiraIssue.getTimeEstimate());
		} catch (Exception ex) {
			convertMap.put("timeEstimate", 0);
		}
		try {
			convertMap.put("timeSpent", jiraIssue.getTimeSpent());
		} catch (Exception ex) {
			convertMap.put("timeSpent", 0);
		}
		try {
			convertMap.put("summary", jiraIssue.getSummary());
		} catch (Exception ex) {
			convertMap.put("summary", "None");
		}
		try {
			convertMap.put("resolution", jiraIssue.getResolution().toString());
		}catch (Exception ex){
			convertMap.put("resolution", "None");
		}
		List<Version> fixVersions = jiraIssue.getFixVersions();
		if (!fixVersions.isEmpty()) {
			convertMap.put("fixVersions", fixVersions.get(0).getName());
		} else {
			convertMap.put("fixVersions", NONE);
		}
		convertMap.put("labels", jiraIssue.getLabels().toString());
		

	}





}
