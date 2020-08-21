package com.jbgroup.tuogubao.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jbgroup.tuogubao.model.Contact;
import com.jbgroup.tuogubao.model.User;
import com.jbgroup.tuogubao.model.UserBuilder;
import com.jbgroup.tuogubao.util.JSONMapper;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static com.jbgroup.tuogubao.util.StringUtil.getGetter;
import static com.jbgroup.tuogubao.util.StringUtil.getSetter;

@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@RequestMapping(value = "/contact")
@RestController
public class ContactController {

    @RequestMapping(value = "/retriveAll/{user_id}", method = RequestMethod.GET)
    public String retriveAllContacts(@PathVariable String user_id){
        // use the user_id to retrieve all its contacts
//        MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGO_URI));

        try {
            MongoClient mongoClient = new MongoClient();
            DB database = mongoClient.getDB("contact");

        } catch(UnknownHostException e) {
            System.out.println(e);
        }

        return "";
    }

    @RequestMapping(value = "/addNew", method = RequestMethod.POST)
    public String addContact(@RequestBody String userId, @RequestBody String param){
        // use the user_id to retrieve all its contacts
//        MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGO_URI));

        try {
            JSONMapper<Contact, Contact.Builder> mapper = new JSONMapper<>(Contact.class, param);
            Contact contact = mapper.parse();

            MongoClient mongoClient = new MongoClient();
            DB database = mongoClient.getDB("tuogubao");
            // retrieve the user by user_id
            DBCollection collection = database.getCollection("user");
            DBObject userQuery = new BasicDBObject();
            userQuery.put("_id", new ObjectId(userId));
            DBObject user = collection.findOne(userQuery);
            List<DBObject> contacts = (List<DBObject>) user.get("contacts");
            if(contacts == null) contacts = new ArrayList<>();
            DBObject newContact = new BasicDBObject();
            // see what fields in User class is publicly accessible
            for(String field: contact.pbFields) {
                String getter = getGetter(field);
                Method getterMethod = Contact.class.getMethod(getter);
                if(getterMethod.invoke(contact).toString() == null || getterMethod.invoke(contact).toString().length() <= 0) {
                    continue;
                } else {
                    newContact.put(field, getterMethod.invoke(contact).toString());
                }
            }
            contacts.add(newContact);
            user.put("contacts", contacts);
            collection.update(userQuery, user);
            mongoClient.close();

        } catch(UnknownHostException e) {
            System.out.println(e);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
//        catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

        return "";
    }
}
