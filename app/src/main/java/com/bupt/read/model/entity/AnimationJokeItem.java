package com.bupt.read.model.entity;

import java.io.Serializable;

/**
 * Created by xs on 2016/6/18.
 */
public class AnimationJokeItem implements Serializable {
    private String ct;
    private String id;
    private String img;
    private String title;
    private float type;

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getType() {
        return type;
    }

    public void setType(float type) {
        this.type = type;
    }
}
