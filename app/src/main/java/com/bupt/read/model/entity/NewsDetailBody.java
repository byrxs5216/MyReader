package com.bupt.read.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xs on 2016/6/26.
 */
public class NewsDetailBody implements Serializable{
    private String content;
    private String html;
    private String time;
    private String title;
    private List<ImgEntity> img_list;
    private List<Object> all_list;

    @Override
    public String toString() {
        return "NewsDetailBody{" +
                "content='" + content + '\'' +
                ", html='" + html + '\'' +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", img_list=" + img_list +
                ", all_list=" + all_list +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ImgEntity> getImg_list() {
        return img_list;
    }

    public void setImg_list(List<ImgEntity> img_list) {
        this.img_list = img_list;
    }

    public List<Object> getAll_list() {
        return all_list;
    }

    public void setAll_list(List<Object> all_list) {
        this.all_list = all_list;
    }
}
