package com.mengft.mengft_ui.Adapter;

/**
 * Created by mengft on 2018/5/28.
 */

public class BaseAdapterCommunityCellData {

    private String title, lastMessage, avatar;
    private Long time;

    public BaseAdapterCommunityCellData(String title, String lastMessage, String avatar, Long time) {
        this.title = title;
        this.lastMessage = lastMessage;
        this.avatar = avatar;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
