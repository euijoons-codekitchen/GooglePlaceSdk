package com.example.googleplayssdkprj.dto;

import android.arch.lifecycle.Observer;

public class MainItem {

    private String title;
    private String description;
    private Observer<String> observer;

    public Observer<String> getObserver() {
        return observer;
    }

    public void setObserver(Observer<String> observer) {
        this.observer = observer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MainItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
