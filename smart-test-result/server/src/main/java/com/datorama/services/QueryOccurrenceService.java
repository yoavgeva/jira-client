package com.datorama.services;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.datorama.elasticsearch.indicies.TestItemIndex;
import com.datorama.occurrence.ConditionPredicate;
import com.datorama.str.enums.Fields;
import com.datorama.str.enums.FilterTypes;
import com.datorama.str.enums.rules.RulesCondition;
import com.datorama.str.models.TestItemModel;
import com.datorama.str.models.rules.RuleFilterModel;

@Service
public class QueryOccurrenceService implements QueryService {
  Logger logger = LogManager.getLogger(QueryOccurrenceService.class);
  public List<TestItemModel> filter(
      List<SearchHit<TestItemIndex>> searchHits,
      List<RuleFilterModel> ruleFilters,
      String latestLaunchId) {
    List<RuleFilterModel> occurrenceFilters = typeFilters(ruleFilters, FilterTypes.OCCURRENCE);
    Map<UniqueTestItem, List<TestItemIndex>> mapOfTestLists =
        mapOfTestItemHistoryList(searchHits, latestLaunchId);
    Map<UniqueTestItem, List<TestItemIndex>> filteredTestItems =
        occurrencesFilter(occurrenceFilters, mapOfTestLists);
    List<TestItemModel> testItems = new ArrayList<>();
    filteredTestItems.forEach(
        (k, v) -> {
          TestItemModel testItem = new TestItemModel();
          testItem.setName(k.getName());
          testItem.setParameters(k.getTestParameters());
          testItems.add(testItem);
        });
    return testItems;
  }

  private Map<UniqueTestItem, List<TestItemIndex>> occurrencesFilter(
      List<RuleFilterModel> occurrenceFilters,
      Map<UniqueTestItem, List<TestItemIndex>> mapOfTestLists) {
    if (occurrenceFilters.size() == 0) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Must have occurrence filter.");
    }
    Map<UniqueTestItem, List<TestItemIndex>> filteredTestItems =
        filterByOccurrence(occurrenceFilters.get(0), mapOfTestLists);
    for (int i = 1; i < occurrenceFilters.size(); i++) {
      filteredTestItems = filterByOccurrence(occurrenceFilters.get(i), filteredTestItems);
    }

    return filteredTestItems;
  }

  private Map<UniqueTestItem, List<TestItemIndex>> filterByOccurrence(
      RuleFilterModel ruleFilterModel, Map<UniqueTestItem, List<TestItemIndex>> mapOfTestLists) {
    Set<Map.Entry<UniqueTestItem, List<TestItemIndex>>> setOfTestLists = mapOfTestLists.entrySet();
    Optional<Fields> optionalField = Fields.getEnum(ruleFilterModel.getField());
    Fields field = null;
    if (optionalField.isPresent()) {
      field = optionalField.get();
    }
    Integer occurrenceCount = ruleFilterModel.getOccurrence().getOccurrenceCount();
    switch (RulesCondition.getEnum(ruleFilterModel.getCondition()).get()) {
      case ITEMS_SIZE:
        RulesCondition rangeCondition =
            RulesCondition.getEnum(ruleFilterModel.getOccurrence().getCondition()).get();
        mapOfTestLists =
            setOfTestLists.parallelStream()
                .filter(
                    x ->
                        ConditionPredicate.predicate(
                            x.getValue().size(), rangeCondition, occurrenceCount))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        break;
      case GREATER_THAN_OR_EQUALS:
        mapOfTestLists =
            getCollectionByFilterGenericCondition(
                setOfTestLists,
                field,
                RulesCondition.GREATER_THAN_OR_EQUALS,
                ruleFilterModel.getValue(),
                occurrenceCount.longValue());
        break;
      case GREATER_THAN:
        mapOfTestLists =
            getCollectionByFilterGenericCondition(
                setOfTestLists,
                field,
                RulesCondition.GREATER_THAN,
                ruleFilterModel.getValue(),
                occurrenceCount.longValue());
        break;
      case LOWER_THAN:
        mapOfTestLists =
            getCollectionByFilterGenericCondition(
                setOfTestLists,
                field,
                RulesCondition.LOWER_THAN,
                ruleFilterModel.getValue(),
                occurrenceCount.longValue());
        break;
      case LOWER_THAN_OR_EQUALS:
        mapOfTestLists =
            getCollectionByFilterGenericCondition(
                setOfTestLists,
                field,
                RulesCondition.LOWER_THAN_OR_EQUALS,
                ruleFilterModel.getValue(),
                occurrenceCount.longValue());
        break;
      case EQUALS:
        mapOfTestLists =
            getCollectionByFilterGenericCondition(
                setOfTestLists,
                field,
                RulesCondition.EQUALS,
                ruleFilterModel.getValue(),
                occurrenceCount.longValue());
        break;
      case NOT_EQUALS:
        mapOfTestLists =
            getCollectionByFilterGenericCondition(
                setOfTestLists,
                field,
                RulesCondition.NOT_EQUALS,
                ruleFilterModel.getValue(),
                occurrenceCount.longValue());
        break;
      default:
        throw new HttpClientErrorException(
            HttpStatus.BAD_REQUEST,
            String.format(
                "Does not support condition -> %s",
                RulesCondition.getEnum(ruleFilterModel.getCondition()).get()));
    }
    return mapOfTestLists;
  }

  private Map<UniqueTestItem, List<TestItemIndex>> getCollectionByFilterGenericCondition(
      Set<Map.Entry<UniqueTestItem, List<TestItemIndex>>> setOfTestLists,
      Fields field,
      RulesCondition condition,
      String value,
      Long occurrenceCount) {
    return setOfTestLists.parallelStream()
        .filter(
            it -> {
              List<TestItemIndex> list = it.getValue();
              Long count = getTestItemHistoryCount(value, field, list);
              return ConditionPredicate.predicate(count, condition, occurrenceCount);
            })
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  private Long getTestItemHistoryCount(String value, Fields field, List<TestItemIndex> list) {
    return list.parallelStream()
        .filter(testItemIndex -> testItemIndex.getField(field, field.getClz()).equals(value))
        .count();
  }

  private Map<UniqueTestItem, List<TestItemIndex>> mapOfTestItemHistoryList(
      List<SearchHit<TestItemIndex>> searchHits, String latestLaunchId) {
    Map<UniqueTestItem, List<TestItemIndex>> mapOfTestLists = new ConcurrentHashMap<>();
    // mapping the keys based on the latest launch

    searchHits.parallelStream()
        .forEach(
            searchHit -> {
              String testItemName = searchHit.getContent().getName();
              String launchId = searchHit.getContent().getLaunch().getId();
              if (launchId.equals(latestLaunchId)) {
                UniqueTestItem uniqueTestItem =
                    new UniqueTestItem(testItemName, searchHit.getContent().getParameters());
                if (mapOfTestLists.containsKey(uniqueTestItem)) {
                  mapOfTestLists.get(testItemName).add(searchHit.getContent());
                } else {
                  List<TestItemIndex> testItemIndexList = new CopyOnWriteArrayList<>();
                  testItemIndexList.add(searchHit.getContent());
                  mapOfTestLists.put(uniqueTestItem, testItemIndexList);
                }
              }
            });

    // adding the values of other launches
    searchHits.parallelStream()
        .forEach(
            searchHit -> {
              String testItemName = searchHit.getContent().getName();
              String launchId = searchHit.getContent().getLaunch().getId();
              Optional<UniqueTestItem> optionalUniqueTestItem =
                  mapOfTestLists.entrySet().stream()
                      .filter(
                          map ->
                              map.getKey()
                                      .equals(testItemName, searchHit.getContent().getParameters())
                                  && !launchId.equals(latestLaunchId))
                      .findFirst()
                      .map(set -> set.getKey());
              if (optionalUniqueTestItem.isPresent()) {
                mapOfTestLists.get(optionalUniqueTestItem.get()).add(searchHit.getContent());
              }
            });
    return mapOfTestLists;
  }
}
