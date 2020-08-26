package com.jbgroup.tuogubao.model;

import com.jbgroup.tuogubao.util.IBuilder;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    private ObjectId _id;
    private String name;
    private String words;
    private List<ContactInfo> contactInfos;
    private List<Message> messages;
    public String[] pbFields = new String[]{"name", "words"};

    public Contact() {}

    private Contact(ObjectId _id, String name, String words, List<ContactInfo> contactInfos, List<Message> messages) {
        this._id = _id;
        this.name = name;
        this.words = words;
        this.contactInfos = contactInfos;
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public String getWords() {
        return words;
    }

    public static class Builder implements IBuilder<Contact> {
        private ObjectId _id;
        private String _idStr;
        private String name;
        private String words;
        private List<ContactInfo> contactInfos;
        private List<Message> messages;

        public Builder setIdStr(String _idStr) {
            this._id = new ObjectId(_idStr);
            return this;
        }

        public Builder setId(ObjectId _id) {
            this._id = _id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setWords(String words) {
            this.words = words;
            return this;
        }

        public Builder setContactInfos(List<ContactInfo> contactInfos) {
            this.contactInfos = contactInfos;
            return this;
        }

        public Builder addContactInfo(ContactInfo contactInfo) {
            if(this.contactInfos == null) {
                this.contactInfos = new ArrayList<>();
            }
            this.contactInfos.add(contactInfo);
            return this;
        }

        public Builder setMessages(List<Message> messages) {
            this.messages = messages;
            return this;
        }

        public Builder addMessage(Message message) {
            if(this.messages == null) {
                this.messages = new ArrayList<>();
            }
            this.messages.add(message);
            return this;
        }

        @Override
        public Contact build() {
            return new Contact(this._id, this.name, this.words, this.contactInfos, this.messages);
        }
    }

    public String toString() {
        String res = "id: " + this._id + ", ";
        res += "name:" + this.name + ", ";
        res += "last words:" + this.words + ", ";
        for(ContactInfo ci: this.contactInfos) {
            res += ci.toString() + ", ";
        }
        for(Message m: this.messages) {
            res += m.toString() + ", ";
        }
        return res;
    }
}
