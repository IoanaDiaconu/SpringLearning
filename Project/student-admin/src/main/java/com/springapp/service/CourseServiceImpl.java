package com.springapp.service;

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

    // global instance
    AboutInfo info;

    public CourseServiceImpl(){
        info = new AboutInfo();
    }

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
        final long time =  AboutInfo.generateTimeInfo();
        Course course = getCourse(id);
        jsonObject.put("time", time);
        jsonObject.put("course",course);
        return jsonObject;

    }

    @Override
    public String generateAbout(String info, List<String> authors) {
        return new AboutInfo().logInfo(info, authors);
    }

    @Override
    public boolean createLogFile(String path) {
        AboutInfo info = new AboutInfo();
        return info.createLogFile(path);
    }

    @Override
    public String createHeaderLog() {
        return info.createLogMessage();
    }


}

