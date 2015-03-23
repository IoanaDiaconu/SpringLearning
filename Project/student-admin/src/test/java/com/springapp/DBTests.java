package com.springapp;

import com.springapp.annotation.StudentComponent;
import com.springapp.dao.Course;
import com.springapp.dao.CourseDAO;
import com.springapp.dao.Student;
import com.springapp.dao.StudentDAO;
import com.springapp.service.CourseService;
import com.springapp.service.StudentService;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transaction;
import java.util.List;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//@ContextConfiguration({"file:student-admin/src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml", "file:student-admin/src/main/webapp/WEB-INF/servlet-context.xml"})
@ContextConfiguration(locations = {"classpath:**/mvc-dispatcher-servlet.xml","classpath:**/security-config.xml","classpath:**/servlet-context.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class DBTests extends AbstractTransactionalJUnit4SpringContextTests {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    protected StudentDAO studentDAO;
    @Autowired
    protected CourseDAO courseDAO;
    @Autowired
    StudentService studentService;
    @Autowired
    CourseService courseService;
    @Autowired
    SessionFactory sessionFactory;
    Student student;
    Course course;
    Logger log = Logger.getLogger(DBTests.class);
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
        student = new Student();
        student.setFirst_name("Ioana");
        student.setLast_name("Diaconu");
        student.setAge("24");
        course = new Course();
        course.setCourse_name("gdcjhgsahfcgddgashgchjasdghgchj");
        course.setCourse_description("bla bla bla");
        student.setCourse(course);
    }

    @Test
    public void testAddStudent() {
        studentDAO.addStudent(student);
        Student testStudent = studentDAO.getStudent(student.getId());
        Assert.assertNotNull(testStudent.getId());
    }

    @Test
    public void testFindStudent() {
        List list = studentDAO.findStudentByName("9");
        Assert.assertNotNull(list);
    }


    @Test
    public void testAddCourse() {
        courseDAO.addCourse(course);
        List courses = courseDAO.getAllCourses();
        Assert.assertNotNull(courses);
    }

    @Test
    public void testStudent() {
        Student student = studentService.getStudent("administrator");
        Assert.assertNotNull(student);
    }

    @Test
    public void testRelations() {
        List students = studentDAO.getAllStudents();
        course.setStudents(students);
        Assert.assertNotNull(courseDAO.getAttendeesCourse(1));
    }

    @Test
    public void testDeleteForeignKey() {
        List<Course> courses = courseService.listCourse();
        Course test = courses.get(1);
        courseDAO.deleteCourse(test.getId());
        List<Student> students = studentService.getStudentsByCourse(test);
        System.out.println(students);
    }


    @Test
    public void testRollback() {
        Course testCourse = new Course();
        testCourse.setCourse_name("dgafhdfghfghfgdhgfhgfgfgsdfgdfssfgdsdfsfdgdfssdgdfsdfgfdsfgfdsgfhsdgf");
        testCourse.setCourse_description("dgsfjfgdjshgafhagfdgsdgdfsgdfgdsffjfdgsdfgfdgsfdhgasdjhfgasgdjfgdsjgf");
        Course testCourse2 = new Course();
        testCourse2.setCourse_description("dagfdsagfas");
        testCourse2.setCourse_name("dasgfhgsahgfhsagfsah");
        Course testCourse3 = new Course();
        testCourse3.setCourse_name("adhshf");
        testCourse3.setCourse_description("agjdsgfdhgf");
        try {
            courseService.addCourse(testCourse2);
            courseService.addCourse(testCourse);
            courseService.addCourse(testCourse3);
        } catch (Exception ex) {
            sessionFactory.getCurrentSession().clear();
        }

        List courses = courseService.listCourse();
        Assert.assertNotNull(courses);
        Assert.assertNull(courseService.getCourse(testCourse.getId()));
    }

    @BeforeTransaction
    public void beforeTransaction() {
        log.info("Before Transaction");
        List<Course> courses = courseService.listCourse();
        Assert.assertNotNull("Courses available ", courses);
        Assert.assertEquals("No.courses available ", 4, courses.size());
    }

    @Test
    public void testTransaction() {
        log.info("Test transaction");
        Course course = new Course();
        course.setCourse_name("lol");
        course.setCourse_description("nanana");
        try {
            courseService.addCourse(course);
            courseService.deleteCourse(18);
            log.info(courseService.listCourse());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull("Courses ", courseService.listCourse());
        Assert.assertNotNull("New course ", courseService.getCourse(course.getId()));
    }

    @AfterTransaction
    public void afterTransactions() {
        log.info("After Transaction");
        List<Course> courses = courseService.listCourse();
        Assert.assertNotNull("Courses available ", courses);
        log.info(courses);
    }

    @Test
    public void testDeleteStudentsFromCourse() {
        Course course = courseService.getCourse(19);
        courseService.removeStudentFromCourse(51, course);
        courseService.listCourse();
        List<Course> courses = courseService.listCourse();

    }


    @Test
    public void testTransactions() {
        try {
            transaction1();
            transaction2();
            transaction3();
        } catch (Exception e) {
            sessionFactory.getCurrentSession().clear();
            log.info("Test Transaction " + courseService.listCourse());
            e.printStackTrace();
        }

        Assert.assertNotNull(courseService.listCourse());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void transaction1() {
        Course course = new Course();
        course.setCourse_name("transaction1");
        courseService.addCourse(course);
        CourseService service = applicationContext.getBean(CourseService.class);
        String courseServiceClassName = service.getClass().getName();
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            String transactionName =  TransactionSynchronizationManager.getCurrentTransactionName();
        }

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void transaction2() {
        String transactionName = "";
        Course course = new Course();
        course.setCourse_name("transaction2");
        courseService.addCourse(course);
        transaction3();
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            transactionName =  TransactionSynchronizationManager.getCurrentTransactionName();
        }
        log.info(transactionName + "transaction 2");
        throw new RuntimeException("Exception");
    }

    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_UNCOMMITTED)
    private void transaction3() {
        Course courseTransaction2 = courseService.getCourseByName("transaction2");
        Course course = new Course();
        course.setCourse_name("transaction3");
        courseTransaction2.setCourse_description("some transaction 3");
        courseService.addCourse(course);
        courseService.updateCourse(courseTransaction2);
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            String transactionName =  TransactionSynchronizationManager.getCurrentTransactionName();
            log.info("method: transaction3" + transactionName + Transaction.class.getAnnotations());
        }
    }


    @Test
    public void testStudentComponent(){
        StudentComponent component = applicationContext.getBean(StudentComponent.class);
        Object info =  component.prepareTestStudent();
        Assert.assertNotNull(info);
        log.info(info);
    }



}
