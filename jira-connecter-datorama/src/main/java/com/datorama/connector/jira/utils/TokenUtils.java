package com.datorama.connector.jira.utils;

import java.util.Base64;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.rcarz.jiraclient.BasicCredentials;

public final class TokenUtils {
	private static final String DELIMITER = "::";
	static Logger logger = LogManager.getLogger(TokenUtils.class);

	public static String createToken(String userName, String password) {
		return Base64.getEncoder()
				.encodeToString(new StringBuilder()
						.append(userName)
						.append(DELIMITER)
						.append(password)
						.toString()
						.getBytes());
	}

	public static Optional<BasicCredentials> getCredentials(String token) {
		try {
			String tokenDecoded = new String(Base64.getDecoder().decode(token));
			String[] tokenSplit = tokenDecoded.split(DELIMITER);
			return Optional.of(new BasicCredentials(tokenSplit[0], tokenSplit[1]));
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.error("Failed to split token to user and password",e);
			return Optional.empty();
		}
	}

}
