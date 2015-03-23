package com.springapp;

import com.springapp.dao.StudentDAO;
import com.springapp.service.StudentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

/**
 * Created by ioana.diaconu on 3/11/2015.
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:student-admin/src/main/webapp/WEB-INF/context.xml", "file:student-admin/src/main/webapp/WEB-INF/servlet-context.xml"})
@PrepareForTest(StudentServiceImpl.class)
public class StudentTest {
    @InjectMocks
    public  StudentServiceImpl studentService;
    @Mock
    StudentDAO studentDAO;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }


    /**
     * Using Whitebox(Powermock) to mock a static final global variable pattern and call a private method
     */
    @Test
    public void  testCheckStudentName() throws Exception {
        Pattern mockPattern = Pattern.compile("\\w+\\.?");
        Whitebox.setInternalState(StudentServiceImpl.class,mockPattern);
        boolean okName= Whitebox.<Boolean>invokeMethod(studentService,"checkIfNameValid","Ioana");
        assertTrue(okName);
    }

}
