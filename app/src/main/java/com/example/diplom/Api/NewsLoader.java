package com.example.diplom.Api;

import java.util.List;

public class NewsLoader {
    public interface NewsLoaderCallback {
        void onNewsLoaded(List<News> newsList);
        void onNewsLoadError(String errorMessage);
    }
}
