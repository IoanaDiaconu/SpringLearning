package com.springapp.service;

import com.springapp.dao.Course;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ioana.diaconu on 12/18/2014.
 */
public interface CourseService {
    public Course getCourse(int id);

    public void updateCourse(Course Course);

    public List<Course> listCourse();

    public void deleteCourse(int id);

    public void addCourse(Course Course);

    public void removeStudentFromCourse(int idStudent, Course course);

    public Course getCourseByName(String courseName);

    public JSONObject generateCourseInfo(int id);
}
