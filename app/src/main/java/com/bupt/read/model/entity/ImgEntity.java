package com.bupt.read.model.entity;

import java.io.Serializable;

/**
 * Created by xs on 2016/6/25.
 */
public class ImgEntity implements Serializable{
    private String url;
    private String height;
    private String width;

    @Override
    public String toString() {
        return "ImgEntity{" +
                "url='" + url + '\'' +
                ", height='" + height + '\'' +
                ", width='" + width + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
