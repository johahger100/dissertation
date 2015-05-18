package com.ggexjob.qrcode;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by rhoorn on 2015-03-11.
 */
public class ServiceGenerator {
    // No need to instantiate this class.
    private ServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }
}
