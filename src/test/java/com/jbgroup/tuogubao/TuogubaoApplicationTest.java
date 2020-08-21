package com.jbgroup.tuogubao;

import com.jbgroup.tuogubao.controller.ContactController;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TuogubaoApplicationTest {
	@Autowired
	private ContactController contactController;

	@Ignore
	@Test
	void contextLoads() {
	}

	@Ignore
	@Test
	public void testInsertContact() {
//		String parameter = "{\"_id\": \"5f271245a3694c2f38ac26d8\"}";
//		contactController.addContact(parameter);
	}

}
