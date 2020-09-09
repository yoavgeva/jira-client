package com.datorama.rp.str.adapters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import com.datorama.rp.client.ReportPortalClient;
import com.datorama.rp.controllers.LaunchController;
import com.datorama.rp.controllers.TestItemController;
import com.datorama.rp.filter.Condition;
import com.datorama.rp.filter.FilterCriteria;
import com.datorama.rp.filter.QueryMapReportPortal;
import com.datorama.rp.filter.QueryPageReportPortal;
import com.datorama.rp.models.LaunchesResponse;
import com.datorama.rp.models.TestItemsResponse;
import com.datorama.str.adapters.LaunchResult;
import com.datorama.str.exceptions.SmartTestResultClientException;
import com.datorama.str.models.LaunchModel;
import com.datorama.str.models.LaunchResultModel;
import com.datorama.str.models.TestItemModel;
import com.datorama.str.models.TestParameters;
import com.epam.ta.reportportal.ws.model.ParameterResource;
import com.epam.ta.reportportal.ws.model.TestItemResource;
import com.epam.ta.reportportal.ws.model.launch.LaunchResource;

import retrofit2.Call;
import retrofit2.Response;

public class LaunchResultReportPortal implements LaunchResult {
  private final QueryMapReportPortal launchQueryMap;
  private final String reportPortalProjectName;
  private int numberOfLaunches = 0;

  public LaunchResultReportPortal(String reportPortalProjectName, QueryMapReportPortal launchQueryMap) {
    this.launchQueryMap = launchQueryMap;
    this.reportPortalProjectName = reportPortalProjectName;
  }

  @Override
  public LaunchResultModel putLatestLaunchResult() {
    LaunchResource launch = getRPLaunch(0);
    List<TestItemResource> testItemResourceList = getTestItemsFromRP(launch);
    List<TestItemModel> testItemSTRList = getSTRTestItems(testItemResourceList);
    LaunchModel launchModel = getLaunchSTR(launch);
    LaunchResultModel launchResultSTR = new LaunchResultModel();
    launchResultSTR.setTestItems(testItemSTRList);
    launchResultSTR.setLaunch(launchModel);
    return launchResultSTR;
  }

  @Override
  public List<LaunchResultModel> putLaunchesResult() {
    List<LaunchResultModel> modelsList = new ArrayList<>();
    for (int i = 0; i <= numberOfLaunches-1; i++) {
      LaunchResource launch = getRPLaunch(i);
      List<TestItemResource> testItemResourceList = getTestItemsFromRP(launch);
      List<TestItemModel> testItemSTRList = getSTRTestItems(testItemResourceList);
      LaunchModel launchModel = getLaunchSTR(launch);
      LaunchResultModel launchResultModel = new LaunchResultModel();
      launchResultModel.setTestItems(testItemSTRList);
      launchResultModel.setLaunch(launchModel);
      modelsList.add(launchResultModel);
    }
    return modelsList;
  }

  public void setNumberOfLaunches(int numberOfLaunches) {
    this.numberOfLaunches = numberOfLaunches;
  }

  private LaunchModel getLaunchSTR(LaunchResource launch) {
    LaunchModel launchSTR = new LaunchModel();
    launchSTR.setEndTime(launch.getEndTime());
    launchSTR.setItemId(launch.getLaunchId());
    launchSTR.setName(launch.getName());
    launchSTR.setStatus(launch.getStatus());
    launchSTR.setStartTime(launch.getStartTime());
    launchSTR.setDescription(launch.getDescription());
    launchSTR.setTags(launch.getTags());
    return launchSTR;
  }

  private List<TestItemModel> getSTRTestItems(List<TestItemResource> testItemResourceList) {
    List<TestItemModel> testItemSTRList = new ArrayList<>();
    testItemResourceList.forEach(
        it -> {
          TestItemModel testItemSTR = convertTestItemFromRPToSTR(it);
          testItemSTR.setRetries(convertRetriesFromRPToSTR(it.getRetries()));
          testItemSTRList.add(testItemSTR);
        });
    return testItemSTRList;
  }

  private List<TestItemModel> convertRetriesFromRPToSTR(List<TestItemResource> listTestItems) {
    if (ObjectUtils.isEmpty(listTestItems)) {
      return Collections.EMPTY_LIST;
    }
    List<TestItemModel> retires = new ArrayList<>();
    listTestItems.forEach(
        it -> {
          retires.add(convertTestItemFromRPToSTR(it));
        });
    return retires;
  }

  @NotNull
  private TestItemModel convertTestItemFromRPToSTR(TestItemResource testItemResource) {
    TestItemModel testItemSTR = new TestItemModel();
    testItemSTR.setEndTime(testItemResource.getEndTime());
    testItemSTR.setStartTime(testItemResource.getStartTime());
    testItemSTR.setDescription(StringUtils.defaultString(testItemResource.getDescription(), ""));
    testItemSTR.setItemId(testItemResource.getItemId());
    testItemSTR.setName(testItemResource.getName());
    testItemSTR.setTags(
        ObjectUtils.defaultIfNull(testItemResource.getTags(), Collections.EMPTY_SET));
    testItemSTR.setStatus(testItemResource.getStatus());
    testItemSTR.setParameters(convertParameterFromRPToSTR(testItemResource.getParameters()));
    testItemSTR.setType(testItemResource.getType());
    return testItemSTR;
  }

  private List<TestParameters> convertParameterFromRPToSTR(List<ParameterResource> parameters) {
    if (ObjectUtils.isEmpty(parameters)) {
      return Collections.emptyList();
    }
    List<TestParameters> parametersSTRList = new ArrayList<>();
    parameters.forEach(
        it -> {
          TestParameters testParametersSTR = new TestParameters();
          testParametersSTR.setKey(it.getKey());
          testParametersSTR.setValue(it.getValue());
          parametersSTRList.add(testParametersSTR);
        });
    return parametersSTRList;
  }

  private List<TestItemResource> getTestItemsFromRP(LaunchResource launch) {
    TestItemController testItemController =
        ReportPortalClient.createServiceWithToken(TestItemController.class);
    QueryMapReportPortal testItemsQueryRP = new QueryMapReportPortal();
    QueryPageReportPortal queryPageRP = new QueryPageReportPortal();
    queryPageRP.numberOfPage(1).sizeOfPage(300);
    testItemsQueryRP
        .addFilter(FilterCriteria.launch, Condition.EQUALS, launch.getLaunchId())
        .addQueryPage(queryPageRP);
    Call<TestItemsResponse> getFirstPageTestItems =
        testItemController.getTestItems(reportPortalProjectName, testItemsQueryRP.toMap());
    Response<TestItemsResponse> response;
    List<TestItemResource> testItemResourceList = new ArrayList<>();
    try {
      response = getFirstPageTestItems.execute();
      if (response.isSuccessful()) {
        TestItemsResponse responseBody = response.body();
        testItemResourceList.addAll(responseBody.getTestItemsResourceList());
        for (int i = 2; i <= responseBody.getPage().getTotalPages(); i++) {
          queryPageRP.numberOfPage(i);
          testItemsQueryRP.addQueryPage(queryPageRP);
          Call<TestItemsResponse> getPerPageTestItems =
              testItemController.getTestItems(reportPortalProjectName, testItemsQueryRP.toMap());
          response = getPerPageTestItems.execute();
          if (response.isSuccessful()) {
            testItemResourceList.addAll(response.body().getTestItemsResourceList());
          } else {
            throw new SmartTestResultClientException(
                String.format("Failed in getting launch data. %s", response.errorBody().string()));
          }
        }
      } else {
        throw new SmartTestResultClientException(
            String.format("Failed in getting launch data. %s", response.errorBody().string()));
      }
    } catch (IOException e) {
      throw new SmartTestResultClientException("Failed in getting test items data.", e);
    }
    return testItemResourceList;
  }

  /** @return always use the first it received, filter or sort to get the one you want. */
  private LaunchResource getRPLaunch(int number) {
    LaunchController launchController =
        ReportPortalClient.createServiceWithToken(LaunchController.class);
    Call<LaunchesResponse> getUiTestLaunch =
        launchController.getLaunches(reportPortalProjectName, launchQueryMap.toMap());
    Response<LaunchesResponse> response;
    try {
      response = getUiTestLaunch.execute();
      if (response.isSuccessful()) {
        return response.body().getLaunchResourceList().get(number);
      } else {
        throw new SmartTestResultClientException(
            String.format("Failed in getting launch data. %s", response.errorBody().string()));
      }
    } catch (IOException e) {
      throw new SmartTestResultClientException("Failed in getting launch data.", e);
    }
  }
}
