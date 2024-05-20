package com.example.diplom.Api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsApiClient {
    private static final String BASE_URL = "https://newsapi.org/";
    private static final String API_KEY = "69250c7c9bd64e11bb8ee49ac0a28c23";

    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static NewsApiService getService() {
        return getClient().create(NewsApiService.class);
    }

    public static String getApiKey() {
        return API_KEY;
    }
}
