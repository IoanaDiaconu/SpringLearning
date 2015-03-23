package com.springapp;

import com.springapp.dao.Course;
import com.springapp.dao.CourseDAO;
import com.springapp.service.AboutInfo;
import com.springapp.service.CourseServiceImpl;
import com.springapp.service.StudentServiceImpl;
import org.easymock.EasyMock;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;


/**
 * Created by ioana.diaconu on 3/2/2015.
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:student-admin/src/main/webapp/WEB-INF/context.xml", "file:student-admin/src/main/webapp/WEB-INF/servlet-context.xml"})
@PrepareForTest(AboutInfo.class)
public class TestMessage {
    @Mock
    CourseDAO course;
    @InjectMocks
    CourseServiceImpl courseService;
    @InjectMocks
    StudentServiceImpl studentService;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

    }

    /**
     * Test that mocks a static method
     */
    @Test
    public void testGenerateInfo() throws Exception {
        final long time = 1L;
        mockStatic(AboutInfo.class);
        when(AboutInfo.generateTimeInfo()).thenReturn(time);
        CourseServiceImpl mockCourse = PowerMock.createPartialMock(CourseServiceImpl.class, "getCourse");
        EasyMock.expect(mockCourse.getCourse(12)).andReturn(new Course());
        JSONObject json = courseService.generateCourseInfo(12);
        PowerMockito.verifyStatic();
        Assert.assertNotNull(json);
        Assert.assertEquals(time, json.get("time"));
    }

    /**
     * Test that uses partial mocking  -  use spy
     */
    @Test
    public void testGenerateAbout() throws Exception {
        AboutInfo aboutInfo = spy(new AboutInfo());
        String info = "Just some testing";
        List<String> authors = new ArrayList<String>();
        List<String> result = new ArrayList<String>();
        result.add("Some mock results");
        String expected = "Some mock results";
        when(aboutInfo, "logInfoAbout", info, authors).thenReturn(result);
        Assert.assertEquals(expected, aboutInfo.logInfo(info, authors));
        verify(aboutInfo, Mockito.times(1));
    }


    /**
     * Mock creation of object
     *
     * @throws Exception
     */
    @Test
    public void testCreateLogFile() throws Exception {
        final String path = "just some path";
        File logFileMock = mock(File.class);
        whenNew(File.class).withArguments(path).thenReturn(logFileMock);
        when(logFileMock.exists()).thenReturn(false);
        when(logFileMock.mkdirs()).thenReturn(true);

        Assert.assertTrue(courseService.createLogFile(path));
        verifyNew(File.class).withArguments(path);
    }


    /**
     * Mock Global variable using reflection
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public void testHeaderLog() throws NoSuchFieldException, IllegalAccessException {
        AboutInfo info = mock(AboutInfo.class);
        when(info.createLogMessage()).thenReturn("Mock Global field");
        CourseServiceImpl service = new CourseServiceImpl();
        Field mockGlobalFied = CourseServiceImpl.class.getDeclaredField("info");
        mockGlobalFied.setAccessible(true);
        mockGlobalFied.set(service, info);
        Assert.assertEquals("Mock Global field", service.createHeaderLog());
    }


}
