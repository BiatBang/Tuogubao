package com.jbgroup.tuogubao.model;

public class ContactInfo {
    private String type;
    private String content;
    public String[] pbFields = {"type", "content"};

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString() {
        return "{ type: " + this.type + ", content: " + this.content + " }";
    }
}
