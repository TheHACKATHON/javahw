package com.yevseienko.models;

public class Task{
    private final int id;
    private final String name;
    private final String path;

    public Task(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public int getId() {
        return id;
    }
}
