package com.datorama.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.datorama.services.JiraService;
import com.datorama.str.CommonConstants;
import com.datorama.str.models.bts.BTSModel;
import com.datorama.str.models.bts.CreateIssueModel;
import com.datorama.str.models.bts.IssueModel;
import com.datorama.str.models.bts.UpdateIssueModel;

import net.rcarz.jiraclient.JiraException;

@RestController
@RequestMapping(value = "/issue")
public class IssueController {
  final JiraService jiraService;
  Logger logger = LogManager.getLogger(IssueController.class);

  @Autowired
  public IssueController(JiraService jiraService) {
    this.jiraService = jiraService;
  }

  @RequestMapping(method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public IssueModel getIssue(@RequestParam(CommonConstants.ISSUE_KEY) String issue_id) {
    logger.info(() -> String.format("get issue-id -> %s", issue_id));
    try {
      return jiraActionSuccess(jiraService.getIssue(issue_id));
    } catch (JiraException e) {
      logger.error(() -> String.format("Failed fetching issue error -> %s", e.getMessage()), e);
      return jiraActionFail(e);
    }

  }

  @RequestMapping(method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  public IssueModel createIssue(@RequestBody CreateIssueModel createIssueModel) {
    logger.info(() -> createIssueModel.toString());
    try {
      return jiraActionSuccess(jiraService.createIssue(createIssueModel));
    } catch (JiraException e) {
      logger.error(() -> String.format("Failed creating issue error -> %s", e.getMessage()), e);
      return jiraActionFail(e);
    }
  }

  @RequestMapping(method = RequestMethod.PUT)
  @ResponseStatus(HttpStatus.ACCEPTED)
  public IssueModel updateIssue(@RequestBody UpdateIssueModel updateIssueModel) {
    logger.info(() -> updateIssueModel.toString());
    try {
      return jiraActionSuccess(jiraService.updateIssue(updateIssueModel));
    } catch (JiraException e) {
      logger.error(() -> String.format("Failed updating issue error -> %s", e.getMessage()), e);
      return jiraActionFail(e);
    }
  }

  public IssueModel jiraActionSuccess(IssueModel issueModel) {
    BTSModel btsModel = new BTSModel();

    IssueModel issueModelWithBts = issueModel;
    btsModel.setSuccess(true);
    issueModelWithBts.setBtsIssueModel(btsModel);
    return issueModelWithBts;
  }

  public IssueModel jiraActionFail(JiraException e) {
    BTSModel btsModel = new BTSModel();
    IssueModel issueModel = new IssueModel();
    btsModel.setSuccess(false);
    btsModel.setError(e.getLocalizedMessage());
    issueModel.setBtsIssueModel(btsModel);
    return issueModel;
  }
}
