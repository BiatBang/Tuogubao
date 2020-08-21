package com.jbgroup.tuogubao.model;

import org.bson.types.ObjectId;

import java.util.Date;

public class Message {
    private ObjectId _id;
    private String content;
    private Date createDate;

    public Message(ObjectId _id, String content, Date createDate) {
        this._id = _id;
        this.content = content;
        this.createDate = createDate;
    }
}
