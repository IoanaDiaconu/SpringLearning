package com.springapp.dao;

import com.springapp.dao.Course;

import java.util.List;

/**
 * Created by ioana.diaconu on 1/14/2015.
 */
public interface CourseDAO {
    public Course getCourse(int id);

    public List getAllCourses();

    public void deleteCourse(int id);

    public void addCourse(Course Course);

    public void updateCourse(Course Course);

    public List findCourseByName(String first_name);

    public List getAttendeesCourse(int id);

    public void removeStudentFromCourse(int idStudent,Course course);

    public Course getCourseByName(String courseName);

    public String getCourseInfo();
}
