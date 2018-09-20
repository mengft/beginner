package com.mengft.mengft_ui.Adapter;

/**
 * Created by mengft on 2018/5/27.
 */

public class BaseAdapterCourseOnlineCellData {

    private String vid, title, thumbnail;
    private Number enrollNumber;

    public BaseAdapterCourseOnlineCellData(String vid, String title, String thumbnail, Number enrollNumber) {
        this.vid = vid;
        this.title = title;
        this.thumbnail = thumbnail;
        this.enrollNumber = enrollNumber;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
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

    public Number getEnrollNumber() {
        return enrollNumber;
    }

    public void setEnrollNumber(Number enrollNumber) {
        this.enrollNumber = enrollNumber;
    }
}
