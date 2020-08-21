package com.jbgroup.tuogubao.model;

import com.jbgroup.tuogubao.util.IBuilder;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class UserBuilder implements IBuilder<User> {
    private String _idStr;
    private ObjectId _id;
    private String username = "new user";
    private String password;
    private boolean isAlive = true;
    private List<Contact> contacts;

    public UserBuilder setIdStr(String _idStr) {
        this._id = new ObjectId(_idStr);
        return this;
    }

    public UserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder setId(ObjectId _id){
        this._id = _id;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
        return this;
    }

    public UserBuilder setIsAlive(String isAlive) {
        if(isAlive.toLowerCase().equals(("true"))) this.isAlive = true;
        else this.isAlive = false;
        return this;
    }

    public UserBuilder addContact(Contact contact) {
        if(this.contacts == null) {
            this.contacts = new ArrayList();
        }
        this.contacts.add(contact);
        return this;
    }

    @Override
    public User build() {
        return new User(this._id, this.username, this.password, this.isAlive, this.contacts);
    }

    public String toString() {
        String contactsStr = "";
        for(Contact cont: this.contacts) {
            contactsStr += cont.toString();
        }
        return "user: " + this.username + (this.isAlive ? " is alive." : "is dead.")
                + contactsStr;
    }
}
