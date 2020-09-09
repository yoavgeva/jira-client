package com.datorama.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.datorama.services.QueryDataResult;
import com.datorama.services.QueryDataService;
import com.datorama.services.QueryOccurrenceService;
import com.datorama.str.models.ResultRequestModel;
import com.datorama.str.models.ResultResponseModel;
import com.datorama.str.models.TestItemModel;

@RestController
@RequestMapping(
    value = "/v1/result",
    consumes = {"application/JSON"})
public class ResultController {
  final QueryDataService queryDataService;
  final QueryOccurrenceService queryOccurrenceService;
  Logger logger = LogManager.getLogger(ResultController.class);

  public ResultController(
      QueryDataService queryDataService, QueryOccurrenceService queryOccurrenceService) {
    this.queryDataService = queryDataService;
    this.queryOccurrenceService = queryOccurrenceService;
  }

  @RequestMapping(method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.OK)
  public ResultResponseModel postResultWithClientRule(@RequestBody ResultRequestModel resultRequestStr) {
    QueryDataResult dataResult = queryDataService.filter(resultRequestStr.getRuleFilters());
    List<TestItemModel> testItems =
        queryOccurrenceService.filter(
            dataResult.getList(),
            resultRequestStr.getRuleFilters(),
            dataResult.getLatestLaunchId());
    ResultResponseModel response = new ResultResponseModel();
    response.setTestItems(testItems);
    return response;
  }
}
