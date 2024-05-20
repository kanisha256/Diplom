package com.example.diplom.Events;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplom.R;

import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {

    private List<Day> dayList;

    public DayAdapter(List<Day> dayList) {
        this.dayList = dayList;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        Day day = dayList.get(position);
        holder.dayText.setText(day.getDayOfWeek());
        holder.tasksLayout.removeAllViews();

        for (String task : day.getTasks()) {
            TextView taskText = new TextView(holder.itemView.getContext());
            taskText.setText(task);
            taskText.setTextSize(16f);
            taskText.setTextColor(0xFF000000);
            holder.tasksLayout.addView(taskText);
        }
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView dayText;
        LinearLayout tasksLayout;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            dayText = itemView.findViewById(R.id.dayText);
            tasksLayout = itemView.findViewById(R.id.tasksLayout);
        }
    }
}
