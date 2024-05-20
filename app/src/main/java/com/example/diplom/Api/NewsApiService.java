package com.example.diplom.Api;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {
    @GET("v2/top-headlines")
    Observable<NewsApiResponse> getTopHeadlines(
            @Query("category") String category,
            @Query("country") String country,
            @Query("apiKey") String apiKey,
            @Query("page") int page
    );

    @GET("v2/top-headlines")
    Observable<NewsApiResponse> getSource(
            @Query("country") String country,
            @Query("apiKey") String apiKey,
            @Query("page") int page
    );

    @GET("v2/sources")
    Call<NewsApiResponse> getNewsSources(
            @Query("apiKey") String apiKey,
            @Query("page") int page
    );

    @GET("v2/top-headlines")
    Observable<NewsApiResponse> getNews(
            @Query("sources") String source,
            @Query("apiKey") String apiKey,
            @Query("page") int page
    );

    @GET("v2/top-headlines")
    Observable<NewsApiResponse> getNewsByName(
            @Query("sources") String sourceName,
            @Query("apiKey") String apiKey
    );
}
