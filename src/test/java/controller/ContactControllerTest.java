package controller;

import com.jbgroup.tuogubao.controller.ContactController;
import com.jbgroup.tuogubao.controller.UserController;
import com.jbgroup.tuogubao.model.User;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
public class ContactControllerTest {

//    @Autowired
//    private User user;

    private ContactController contactController = new ContactController();
    private UserController userController = new UserController();

    @Ignore
    @Test
    public void AddNewUserSucceed() {
        String testStr = "{\"username\": \"caonima\", \"password\": \"nimabi\", \"isAlive\": \"true\"}";
        userController.addUser(testStr);
    }

    @Test
    public void AddNewContactSucceed() {
        String userId = "5f39dc0b53a53793bec683b5";
        String param = "{\"name\": \"baba\", \"words\": \"I died\"}";
        contactController.addContact(userId, param);
    }
}
