package com.springapp.dao;

import com.springapp.annotation.CourseInfo;
import com.springapp.annotation.CourseState;
import com.springapp.service.StudentService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@CourseInfo
@Repository
public class CourseDAOImpl implements CourseDAO {
    @Autowired
    StudentService studentService;
    @Autowired
    private SessionFactory sessionFactory;

    Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Course getCourse(int id) {
        return (Course) getCurrentSession().get(Course.class, id);
    }

    @Override
    public List<Course> getAllCourses() {
        return getCurrentSession().createQuery("From Course").list();
    }


    @Override
    public void deleteCourse(int id) {
        Course Course = getCourse(id);
        getCurrentSession().delete(Course);
        getCurrentSession().flush();
    }

    @Override
    public void addCourse(Course course) {
        getCurrentSession().save(course);
    }

    @Override
    public void updateCourse(Course course) {
        getCurrentSession().update(course);
        getCurrentSession().flush();
    }

    @Override
    public List findCourseByName(String courseName) {
        String hsql = "From Course where course_name = '" + courseName + "'";
        return getCurrentSession().createQuery(hsql).list();
    }

    @Override
    public List getAttendeesCourse(int id) {
        String hsql = "From Student where course_id =" + id;
        return getCurrentSession().createQuery(hsql).list();
    }

    @Override
    public void removeStudentFromCourse(int idStudent, Course course) {
        Student student = studentService.getStudent(idStudent);
        List<Student> attendees = getAttendeesCourse(course.getId());
        for (Student attendee : attendees) {
            if (student != null && attendee.equals(student)) {
                attendee.setCourse(null);
                studentService.updateStudent(attendee);
                break;
            }
        }
        attendees = studentService.getStudentsByCourse(course);
        course.setStudents(attendees);
        getCurrentSession().update(course);
        getCurrentSession().flush();

    }

    @Override
    public Course getCourseByName(String courseName) {
        Query query = getCurrentSession().createQuery("From Course where course_name = :courseName");
        query.setParameter("courseName",courseName);
        return (Course)query.list().get(0);
    }

    @Override
    @CourseInfo(name="Available Course",state = CourseState.DEMANDED, dateAdded = "25.02.2014", expected = IllegalStateException.class )
    public String getCourseInfo() {
        throw new IllegalStateException("Not in a good state");
    }


}
