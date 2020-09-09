package com.datorama.rp.client;

import com.datorama.str.exceptions.SmartTestResultClientException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ReportPortalClient {
	private static final Retrofit.Builder builder
			= new Retrofit.Builder()
			.baseUrl(configuration().getBaseURL())
			.addConverterFactory(JacksonConverterFactory.create());
	private static final OkHttpClient.Builder httpClient
			= new OkHttpClient.Builder();
	private static final HttpLoggingInterceptor logging
			= new HttpLoggingInterceptor()
			.setLevel(HttpLoggingInterceptor.Level.BASIC);
	private static Retrofit retrofit = builder.build();

	private static ReportPortalClientConfiguration configuration() {
		return ReportPortalClientConfiguration.getInstance();
	}

	public static <S> S createServiceWithToken(Class<S> serviceClass) {
		httpClient.interceptors().clear();
		if (!httpClient.interceptors().contains(logging) && configuration().isShowLogs()) {
			httpClient.addInterceptor(logging);
		}
		if (configuration().getToken() != null) {
			httpClient.addInterceptor(chain -> {
				Request original = chain.request();
				Request.Builder builder1 = original.newBuilder()
						.header("Accept","application/json")
						.header("Authorization", String.format("bearer %s", configuration().getToken()));
				Request request = builder1.build();
				return chain.proceed(request);
			});
			builder.client(httpClient.build());
			retrofit = builder.build();
		} else {
			throw new SmartTestResultClientException("Token is null, use configuration to set token.");
		}
		return retrofit.create(serviceClass);
	}
}
