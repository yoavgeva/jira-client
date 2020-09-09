package com.datorama.rp.controllers;

import java.util.Map;

import com.datorama.rp.models.LaunchesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface LaunchController {
	@GET("/api/v1/{projectname}/launch")
	Call<LaunchesResponse> getLaunches(@Path("projectname") String projectName, @QueryMap Map<String, String> queryMap);
}
