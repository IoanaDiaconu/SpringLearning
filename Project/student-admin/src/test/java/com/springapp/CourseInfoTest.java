package com.springapp;

import com.springapp.annotation.CourseInfo;
import com.springapp.dao.CourseDAOImpl;
import com.springapp.service.CourseService;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by ioana.diaconu on 2/25/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//@ContextConfiguration({"file:student-admin/src/main/webapp/WEB-INF/servlet-context.xml"})
@ContextConfiguration(locations = {"classpath:**/mvc-dispatcher-servlet.xml","classpath:**/security-config.xml","classpath:**/servlet-context.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CourseInfoTest {
    @Autowired
    protected CourseService courseService;

    Logger log = Logger.getLogger(CourseInfoTest.class);

    @Test
    public void testClassAnnotation() throws NoSuchMethodException {
        Class courseInfo = CourseDAOImpl.class;
        Annotation[] annotations = courseInfo.getAnnotations();
        try {
            for (Annotation annotation : annotations) {
                if (annotation instanceof CourseInfo) {
                    Assert.assertNotNull(((CourseInfo) annotation).name());
                    log.info("Course Info name " + ((CourseInfo) annotation).name());
                    log.info("Course Info state " + ((CourseInfo) annotation).state());
                    log.info("Course Info date " + ((CourseInfo) annotation).dateAdded());
                    log.info("Course Info expected " + ((CourseInfo) annotation).expected());

                }
            }
        } catch (Exception ex) {
            log.error("Error {}" + ex.getMessage());
        }
    }

    @Test
    public void testMethodAnnotation() {
        Annotation annotation = null;
        try {
            Method courseInfo = CourseDAOImpl.class.getMethod("getCourseInfo", new Class[]{});
            annotation = courseInfo.getAnnotation(CourseInfo.class);
            if (annotation == null)
                return;
            log.info(((CourseInfo) annotation).name() + " " + courseInfo.getName());
            Object testInfo = CourseDAOImpl.class.newInstance();
            courseInfo.invoke(testInfo);
        } catch (Exception ex) {
            boolean condition = ((CourseInfo) annotation).expected() == ex.getCause().getClass();
            log.info(((CourseInfo) annotation).expected() + " " + ex.getCause().getClass());
            Assert.assertTrue("Expected and throw are the same", condition);

        }
    }

    
}
