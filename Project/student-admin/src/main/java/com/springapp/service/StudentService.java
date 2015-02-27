package com.springapp.service;

import com.springapp.dao.Course;
import com.springapp.dao.Student;

import java.util.List;

/**
 * Created by ioana.diaconu on 12/18/2014.
 */
public interface StudentService {
    public Student getStudent(int id);

    public void updateStudent(Student Student);

    public List<Student> listStudent();

    public void deleteStudent(int id);

    public void addStudent(Student Student);

    Student getStudent(String username);

    public List<Student> getStudentsByCourse(Course course);
}
