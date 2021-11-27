package com.cyj.whereareyou.data.entity;

public class NotificationEntity {
    private int id;
    private String title;
    private String content;
    private String time;
    private String type;
    private int status;

    public NotificationEntity(int id, String title, String content, String time, String type, int status) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.time = time;
        this.type = type;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
