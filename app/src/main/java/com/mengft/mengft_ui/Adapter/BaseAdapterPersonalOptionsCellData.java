package com.mengft.mengft_ui.Adapter;

/**
 * Created by mengft on 2018/5/29.
 */

public class BaseAdapterPersonalOptionsCellData {

    private String title;
    private int iconName, color;

    public BaseAdapterPersonalOptionsCellData(String title, int iconName, int color) {
        this.title = title;
        this.iconName = iconName;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconName() {
        return iconName;
    }

    public void setIconName(int iconName) {
        this.iconName = iconName;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
