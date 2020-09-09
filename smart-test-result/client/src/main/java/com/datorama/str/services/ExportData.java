package com.datorama.str.services;

import java.io.IOException;
import java.util.List;

import com.datorama.str.SmartTestResultClient;
import com.datorama.str.adapters.LaunchResult;
import com.datorama.str.controllers.LaunchController;
import com.datorama.str.exceptions.SmartTestResultClientException;
import com.datorama.str.models.LaunchResultModel;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Service to export data from reporter/test framework to Smart Test result.
 */
public class ExportData {
	public ExportData() {
	}

	/**
	 * send latest launch result
	 * @param launchResult launch result received by the testing framework.
	 */
	public void putLatestLaunchResult(LaunchResult launchResult) {
		LaunchResultModel launchResultSTR = launchResult.putLatestLaunchResult();
		LaunchController launchController = SmartTestResultClient.createService(LaunchController.class);
		sendResult(launchResultSTR, launchController);
	}

	private void sendResult(LaunchResultModel launchResultSTR, LaunchController launchController) {
		Call<Void> call = launchController.put(launchResultSTR);
		Response<Void> response;
		try {
			response = call.execute();
			if (!response.isSuccessful()) {
				throw new SmartTestResultClientException(String.format("Failed to post data. %s", response.errorBody().string()));
			}
		} catch (IOException e) {
			throw new SmartTestResultClientException("Failed to post data.", e);
		}
	}

	/**
	 * Number of launches to export.
	 * @param launchResult launch result received by the testing framework.
	 */
	public void putLaunchesResult(LaunchResult launchResult) {
	  List<LaunchResultModel> launchResultModels = launchResult.putLaunchesResult();
	  LaunchController launchController = SmartTestResultClient.createService(LaunchController.class);
	  for (LaunchResultModel model : launchResultModels) {
	      sendResult(model,launchController);
	  }
  }
}
