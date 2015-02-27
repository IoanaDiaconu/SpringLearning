package com.springapp.annotation;

import com.springapp.dao.Student;
import com.springapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ioana.diaconu on 2/26/2015.
 */
@Component
public class StudentComponent {
    @Autowired
    StudentService studentService;

    @StudentInfo(firstName = "Ioana",lastName = "Bla",age = "25")
    public Object prepareTestStudent(){
        return "Test";
    }
}
