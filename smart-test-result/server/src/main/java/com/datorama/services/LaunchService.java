package com.datorama.services;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datorama.elasticsearch.indicies.LaunchIndex;
import com.datorama.elasticsearch.indicies.TestItemIndex;
import com.datorama.elasticsearch.repository.LaunchCRUDRepository;
import com.datorama.elasticsearch.repository.TestItemCRUDRepository;
import com.datorama.str.models.LaunchModel;
import com.datorama.str.models.LaunchResultModel;

@Service
public class LaunchService {
	final TestItemCRUDRepository testItemCRUDRepository;
	final LaunchCRUDRepository launchCRUDRepository;

	@Autowired
	public LaunchService(TestItemCRUDRepository testItemCRUDRepository, LaunchCRUDRepository launchCRUDRepository) {
		this.testItemCRUDRepository = testItemCRUDRepository;
		this.launchCRUDRepository = launchCRUDRepository;
	}

	public void createIndices(LaunchResultModel launchResultSTR) {
		LaunchIndex launchIndex = getLaunchIndex(launchResultSTR.getLaunch());
		LaunchIndex indexedLaunch = launchCRUDRepository.save(launchIndex);
		List<TestItemIndex> itemIndices = getTestItemsIndices(launchResultSTR,indexedLaunch.getId());
		testItemCRUDRepository.saveAll(itemIndices);

	}

	private LaunchIndex getLaunchIndex(LaunchModel launch) {
		LaunchIndex launchIndex = new LaunchIndex();
		launchIndex.setAttributes(launch.getAttributes());
		launchIndex.setDescription(launch.getDescription());
		launchIndex.setStatus(launch.getStatus());
		launchIndex.setEnvironment(launch.getEnvironment());
		launchIndex.setTags(launch.getTags());
		launchIndex.setName(launch.getName());
		launchIndex.setItemId(launch.getItemId());
		launchIndex.setStartTime(launch.getStartTime());
		launchIndex.setEndTime(launch.getEndTime());
		return launchIndex;
	}

	private List<TestItemIndex> getTestItemsIndices(LaunchResultModel launchResultSTR,String launchId) {
		List<TestItemIndex> testItemIndices = new CopyOnWriteArrayList<>();
		launchResultSTR.getTestItems().parallelStream().forEach(it -> {
			TestItemIndex testItemIndex = new TestItemIndex();
			testItemIndex.setAttributes(it.getAttributes());
			testItemIndex.setDescription(it.getDescription());
			testItemIndex.setTestId(it.getItemId());
			testItemIndex.setEndTime(it.getEndTime());
			testItemIndex.setStartTime(it.getStartTime());
			testItemIndex.setName(it.getName());
			testItemIndex.setStatus(it.getStatus());
			testItemIndex.setParameters(it.getParameters());
			testItemIndex.setType(it.getType());
			testItemIndex.setTags(it.getTags());
			testItemIndex.setRetries(it.getRetries());
			launchResultSTR.getLaunch().setId(launchId);
			testItemIndex.setLaunch(launchResultSTR.getLaunch());
			testItemIndices.add(testItemIndex);
		});
		return testItemIndices;
	}
}
