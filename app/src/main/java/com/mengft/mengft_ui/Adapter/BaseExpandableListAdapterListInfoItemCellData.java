package com.mengft.mengft_ui.Adapter;

/**
 * Created by mengft on 2018/5/31.
 */

public class BaseExpandableListAdapterListInfoItemCellData {

    private String key, value;

    public BaseExpandableListAdapterListInfoItemCellData(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
