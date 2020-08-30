package com.jbgroup.tuogubao.controller;

import com.jbgroup.tuogubao.model.Contact;
import com.jbgroup.tuogubao.util.ContactJSONMapper;
import com.jbgroup.tuogubao.util.IJSONMapper;
import com.jbgroup.tuogubao.util.JSONMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = "/retriveAll", method = RequestMethod.GET)
    public List<Contact> retriveAllContacts(@RequestParam(value="userId") String userId){
        // use the user_id to retrieve all its contacts
        List<Contact> contacts = new ArrayList<>();

        try {
            MongoClient mongoClient = new MongoClient();
            DB database = mongoClient.getDB("tuogubao");
            // retrieve the user by user_id
            DBCollection collection = database.getCollection("user");
            DBObject userQuery = new BasicDBObject();
            userQuery.put("_id", new ObjectId(userId));
            DBObject user = collection.findOne(userQuery);
            List<DBObject> contactObjs = (List<DBObject>) user.get("contacts");

            for(DBObject cont: contactObjs) {
                Contact.Builder builder = new Contact.Builder();
                if(cont.containsField("name")) builder.setName(cont.get("name").toString());
                if(cont.containsField("words")) builder.setWords(cont.get("words").toString());
                contacts.add(builder.build());
            }

        } catch(UnknownHostException e) {
            System.out.println(e);
        }

        return contacts;
    }

    @RequestMapping(value = "/addNew", method = RequestMethod.POST)
    public String addContact(@RequestBody String userId, @RequestBody String param){
        // use the user_id to retrieve all its contacts
//        MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGO_URI));
        try {
            ContactJSONMapper mapper = new ContactJSONMapper(param);
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
