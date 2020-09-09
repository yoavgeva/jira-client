package com.datorama.connector.jira.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Failed to find resource")
public class NotFoundException  extends RuntimeException {
}
