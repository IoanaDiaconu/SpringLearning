package com.springapp.dao;

import com.springapp.dao.Student;

import java.util.List;

public interface StudentDAO {
    public Student getStudent(int id);

    public List getAllStudents();

    public void deleteStudent(int id);

    public void addStudent(Student student);

    public void updateStudent(Student student);

    public List findStudentByName(String first_name);

    public List getStudent(String username);

    List<Student> getStudentsByCourse(Course course);
}
