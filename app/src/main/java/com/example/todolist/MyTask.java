package com.example.todolist;

import java.io.Serializable;

public class MyTask implements Serializable {
    private int id;
    private String title;
    private String content;
    private int complete;

    public MyTask() {
    }

    public MyTask(int id, String title, String content, int complete) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.complete = complete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }
}
