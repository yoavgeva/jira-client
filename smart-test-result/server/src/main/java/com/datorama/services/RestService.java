package com.datorama.services;

import org.springframework.stereotype.Service;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Service
public class RestService {
	private Retrofit retrofit;

	private Retrofit create(String url) {
		OkHttpClient.Builder httpClient
				= new OkHttpClient.Builder();
		Retrofit.Builder builder
				= new Retrofit.Builder()
				.baseUrl(url)
				.addConverterFactory(JacksonConverterFactory.create())
				.client(httpClient.build());
		retrofit = builder.build();
		return retrofit;
	}

	public Retrofit getRest(String baseUrl) {
		if (retrofit.baseUrl().toString().equals(baseUrl)) {
			return retrofit;
		} else {
			return create(baseUrl);
		}
	}
}
