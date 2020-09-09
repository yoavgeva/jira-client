package com.datorama.str.controllers;

import com.datorama.str.models.ResultRequestModel;
import com.datorama.str.models.ResultResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ResultController {
	@POST("/api/v1/result")
	Call<ResultResponseModel> get(@Body ResultRequestModel ruleOutcomeRequest);
}
