package com.datorama.config.prod;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.datorama.elasticsearch")
@ComponentScan(basePackages = {"com.datorama"})
@Profile("prod")
public class ElasticSearchConfigProd {

	@Autowired
	private Environment environment;

	@Bean
	public RestHighLevelClient client() {
		ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo(environment.getProperty("ELASTICSEARCH_URL")).build();
		return RestClients.create(clientConfiguration).rest();
	}

	@Lazy
	@Bean
	public ElasticsearchOperations esOperations() {
		return new ElasticsearchRestTemplate(client());
	}
}
