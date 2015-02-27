package com.springapp.service;

import com.springapp.service.CourseService;
import com.springapp.dao.Course;
import com.springapp.dao.CourseDAO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ioana.diaconu on 12/18/2014.
 */
@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseDAO courseDAO;

    // global instance for group students
    // global static instance for group students

    @Override
    public Course getCourse(int id) {
        return courseDAO.getCourse(id);
    }

    @Override
    public void updateCourse(Course Course) {
        courseDAO.updateCourse(Course);
    }

    @Override
    public List<Course> listCourse() {
        return courseDAO.getAllCourses();
    }

    @Override
    public void deleteCourse(int id) {
        courseDAO.deleteCourse(id);
    }

    @Override
    public void addCourse(Course Course) {
       courseDAO.addCourse(Course);
    }

    @Override
    public void removeStudentFromCourse(int idStudent,Course course) {
        courseDAO.removeStudentFromCourse(idStudent,course);
    }

    @Override
    public Course getCourseByName(String courseName) {
        return courseDAO.getCourseByName(courseName);
    }

    @Override
    public JSONObject generateCourseInfo(int id) {
        JSONObject jsonObject = new JSONObject();
        final long time =  TimeInfo.generateTimeInfo();
        Course course = getCourse(id);
        jsonObject.put("time", time);
        jsonObject.put("course",course.getStudents());
        return jsonObject;

    }


}
