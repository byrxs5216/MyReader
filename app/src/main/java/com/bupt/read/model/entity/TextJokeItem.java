package com.bupt.read.model.entity;

import java.io.Serializable;

/**
 * Created by xs on 2016/6/21.
 */
public class TextJokeItem implements Serializable{
    private String id;
    private String text;
    private String title;
    private int type;
    private String ct;

    @Override
    public String toString() {
        return "TextJokeItem{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", ct='" + ct + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }
}
