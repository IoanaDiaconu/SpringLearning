package com.springapp.service;

import com.springapp.dao.Course;

import java.beans.PropertyEditorSupport;

/**
 * Created by ioana.diaconu on 1/20/2015.
 */
public class CourseEditor extends PropertyEditorSupport {
    private CourseService courseService;


    public CourseEditor(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String id = "";
        String[] courseInfo = text.split(",");
        for (String s : courseInfo) {
            if (s.contains("id")) {
                courseInfo = s.split("=");
                id = courseInfo[1];
                break;
            }
        }

        Course course = courseService.getCourse(Integer.parseInt(id));
        setValue(course);
    }
}
