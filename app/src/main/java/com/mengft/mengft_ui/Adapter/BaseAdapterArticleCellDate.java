package com.mengft.mengft_ui.Adapter;

/**
 * Created by mengft on 2018/5/22.
 */

public class BaseAdapterArticleCellDate {

    private String title, thumbnail, url;
    private Number enrollNumber;

    public BaseAdapterArticleCellDate(String title, String thumbnail, String url, Number enrollNumber) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.url = url;
        this.enrollNumber = enrollNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Number getEnrollNumber() {
        return enrollNumber;
    }

    public void setEnrollNumber(Number enrollNumber) {
        this.enrollNumber = enrollNumber;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
