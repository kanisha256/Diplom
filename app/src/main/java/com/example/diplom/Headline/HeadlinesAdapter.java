package com.example.diplom.Headline;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.Api.News;
import com.example.diplom.Api.Source;
import com.example.diplom.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HeadlinesAdapter extends RecyclerView.Adapter<HeadlinesAdapter.NewsViewHolder> {

    private List<News> newsList = new ArrayList<>();

    private OnItemClickListener listener;
    public void addNews(List<News> news) {
        int startPosition = newsList.size();
        for (News item : news) {
            if (isValidHead(item)) {
                newsList.add(item);
            }
        }
        notifyItemRangeInserted(startPosition, news.size());
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_headline, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.bind(news);
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClick(news);
            }
        });

    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (News news : newsList) {
            if (isValidHead(news)) {
                count++;
            }
        }
        return count;
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView descriptionTextView;
        private ImageView imageView;

        private ImageView iconView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.source);
            descriptionTextView = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.image_news);
            iconView = itemView.findViewById(R.id.icon_news);
        }

        public void bind(News news) {
            if (news != null) {
                titleTextView.setText(news.getSource().getName());
                descriptionTextView.setText(news.getDescription());
                Picasso.get().load(news.getImageUrl()).into(imageView);
                Picasso.get().load(R.drawable.cnnmonogram).into(iconView);
            }
        }
    }

    public void clearNews() {
        newsList.clear();
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(News news);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private boolean isValidHead(News news) {
        Source source = news.getSource();
        return source != null && source.getName() != null && !source.getName().isEmpty() && source.getId() != null && !source.getId().isEmpty() && news.getDescription() != null && !news.getDescription().isEmpty();
    }

}
