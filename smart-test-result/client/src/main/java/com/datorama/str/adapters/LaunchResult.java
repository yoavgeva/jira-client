package com.datorama.str.adapters;

import java.util.List;

import com.datorama.str.models.LaunchResultModel;

public interface LaunchResult {
	LaunchResultModel putLatestLaunchResult();
	List<LaunchResultModel> putLaunchesResult();
}
