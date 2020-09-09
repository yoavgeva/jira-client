package com.datorama.config;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.IssueType;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

@Configuration
public class JiraConfig {
  final Environment environment;
  Logger logger = LogManager.getLogger(JiraConfig.class);

  @Autowired
  public JiraConfig(Environment environment) {
    this.environment = environment;
  }

  @Bean
  JiraClient jiraClient() {
    String user = environment.getRequiredProperty("JIRA_USER");
    String url = environment.getRequiredProperty("JIRA_URL");
    String password = environment.getRequiredProperty("JIRA_PASSWORD");
    BasicCredentials basicCredentials = new BasicCredentials(user, password);
    try {
      return new JiraClient(url, basicCredentials);
    } catch (JiraException e) {
      logger.error("Illegal Jira credentials", e);
      throw new IllegalArgumentException(
          String.format("Illegal Jira credentials  -> %s", e.getMessage()));
    }
  }

  @Bean
  IssueType bug(@Autowired JiraClient client) throws JiraException {
    List<IssueType> issueTypesList = client.getIssueTypes();

    return issueTypesList.stream()
        .filter(it -> it.getName().equals("Bug"))
        .findFirst()
        .orElseThrow();
  }
}
