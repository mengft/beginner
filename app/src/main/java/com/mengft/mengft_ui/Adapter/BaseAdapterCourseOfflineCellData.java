package com.mengft.mengft_ui.Adapter;

/**
 * Created by mengft on 2018/5/27.
 */

public class BaseAdapterCourseOfflineCellData {

    private String title, thumbnail, address;
    private Number enrollNumber;
    private Long startTime;

    public BaseAdapterCourseOfflineCellData(String title, String thumbnail, String address, Long startTime, Number enrollNumber) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.address = address;
        this.enrollNumber = enrollNumber;
        this.startTime = startTime;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Number getEnrollNumber() {
        return enrollNumber;
    }

    public void setEnrollNumber(Number enrollNumber) {
        this.enrollNumber = enrollNumber;
    }

}
