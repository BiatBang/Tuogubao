package com.jbgroup.tuogubao.util;

import com.jbgroup.tuogubao.model.Contact;
import com.jbgroup.tuogubao.model.ContactInfo;
import com.jbgroup.tuogubao.model.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.jbgroup.tuogubao.util.StringUtil.getSetter;

public class ContactJSONMapper implements IJSONMapper<Contact, Contact.Builder> {
    private String jsonStr;
    private Contact result;

    public ContactJSONMapper(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    @Override
    public Contact parse() {
        if(jsonStr == null) throw new IllegalArgumentException("no json string detected");
        JSONObject jsonObject = new JSONObject(jsonStr);

        this.result = analyzeJSON(jsonObject);
        return this.result;
    }

    private Contact analyzeJSON(JSONObject jsonObject) {
        Contact.Builder builder = new Contact.Builder();
        String[] pbfields = new Contact().pbFields;

        for(String field: pbfields) {
            if(jsonObject.has(field)) {
                String setterName = getSetter(field);
                Method setterMethod = null;
                try {
                    setterMethod = Contact.Builder.class.getMethod(setterName, String.class);
                    setterMethod.invoke(builder, jsonObject.get(field));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        // customized fields
        if(jsonObject.has("contactInfos")) {
            JSONArray infos = (JSONArray) jsonObject.get("contactInfos");
            Iterator iterator = infos.iterator();
            List<ContactInfo> contactInfos = new ArrayList();
            while(iterator.hasNext()) {
                JSONMapper<ContactInfo, ContactInfo> cimapper = new JSONMapper<>(ContactInfo.class, iterator.next().toString());
                ContactInfo ci = cimapper.parse();
                contactInfos.add(ci);
            }
            builder.setContactInfos(contactInfos);
        }

        if(jsonObject.has("messages")) {
            JSONArray ms = (JSONArray) jsonObject.get("messages");
            Iterator iterator = ms.iterator();
            List<Message> messages = new ArrayList();
            while(iterator.hasNext()) {
                JSONMapper<Message, Message> mmapper = new JSONMapper<>(Message.class, iterator.next().toString());
                Message m = mmapper.parse();
                messages.add(m);
            }
            builder.setMessages(messages);
        }

        return builder.build();
    }

    @Override
    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }
}
