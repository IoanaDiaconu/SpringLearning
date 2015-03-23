package com.springapp.service;

import com.springapp.dao.Course;
import com.springapp.dao.Student;
import com.springapp.dao.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ioana.diaconu on 12/18/2014.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class StudentServiceImpl  implements StudentService{
    // global static instance for group students
    private static Pattern patternForName = Pattern.compile("^[\\p{L} .'-]+$");

    @Autowired
    StudentDAO studentDAO;
    @Override
    public Student getStudent(int id) {
        return studentDAO.getStudent(id);
    }

    @Override
    public void updateStudent(Student student) {
        studentDAO.updateStudent(student);
    }

    @Override
    public List<Student> listStudent() {
        return studentDAO.getAllStudents();
    }

    @Override
    public void deleteStudent(int id) {
        studentDAO.deleteStudent(id);
    }

    @Override
    public void addStudent(Student student) {
       String studentName = student.getFirst_name()+" "+student.getLast_name(); 
       if(checkIfNameValid(studentName))
            studentDAO.addStudent(student);
    }

    private boolean checkIfNameValid(String studentName) {
        Matcher matcher = patternForName.matcher(studentName);
        return matcher.matches();
    }

    @Override
    public Student getStudent(String username) {
        return (Student) studentDAO.getStudent(username).get(0);
    }

    @Override
    public List<Student> getStudentsByCourse(Course course) {
        return studentDAO.getStudentsByCourse(course);
    }
}
