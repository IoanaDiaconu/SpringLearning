package com.spring.app;

import com.spring.app.config.JPAConfig;
import com.spring.app.config.MvcConfig;
import com.spring.app.config.ServiceConfig;
import com.spring.app.config.WebAppInitializer;
import com.spring.app.repository.UserDTO;
import com.spring.app.service.UserService;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertNotNull;


/**
 * Created by ioana.diaconu on 2/13/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {JPAConfig.class, ServiceConfig.class, WebAppInitializer.class, MvcConfig.class}, loader = AnnotationConfigWebContextLoader.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TestUserService {
    private static final Logger log = LoggerFactory.getLogger(TestUserService.class);
    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    UserService userService;
    private UserDTO user;
    private MockMvc mockMvc;

    @BeforeTransaction
    public void setup() {
        user = new UserDTO();
        user.setUsername("test");
        user.setEmail("test@gmail.com");
        user.setPassword("test");
        user.setEnables(1);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createTest() {
        userService.create(user);
        log.info("Users available in method create {}", userService.findAll());
        assertNotNull(userService.findByUsername("test"));
    }

    @Test
    public void deleteTest() {
       createTestUser();
       userService.delete("test");
       assertNull(userService.findByUsername("test"));
    }

    @Test
    public void findByEmailTest(){
        createTestUser();
        UserDTO user = userService.findByEmail("test@gmail.com");
        assertNotNull(user);
    }

    @Test
    public void findByUsernameAndEmailTest(){
        createTestUser();
        List<UserDTO> users = userService.findByUsernameAndEmail("test","test@gmail.com");
        assertNotNull(users);

    }

    private void createTestUser() {
        userService.create(user);
    }
}
