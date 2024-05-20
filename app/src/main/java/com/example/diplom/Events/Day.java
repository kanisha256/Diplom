package com.example.diplom.Events;

public class Day {
    private String dayOfWeek;
    private String[] tasks;

    public Day(String dayOfWeek, String[] tasks) {
        this.dayOfWeek = dayOfWeek;
        this.tasks = tasks;
    }


    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String[] getTasks() {
        return tasks;
    }
}
