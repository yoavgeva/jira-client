package com.datorama.connector.jira.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Failed cause of some bad configuration")
public class BadRequestException  extends RuntimeException{
}
