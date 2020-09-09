package com.datorama.connector.jira.services;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datorama.connector.jira.exceptions.NotFoundException;

import static com.datorama.connector.jira.common.PropertiesKeys.LAST_POLL_DATA_DATE_PROPERTY;

@Service
public class PropertiesDatastreamFileHandler {
	private final String JIRA_JSON_DIR = "jiraVersion/";

	private final String DATASTREAM_DIR = "datastream/";

	private final String PROPERTIES_SUFFIX = ".properties";

	private final S3Service s3Service;


	Logger logger = LogManager.getLogger(PropertiesDatastreamFileHandler.class);

	@Autowired
	public PropertiesDatastreamFileHandler(S3Service s3Service) {
		this.s3Service = s3Service;
	}

	public Optional<Properties> dataFromPropertiesInCloud(String uniqueId,String jql) {
		//String combineId = StringUtils.substring(uniqueId + encodeJQL(jql),0,80);
		String combineId = uniqueId;
		logger.info("id size: {}",combineId.toCharArray().length);
		Optional<File> file = s3Service.downloadFile(DATASTREAM_DIR + combineId + PROPERTIES_SUFFIX, combineId + PROPERTIES_SUFFIX);
		if (file.isEmpty()) {
			uploadNewDatastreamFile(combineId, initProperties());
			return Optional.empty();
		} else {
			Properties properties = getPropertiesFromFile(file.get());
			updateDataStreamFile(file.get(), combineId, properties);
			return Optional.of(properties);
		}
	}

	private String encodeJQL(String jql) {
		return Base64.getEncoder()
				.encodeToString(jql.getBytes());
	}

	private void updateDataStreamFile(File file, String token, Properties properties) {
		addPropertiesToFile(file, properties);
		s3Service.uploadFile(DATASTREAM_DIR + token + PROPERTIES_SUFFIX, file);
	}

	private Properties getPropertiesFromFile(File file) {
		Properties prop = new Properties();
		try (InputStream input = new FileInputStream(file)) {
			prop.load(input);
		} catch (IOException ex) {
			logger.error("Failed to load properties from file", ex);
			throw new NotFoundException();
		}
		return prop;
	}

	private void uploadNewDatastreamFile(String token, Properties properties) {
		File propertiesFile;
		try {
			propertiesFile = createPropertiesFile(token);
		} catch (IOException e) {
			logger.error("Failed creating properties file", e);
			throw new NotFoundException();
		}
		addPropertiesToFile(propertiesFile, properties);
		s3Service.uploadFile(DATASTREAM_DIR + token + PROPERTIES_SUFFIX, propertiesFile);
	}

	private File createPropertiesFile(String token) throws IOException {
		File propertiesFile = new File(token + PROPERTIES_SUFFIX);
		propertiesFile.createNewFile();
		return propertiesFile;
	}

	private void addPropertiesToFile(File file, Properties properties) {
		try (OutputStream outputStream = new FileOutputStream(file)) {
			properties.store(outputStream, null);
		} catch (IOException e) {
			logger.error("Failed updating properties file", e);
			throw new NotFoundException();
		}
	}

	private Properties initProperties() {
		Properties properties = new Properties();
		properties.setProperty(LAST_POLL_DATA_DATE_PROPERTY, jiraDateOfNow());
		return properties;
	}

	private String jiraDateOfNow() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.format(new Date());
	}

	@PostConstruct
	public void init() {
	//	s3Service.doesFileExist()
	}
}
