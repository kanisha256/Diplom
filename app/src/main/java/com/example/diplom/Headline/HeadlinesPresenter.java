package com.example.diplom.Headline;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.diplom.Api.NewsApiClient;
import com.example.diplom.Api.NewsApiService;
import com.example.diplom.Api.NewsLoader;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@InjectViewState
public class HeadlinesPresenter extends MvpPresenter<HeadlinesView> {
    public static void loadTopHeadlines(String category, int page, NewsLoader.NewsLoaderCallback callback) {
        NewsApiService apiService = NewsApiClient.getService();
        apiService.getTopHeadlines(category, "us",NewsApiClient.getApiKey(), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (callback != null) {
                                callback.onNewsLoaded(response.getArticles());
                            }
                        },
                        throwable -> {
                            if (callback != null) {
                                callback.onNewsLoadError(throwable.getMessage());
                            }
                        }
                );
    }
}
