package com.yevseienko.models;

import java.util.ArrayList;

public class Homework{
    private final int id;
    private final String name;
    private final ArrayList<Task> tasks;

    private int taskId;

    public Homework(int id, String name) {
        this.name = name;
        this.id = id;
        tasks = new ArrayList<>();

        taskId = 0;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int getId() {
        return id;
    }

    public void addTask(String name, String path){
        Task task = new Task(++taskId, name, path);
        tasks.add(task);
    }
}

