package com.jbgroup.tuogubao.model;

import com.jbgroup.tuogubao.util.IBuilder;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class User {
    private ObjectId _id;
    private String username;
    private String password;
    private boolean isAlive;
    private List<Contact> contacts;

    public String[] pbFields = new String[]{"username", "password", "isAlive"};

    public User() {}

    public User(ObjectId _id, String username, String password, boolean isAlive, List<Contact> contacts) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.isAlive = isAlive;
        this.contacts = contacts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
    public void setAlive(String alive) {
        if(alive.toLowerCase().equals("true")) this.isAlive = true;
        else this.isAlive = false;
    }

    public static class Builder implements IBuilder<User> {
        private static String _idStr;
        private static ObjectId _id;
        private static String username = "new user";
        private static String password;
        private boolean isAlive = true;
        private List<Contact> contacts;

        public Builder setIdStr(String _idStr) {
            this._id = new ObjectId(_idStr);
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setId(ObjectId _id){
            this._id = _id;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setIsAlive(boolean isAlive) {
            this.isAlive = isAlive;
            return this;
        }

        public Builder setIsAlive(String isAlive) {
            if(isAlive.toLowerCase().equals(("true"))) this.isAlive = true;
            else this.isAlive = false;
            return this;
        }

        public Builder addContact(Contact contact) {
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
}
