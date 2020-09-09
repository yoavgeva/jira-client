package com.datorama.str.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.datorama.str.SmartTestResultClient;
import com.datorama.str.controllers.ResultController;
import com.datorama.str.exceptions.SmartTestResultClientException;
import com.datorama.str.models.ResultRequestModel;
import com.datorama.str.models.ResultResponseModel;
import com.datorama.str.models.rules.RuleFilterModel;

import retrofit2.Call;
import retrofit2.Response;

/** Service for Result of by rule filters. */
public class ResultService {
  public ResultService() {}

  public ResultResponseModel getResultByClientRule(RuleFilters filters) {
    ResultRequestModel resultRequest = new ResultRequestModel();
    List<RuleFilterModel> models = new ArrayList<>();
    filters.getDataFilterList().forEach(it -> models.add(it.filter()));
    filters.getOccurrenceFilterList().forEach(it -> models.add(it.filter()));
    resultRequest.setRuleFilters(models);
    ResultController resultController = SmartTestResultClient.createService(ResultController.class);
    Call<ResultResponseModel> call = resultController.get(resultRequest);
    Response<ResultResponseModel> response;
    try {
      response = call.execute();
      if (!response.isSuccessful()) {
        throw new SmartTestResultClientException(
            String.format("Failed to get result. %s", response.errorBody().string()));
      }
    } catch (IOException e) {
      throw new SmartTestResultClientException("Failed to get result.", e);
    }

    return response.body();
  }
}
