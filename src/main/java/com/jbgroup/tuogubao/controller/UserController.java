package com.jbgroup.tuogubao.controller;

import com.jbgroup.tuogubao.model.User;
import com.jbgroup.tuogubao.model.UserBuilder;
import com.jbgroup.tuogubao.util.JSONMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.UnknownHostException;

import static com.jbgroup.tuogubao.util.StringUtil.getGetter;

@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@RestController
public class UserController {

    @RequestMapping(value = "/user/addNew", method = RequestMethod.POST)
    public String addUser(@RequestBody String param){
        // use the user_id to retrieve all its contacts
//        MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGO_URI));

        try {
            JSONMapper<User, UserBuilder> mapper = new JSONMapper<>(User.class, param);
            User user = mapper.parse();

            MongoClient mongoClient = new MongoClient();
            DB database = mongoClient.getDB("tuogubao");
            // retrieve the user by user_id
            DBCollection collection = database.getCollection("user");
            DBObject newUser = new BasicDBObject();
            // see what fields in User class is publicly accessible
            for(String field: user.pbFields) {
                String getter = getGetter(field);
                Method getterMethod = User.class.getMethod(getter);
                if(getterMethod.invoke(user).toString() == null || getterMethod.invoke(user).toString().length() <= 0) {
                    continue;
                } else {
                    newUser.put(field, getterMethod.invoke(user).toString());
                }
            }
            collection.insert(newUser);
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
