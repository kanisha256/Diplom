package com.example.diplom.Headline;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.diplom.Api.News;
import com.example.diplom.Api.NewsLoader;
import com.example.diplom.R;
import com.google.android.material.tabs.TabLayout;

import java.util.List;



public class HeadlinesFragment extends Fragment implements HeadlinesView, NewsLoader.NewsLoaderCallback {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private HeadlinesAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int threshold = 10;
    private String category = "general";


    HeadlinesPresenter headlinesPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_headlines, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);

        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        adapter = new HeadlinesAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(() -> onRefresh());
        loadNews(category);

        tabLayout.addTab(tabLayout.newTab().setText("general").setIcon(R.drawable.globe));
        tabLayout.addTab(tabLayout.newTab().setText("science").setIcon(R.drawable.plane));
        tabLayout.addTab(tabLayout.newTab().setText("sports").setIcon(R.drawable.coin_hand));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                category = tab.getText().toString().toLowerCase();
                onRefresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    if (!isLoading && !isLastPage) {
                        if ((visibleItemCount + firstVisibleItemPosition + threshold) >= totalItemCount
                                && firstVisibleItemPosition >= 0
                                && totalItemCount >= 10) {
                            if (dy > 0) {
                                progressBar.setVisibility(View.VISIBLE);
                                loadMoreNews(category);
                            }
                        }
                    }
                }
            }
        });

        return view;
    }

    private void loadNews(String category) {
        isLoading = true;
        if (currentPage == 1) {
            progressBar.setVisibility(View.VISIBLE);
        }
        headlinesPresenter.loadTopHeadlines(category, currentPage,this);
    }

    private void loadMoreNews(String category) {
        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            headlinesPresenter.loadTopHeadlines(category, currentPage, this);
            progressBar.setVisibility(View.GONE);
        }, 1500);
    }

    public void onNewsLoaded(List<News> newsList) {
        if (currentPage == 1) {
            adapter.clearNews();
            progressBar.setVisibility(View.GONE);
        }
        adapter.addNews(newsList);
        isLoading = false;
        currentPage++;
        swipeRefreshLayout.setRefreshing(false);
    }

    public void onNewsLoadError(String errorMessage) {
        if (currentPage == 1) {
            progressBar.setVisibility(View.GONE);
        }
        isLoading = false;
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    public void onRefresh() {
        adapter.clearNews();
        currentPage = 1;
        loadNews(category);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage) {}

    @Override
    public void showNews(List<News> newsList) {
        adapter.addNews(newsList);
    }
}