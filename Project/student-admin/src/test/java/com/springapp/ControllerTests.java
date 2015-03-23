package com.springapp;

import com.springapp.controller.CoursesController;
import com.springapp.service.CourseService;
import com.springapp.service.StudentService;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExcecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by ioana.diaconu on 1/14/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
@WebAppConfiguration
//@ContextConfiguration({"file:student-admin/src/test/resources/mvc-dispatcher-servlet.xml", "file:student-admin/src/test/resources/security-config.xml", "file:student-admin/src/test/resources/servlet-context.xml"})
@ContextConfiguration(locations = {"classpath:**/mvc-dispatcher-servlet.xml","classpath:**/security-config.xml","classpath:**/servlet-context.xml"})
@TestExecutionListeners(listeners = {ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        WithSecurityContextTestExcecutionListener.class})
public class ControllerTests {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;
    Logger log = Logger.getLogger(ControllerTests.class);
    @InjectMocks
    CoursesController coursesController;
    @Mock
    StudentService studentService;
    @Mock
    SessionRegistry sessionRegistry;
    @Mock
    private CourseService courseService;
    private MockMvc mockMvc, mockMVCStandalone;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        MockitoAnnotations.initMocks(this);
        this.mockMVCStandalone = MockMvcBuilders.standaloneSetup(coursesController).setViewResolvers(viewResolver).build();
    }


    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void testLogIn() throws Exception {
        this.mockMvc.perform(get("/list")).andExpect(status().isOk()).andExpect(model().attributeExists("students"));
    }

    @Test
    public void testCourse() throws Exception {
        this.mockMvc.perform(get("/viewStudents"));
    }

    /**
     * Test using standalone configuration
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void testCourses() throws Exception {
        this.mockMVCStandalone.perform(get("/courses"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void testSaveCourse() throws Exception {
        this.mockMVCStandalone.perform(post("/saveCourse")
                .param("course_id", "101")
                .param("course_name", "test")
                .param("course_description", "testing stuff"))
                .andDo(print())
                .andExpect(redirectedUrl("courses"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void testListAvailableCourses() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/courses/available")).andDo(print()).andReturn();
        String jsonResult = result.getResponse().getContentAsString();
        log.info("result" + jsonResult);
        Assert.assertNotNull(jsonResult);
    }


}
