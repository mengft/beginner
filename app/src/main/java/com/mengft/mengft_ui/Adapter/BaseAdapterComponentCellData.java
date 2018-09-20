package com.mengft.mengft_ui.Adapter;

/**
 * Created by mengft on 2018/8/14.
 */

public class BaseAdapterComponentCellData {
    private String key, title, text;

    public BaseAdapterComponentCellData(String key, String title, String text) {
        this.key = key;
        this.title = title;
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
