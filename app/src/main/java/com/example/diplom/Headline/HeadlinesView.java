package com.example.diplom.Headline;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.diplom.Api.News;

import java.util.List;

public interface HeadlinesView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showProgress();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideProgress();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showNews(List<News> newsList);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showError(String errorMessage);

}
