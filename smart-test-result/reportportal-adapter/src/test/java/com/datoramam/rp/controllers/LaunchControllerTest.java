package com.datoramam.rp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.datorama.rp.client.ReportPortalClientConfiguration;
import com.datorama.rp.filter.Condition;
import com.datorama.rp.filter.FilterCriteria;
import com.datorama.rp.filter.QueryMapReportPortal;
import com.datorama.rp.filter.QueryPageReportPortal;
import com.datorama.rp.str.adapters.LaunchResultReportPortal;
import com.datorama.str.SmartTestResultConfiguration;
import com.datorama.str.enums.Fields;
import com.datorama.str.enums.Indices;
import com.datorama.str.enums.rules.RulesCondition;
import com.datorama.str.filter.data.ConditionDataFilter;
import com.datorama.str.filter.data.DataFilter;
import com.datorama.str.filter.data.LastLaunchesDataFilter;
import com.datorama.str.filter.occurence.ConditionOccurrenceFilter;
import com.datorama.str.filter.occurence.NewTestOccurrenceFilter;
import com.datorama.str.filter.occurence.OccurrenceFilter;
import com.datorama.str.models.ResultResponseModel;
import com.datorama.str.services.ExportData;
import com.datorama.str.services.ResultService;
import com.datorama.str.services.RuleFilters;

 @Ignore
public class LaunchControllerTest {

  @BeforeClass
  public static void beforeClass() {
    SmartTestResultConfiguration.create("http://localhost:8080", "", true);
  }

  @Ignore
  @Test
  public void getLaunchTest() {
    ReportPortalClientConfiguration.create(
        "https://report-portal.datorama.io", "a8be570d-46ed-42ca-9612-63c177f71702", true);
    QueryMapReportPortal queryMapRP = new QueryMapReportPortal();
    queryMapRP
        .addFilter(FilterCriteria.name, Condition.EQUALS, "pre_prod_deploy_and_test_Ui")
        .addFilter(FilterCriteria.status,Condition.NOT_IN,"IN_PROGRESS,INTERRUPTED")
        .addQueryPage(new QueryPageReportPortal().sortPage("start_time,desc"));
    LaunchResultReportPortal testItemsRP = new LaunchResultReportPortal("regression", queryMapRP);
    testItemsRP.setNumberOfLaunches(5);
    ExportData exportDataToSTR = new ExportData();
    exportDataToSTR.putLaunchesResult(testItemsRP);

  }

  @Test
  public void getQueryResult() {
    //TODO better wrapper and builders
    ResultService result = new ResultService();
    List<DataFilter> dataFilters = new ArrayList<>();
    List<OccurrenceFilter> occurrenceFilters = new ArrayList<>();
    LastLaunchesDataFilter lastLaunchesDataFilter = new LastLaunchesDataFilter(3);
    ConditionDataFilter testTypeFilter = new ConditionDataFilter(Indices.TEST_ITEM);
    testTypeFilter.filter(Fields.TYPE, RulesCondition.EQUALS,"STEP");
    NewTestOccurrenceFilter newTestsFilter = new NewTestOccurrenceFilter(2);
    ConditionOccurrenceFilter failedTestOccurrence = new ConditionOccurrenceFilter(RulesCondition.GREATER_THAN_OR_EQUALS,1);
    failedTestOccurrence.filterEquals(Fields.STATUS,"FAILED");
    dataFilters.add(lastLaunchesDataFilter);
    dataFilters.add(testTypeFilter);
    occurrenceFilters.add(newTestsFilter);
    occurrenceFilters.add(failedTestOccurrence);
    RuleFilters ruleFilters = new RuleFilters(dataFilters, occurrenceFilters);
    ResultResponseModel response = result.getResultByClientRule(ruleFilters);
    System.out.println("size:" + response.getTestItems().size());
    response.getTestItems().forEach(System.out::println);
  }
}
