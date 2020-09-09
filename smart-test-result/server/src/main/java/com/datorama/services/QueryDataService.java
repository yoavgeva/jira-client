package com.datorama.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.datorama.elasticsearch.indicies.LaunchIndex;
import com.datorama.elasticsearch.indicies.TestItemIndex;
import com.datorama.elasticsearch.query.DatabaseQuery;
import com.datorama.elasticsearch.repository.LaunchNativeRepository;
import com.datorama.elasticsearch.repository.TestItemNativeRepository;
import com.datorama.str.CommonConstants;
import com.datorama.str.enums.FilterTypes;
import com.datorama.str.enums.Indices;
import com.datorama.str.enums.rules.RulesCondition;
import com.datorama.str.models.rules.RuleFilterModel;

@Service
public class QueryDataService implements QueryService {
  final LaunchNativeRepository launchNativeRepository;
  final TestItemNativeRepository testItemNativeRepository;
  Logger logger = LogManager.getLogger(QueryDataService.class);

  @Autowired
  public QueryDataService(
      LaunchNativeRepository launchNativeRepository,
      TestItemNativeRepository testItemNativeRepository) {
    this.launchNativeRepository = launchNativeRepository;
    this.testItemNativeRepository = testItemNativeRepository;
  }

  public QueryDataResult filter(List<RuleFilterModel> filterModelList) {
    List<RuleFilterModel> dataFilters = typeFilters(filterModelList, FilterTypes.DATA);
    List<RuleFilterModel> launchFilters = IndexFilter(dataFilters, Indices.LAUNCH);
    DatabaseQuery launchDatabaseQuery = new DatabaseQuery();
    List<SearchHit<LaunchIndex>> launchHits =
        launchNativeRepository.search(esQuery(launchFilters, launchDatabaseQuery));
    // TODO need to add validator for return entities with size bigger than 0
    logger.debug("launches hit size: {}", () -> launchHits.size());
    List<String> launchIds = launchHits.stream().map(SearchHit::getId).collect(Collectors.toList());
    DatabaseQuery testItemDatabaseQuery = new DatabaseQuery();
    testItemDatabaseQuery.matchAny(
        String.format("%s.%s.%s", CommonConstants.LAUNCH, CommonConstants.ID,CommonConstants.KEYWORD), launchIds);
    List<RuleFilterModel> testItemFilters = IndexFilter(dataFilters, Indices.TEST_ITEM);
    List<SearchHit<TestItemIndex>> testItemHits =
        testItemNativeRepository.search(esQuery(testItemFilters, testItemDatabaseQuery));
    logger.debug("test items hit size: {}", () -> testItemHits.size());
    String latestLaunchId =
        launchHits.stream()
            .sorted(
                Comparator.comparing(
                    launchIndexSearchHit -> launchIndexSearchHit.getContent().getStartTime(),
                    Comparator.nullsLast(Comparator.reverseOrder())))
            .findFirst()
            .get()
            .getId();
    QueryDataResult result = new QueryDataResult(latestLaunchId, testItemHits);
    logger.debug("latest launch id -> {}",() -> result.getLatestLaunchId());
    return result;
  }

  private List<RuleFilterModel> IndexFilter(List<RuleFilterModel> filterModelList, Indices index) {
    List<RuleFilterModel> filtersModels = new ArrayList<>();
    filterModelList.forEach(
        it -> {
          if (it.getIndexName().equals(index.getValue())) {
            filtersModels.add(it);
          }
        });
    return filtersModels;
  }

  private NativeSearchQuery esQuery(List<RuleFilterModel> modelList, DatabaseQuery databaseQuery) {
    modelList.forEach(
        it -> {
          if (ObjectUtils.isNotEmpty(it.getRange())) {
            range(databaseQuery, it);
          } else if (ObjectUtils.isNotEmpty(it.getAttributes())) {
            condition(
                databaseQuery,
                RulesCondition.getEnum(it.getCondition()).get(),
                String.format("%s.%s", CommonConstants.ATTRIBUTES, it.getAttributes().getKey()),
                String.format("%s.%s", CommonConstants.ATTRIBUTES, it.getAttributes().getValue()));
          } else if (StringUtils.isNotEmpty(it.getCondition())) {
            condition(
                databaseQuery,
                RulesCondition.getEnum(it.getCondition()).get(),
                it.getField(),
                it.getValue());
          } else {
            throw new HttpClientErrorException(
                HttpStatus.BAD_REQUEST, String.format("Filter unsupported -> %s", it.toString()));
          }
        });
    return databaseQuery.create();
  }

  private void range(DatabaseQuery databaseQuery, RuleFilterModel it) {
    if (it.getRange().getOccurrenceCount() > 0) {
      databaseQuery.sort(it.getField(), it.getRange().getOccurrenceCount(), SortOrder.DESC);
      return;
    }
    if (ObjectUtils.isNotEmpty(it.getRange().getNewestDate())) {
      databaseQuery.between(
          it.getField(), it.getRange().getOldestDate(), it.getRange().getNewestDate());
      return;
    }
  }

  private void condition(
      DatabaseQuery databaseQuery, RulesCondition condition, String field, String value) {
    switch (condition) {
      case CONTAINS:
        databaseQuery.contains(field, value);
        break;
      case EQUALS:
        databaseQuery.equals(field, value);
        break;
      case NOT_EQUALS:
        databaseQuery.notEquals(field, value);
        break;
      case GREATER_THAN_OR_EQUALS:
        databaseQuery.greaterThanOrEquals(field, value);
        break;
      case GREATER_THAN:
        databaseQuery.greaterThan(field, value);
        break;
      case LOWER_THAN:
        databaseQuery.lowerThan(field, value);
        break;
      case LOWER_THAN_OR_EQUALS:
        databaseQuery.lowerThanOrEquals(field, value);
        break;
      default:
        throw new HttpClientErrorException(
            HttpStatus.BAD_REQUEST,
            String.format("Condition unsupported -> %s", condition.getValue()));
    }
  }
}
