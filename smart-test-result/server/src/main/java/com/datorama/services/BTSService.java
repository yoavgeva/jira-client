package com.datorama.services;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Optional;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.datorama.str.CommonConstants;
import com.datorama.str.models.bts.CreateIssueModel;
import com.datorama.str.models.bts.IssueModel;
import com.datorama.str.models.bts.UpdateIssueModel;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BTSService {
  final DiscoveryClient discoveryClient;
  final RestTemplate restClient;
  Logger logger = LogManager.getLogger(BTSService.class);
  private Boolean checkedBTS = false;
  private Optional<URI> uri = Optional.empty();

  @Autowired
  public BTSService(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
    this.discoveryClient = discoveryClient;
    this.restClient = restTemplate;
  }

  public Optional<String> create(Supplier<CreateIssueModel> createIssueModelSupplier) {
    if (serviceUrl().isEmpty()) {
      return Optional.empty();
    }
    URI uriBTS = uri.get();
    HttpEntity<CreateIssueModel> request = new HttpEntity<>(createIssueModelSupplier.get());
    ResponseEntity<IssueModel> response;
    try {
      response =
          restClient.exchange(
              String.format("%s/%s", uriBTS.toURL().toString(), "/issue"),
              HttpMethod.POST,
              request,
              IssueModel.class);

      return Optional.of(response.getBody().getIssueKey());

    } catch (MalformedURLException e) {
      logger.warn(() -> String.format("Failed creating BTS issue -> %s", e.getMessage()));
      return Optional.empty();
    } catch (HttpClientErrorException exception) {
      logger.warn(
          () ->
              String.format(
                  "Failed creating BTS issue -> %s", exception.getResponseBodyAsString()));
      return Optional.empty();
    } catch (HttpStatusCodeException exception) {
      logger.warn(
          () ->
              String.format(
                  "Failed creating BTS issue -> %s", exception.getResponseBodyAsString()));
      return Optional.empty();
    }
  }

  public void update(Supplier<UpdateIssueModel> updateIssueModelSupplier) {
    if (!serviceUrl().isEmpty()) {
      URI uriBTS = uri.get();
      HttpEntity<UpdateIssueModel> request = new HttpEntity<>(updateIssueModelSupplier.get());
      try {
        restClient.postForLocation(
            String.format("%s/%s", uriBTS.toURL().toString(), "/issue"), request);

      } catch (MalformedURLException e) {
        logger.warn(() -> String.format("Failed updating BTS issue -> %s", e.getMessage()));
      } catch (HttpClientErrorException exception) {
        logger.warn(
            () ->
                String.format(
                    "Failed updating BTS issue -> %s", exception.getResponseBodyAsString()));
      } catch (HttpStatusCodeException exception) {
        logger.warn(
            () ->
                String.format(
                    "Failed updating BTS issue -> %s", exception.getResponseBodyAsString()));
      }
    }
  }

  public Optional<IssueModel> read(String issueId) {
    if (serviceUrl().isEmpty()) {
      return Optional.empty();
    }
    URI uriBTS = uri.get();
    ResponseEntity<IssueModel> response;
    try {
     response = restClient.exchange(
              String.format("%s/%s/{%s}", uriBTS.toURL().toString(), "/issue", CommonConstants.ISSUE_KEY), HttpMethod.GET,null,IssueModel.class,issueId);
      return Optional.of(response.getBody());
    } catch (MalformedURLException e) {
      logger.warn(() -> String.format("Failed fetching BTS issue -> %s", e.getMessage()));
    } catch (HttpClientErrorException exception) {
      logger.warn(
              () ->
                      String.format(
                              "Failed fetching BTS issue -> %s", exception.getResponseBodyAsString()));
    } catch (HttpStatusCodeException exception) {
      logger.warn(
              () ->
                      String.format(
                              "Failed fetching BTS issue -> %s", exception.getResponseBodyAsString()));
    }
    return Optional.empty();
  }

  private Optional<URI> serviceUrl() {
    if (!checkedBTS) {
      checkedBTS = true;
      Optional<String> serviceName =
          discoveryClient.getServices().stream()
              .filter(service -> service.contains("bts"))
              .findFirst();
      if (!serviceName.isPresent()) {
        uri = Optional.empty();
      } else {
        uri =
            discoveryClient.getInstances(serviceName.get()).stream()
                .findFirst()
                .map(serviceInstance -> serviceInstance.getUri());
      }
    }
    return uri;
  }


}
