package com.datorama.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.datorama.services.LaunchService;
import com.datorama.str.models.LaunchResultModel;

@RestController
@RequestMapping(
    value = "/v1/launch",
    consumes = {"application/JSON"})
public class LaunchController {
  final LaunchService launchService;
  Logger logger = LogManager.getLogger(LaunchController.class);

  @Autowired
  public LaunchController(LaunchService launchService) {
    this.launchService = launchService;
  }

  @RequestMapping(method = RequestMethod.PUT)
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void putLaunch(@RequestBody LaunchResultModel launchResultSTR) {
    logger.info("size: {}", launchResultSTR.getTestItems().size());
    launchService.createIndices(launchResultSTR);
  }
}
