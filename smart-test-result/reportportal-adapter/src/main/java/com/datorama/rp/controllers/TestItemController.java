package com.datorama.rp.controllers;

import java.util.Map;

import com.datorama.rp.models.TestItemsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface TestItemController {

	@GET("/api/v1/{projectname}/item")
	Call<TestItemsResponse> getTestItems(@Path("projectname") String projectName,@QueryMap Map<String, String> queryMap);

}
