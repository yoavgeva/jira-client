package com.datorama.connector.jira.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Service
public class S3Service {
	private final AmazonS3 amazonS3Client;
	Logger logger = LogManager.getLogger(S3Service.class);
	@Value("${s3.bucket.name}")
	private String s3BucketName;

	@Autowired
	public S3Service(AmazonS3 amazonS3Client) {
		this.amazonS3Client = amazonS3Client;
	}

	public void uploadFile(String fileInBucket, File file) {
		PutObjectRequest putObjectRequest = new PutObjectRequest(s3BucketName, fileInBucket, file);
		amazonS3Client.putObject(putObjectRequest);
	}

	public Optional<File> downloadFile(String fileInBucket, String fileDownloadedName) {
		S3Object s3Object = amazonS3Client.getObject(s3BucketName, fileInBucket);
		S3ObjectInputStream inputStream = s3Object.getObjectContent();
		try {
			File file = new File(fileDownloadedName);
				byte[] bytes = StreamUtils.copyToByteArray(inputStream);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
				fileOutputStream.write(bytes);
				return Optional.of(file);
		} catch (IOException e) {
			logger.error("Failed downloading file",e);
			return Optional.empty();
		}
	}

	public void deleteFile(String fileInBucket) {
		amazonS3Client.deleteObject(s3BucketName, fileInBucket);
	}

	public boolean doesFileExist(String fileInBucket) {
		return amazonS3Client.doesObjectExist(s3BucketName, fileInBucket);
	}

	@PostConstruct
	public void init() {
		if (!amazonS3Client.doesBucketExistV2(s3BucketName)) {
			amazonS3Client.createBucket(s3BucketName);
		}
	}
}
