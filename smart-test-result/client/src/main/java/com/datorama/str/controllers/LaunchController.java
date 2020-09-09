package com.datorama.str.controllers;

import com.datorama.str.models.LaunchResultModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public interface LaunchController {
	@PUT("/api/v1/launch")
	Call<Void> put(@Body LaunchResultModel launchResult);
}
