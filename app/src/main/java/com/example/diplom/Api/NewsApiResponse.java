package com.example.diplom.Api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsApiResponse {
    private List<News> articles;
    public List<News> getArticles() {
        return articles;
    }

    @SerializedName("status")
    private String status;

    @SerializedName("sources")
    private List<Source> sources;

    // Геттеры и сеттеры для полей
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

}
