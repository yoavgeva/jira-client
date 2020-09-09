//package com.datorama.services;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jettison.json.JSONArray;
//import org.codehaus.jettison.json.JSONException;
//import org.codehaus.jettison.json.JSONObject;
//
//import com.atlassian.jira.rest.client.api.domain.Field;
//import com.atlassian.jira.rest.client.api.domain.FieldSchema;
//import com.atlassian.jira.rest.client.api.domain.IssueField;
//import com.atlassian.jira.rest.client.api.domain.input.ComplexIssueInputFieldValue;
//import com.atlassian.jira.rest.client.api.domain.input.ValueTransformer;
//import com.google.common.collect.ImmutableMap;
//
//public class JiraTransformerFieldValue implements ValueTransformer {
//   Logger logger = LogManager.getLogger(JiraTransformerFieldValue.class);
//  @Override
//  public Object apply(Object rawValue) {
//    System.out.println(rawValue.toString());
//    if (rawValue == null) return null;
//    if(rawValue instanceof JiraField){
//      JiraField jiraField = (JiraField) rawValue;
//      return toJiraObjectValue(jiraField.getIssueField(), jiraField.getField());
//    }
////    if (rawValue instanceof JSONArray) {
////      try {
////        JSONArray array = (JSONArray) rawValue;
////        JSONObject jsonObject = array.getJSONObject(0);
////        List<String> valueList = new ArrayList<>();
////        valueList.add( new ObjectMapper().writeValueAsString(jsonObject.get("value")));
////        return new ComplexIssueInputFieldValue(
////            ImmutableMap.of(
////                "id",
////                jsonObject.get("id").toString(),
////                "value",
////                valueList));
////      } catch (JSONException | IOException e) {
////        System.out.println(e.getMessage());
////        return CANNOT_HANDLE;
////      }
////    }
////    if (rawValue instanceof JSONObject) {
////      try {
////        JSONObject jsonObject = (JSONObject) rawValue;
////        return new ComplexIssueInputFieldValue(
////            ImmutableMap.of(
////                "id",
////                jsonObject.get("id").toString(),
////                "value",
////                jsonObject.get("value").toString()));
////      } catch (JSONException e) {
////        System.out.println(e.getMessage());
////        return CANNOT_HANDLE;
////      }
////    }
//
//    return CANNOT_HANDLE;
//  }
//
//  public  Object toJiraObjectValue(IssueField value, Field field) {
//    FieldSchema schema = field.getSchema();
//    Object objectValue = value.getValue();
//    logger.info("value " + value.getValue().toString());
//    logger.info("field " + field.toString());
//    if (schema.isCustom()) {
//      if (schema.getCustom().contains("multi")) {
//        HashMap<String, Object> hashMap = new HashMap<>();
//        if (objectValue instanceof JSONArray) {
//          try {
//            JSONArray array = (JSONArray) objectValue;
//            JSONObject jsonObject = array.getJSONObject(0);
//            boolean isValue = jsonObject.has("value");
//            boolean isName = jsonObject.has("name");
//            if (isName) {
//              hashMap.put("name", jsonObject.get("name"));
//              return new ComplexIssueInputFieldValue(hashMap);
//            }
//            if (isValue) {
//              hashMap.put("value", jsonObject.get("value"));
//              return new ComplexIssueInputFieldValue(hashMap);
//            }
//          } catch (JSONException e) {
//            return ValueTransformer.CANNOT_HANDLE;
//          }
//        }
//
//      } else {
//        try {
//          JSONObject jsonObject = (JSONObject) objectValue;
//          boolean isValue = jsonObject.has("value");
//          boolean isName = jsonObject.has("name");
//          if (isName) {
//            return ComplexIssueInputFieldValue.with("name", jsonObject.get("name"));
//          }
//          if (isValue) {
//            return ComplexIssueInputFieldValue.with("value", jsonObject.get("value"));
//          }
//        } catch (JSONException e) {
//          return ValueTransformer.CANNOT_HANDLE;
//        }
//      }
//    }
//    return ValueTransformer.CANNOT_HANDLE;
//  }
//}
