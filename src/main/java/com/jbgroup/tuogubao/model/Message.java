package com.jbgroup.tuogubao.model;

import org.bson.types.ObjectId;

import java.util.Date;

public class Message {
    private ObjectId _id;
    private String content;
    private String createDate;

    public Message(ObjectId _id, String content, String createDate) {
        this._id = _id;
        this.content = content;
        this.createDate = createDate;
    }

    public Message() {}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String toString() {
        return "{ content: " + this.content + ", createDate: " + this.createDate + " }";
    }
}
