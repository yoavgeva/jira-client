package com.datorama.services;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.datorama.str.controllers.bts.IssueController;
import com.datorama.str.models.bts.CreateIssueModel;
import com.datorama.str.models.bts.IssueModel;
import com.datorama.str.models.bts.UpdateIssueModel;

import retrofit2.Call;
import retrofit2.Response;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BTSRestService {
	final DiscoveryClient discoveryClient;
	final RestService restService;
	Logger logger = LogManager.getLogger(BTSRestService.class);
	private Boolean checkedBTS = false;
	private Optional<URI> uri = Optional.empty();

	@Autowired
	public BTSRestService(DiscoveryClient discoveryClient, RestService restService) {
		this.discoveryClient = discoveryClient;
		this.restService = restService;
	}

	public Optional<IssueModel> create(Supplier<CreateIssueModel> createIssueModelSupplier) {
		if (serviceUrl().isEmpty()) {
			return Optional.empty();
		}
		try {
			URI uriBTS = uri.get();
			String url = uriBTS.toURL().getPath();
			IssueController issueController = restService.getRest(url).create(IssueController.class);
			Call<IssueModel> issueModel = issueController.createIssue(createIssueModelSupplier.get());
			Response<IssueModel> response = issueModel.execute();
			return Optional.of(response.body());
		} catch (IOException e) {
			logger.warn(() -> String.format("Failed creating BTS issue -> %s", e.getMessage()));
			return Optional.empty();
		}
	}

	public Optional<IssueModel> update(Supplier<UpdateIssueModel> updateIssueModelSupplier) {
		if (serviceUrl().isEmpty()) {
			return Optional.empty();
		}
			try {
				URI uriBTS = uri.get();
				String url = uriBTS.toURL().getPath();
				IssueController issueController = restService.getRest(url).create(IssueController.class);
				Call<IssueModel> issueModel = issueController.updateIssue(updateIssueModelSupplier.get());
				Response<IssueModel> response = issueModel.execute();
				return Optional.of(response.body());
			} catch (IOException e) {
				logger.warn(() -> String.format("Failed updating BTS issue -> %s", e.getMessage()));
				return Optional.empty();
			}

	}

	public Optional<IssueModel> read(Supplier<String>  issueId) {
		if (serviceUrl().isEmpty()) {
			return Optional.empty();
		}
		try {
			URI uriBTS = uri.get();
			String url = uriBTS.toURL().getPath();
			IssueController issueController = restService.getRest(url).create(IssueController.class);
			Call<IssueModel> issueModel = issueController.getIssue(issueId.get());
			Response<IssueModel> response = issueModel.execute();
			return Optional.of(response.body());
		} catch (IOException e) {
			logger.warn(() -> String.format("Failed updating BTS issue -> %s", e.getMessage()));
			return Optional.empty();
		}
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
