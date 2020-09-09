package com.datorama.config.dev;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.datorama")
@Profile("dev")
public class ElasticSearchConfigDev extends AbstractElasticsearchConfiguration {
	@Value("${db.elasticsearch.url}")
	private String elasticSearchURL;

	@Autowired
	private Environment environment;

	@Lazy
	@Bean
	public ElasticsearchOperations esOperations() {
		return new ElasticsearchRestTemplate(elasticsearchClient());
	}

	@Override public RestHighLevelClient elasticsearchClient() {
		String url = environment.getProperty("ELASTICSEARCH_URL", "");
		if(url.isEmpty()){
			url = elasticSearchURL;
		}

		HttpHost httpHost = HttpHost.create(url);
		RestClientBuilder builder = RestClient.builder(httpHost);
		//maybe add creds
		return new RestHighLevelClient(builder);
	}

	@Bean(destroyMethod = "close")
	public RestClient restClient() {
		return elasticsearchClient().getLowLevelClient();
	}
}
