package com.springapp.dao;

import com.springapp.dao.StudentDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO {
    @Autowired
    private SessionFactory sessionFactory;

    Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Student getStudent(int id) {
       return(Student) getCurrentSession().get(Student.class, id);
    }

    @Override
    public List getAllStudents() {
        return  getCurrentSession().createQuery("From Student").list();
    }


    @Override
    public void deleteStudent(int id) {
        Student student = getStudent(id);
        getCurrentSession().delete(student);
        getCurrentSession().flush();
    }

    @Override
    public void addStudent(Student student) {
       getCurrentSession().save(student);
    }

    @Override
    public void updateStudent(Student student) {
       getCurrentSession().update(student);
       getCurrentSession().flush();
    }

    @Override
    public List findStudentByName(String first_name) {
        String hsql = "From Student where first_name = '"+ first_name+"'";
        return getCurrentSession().createQuery(hsql).list();
    }

    @Override
    public List getStudent(String username) {
       String hsql = "From Student where username = '" + username+"'";
       return  getCurrentSession().createQuery(hsql).list();
    }

    @Override
    public List<Student> getStudentsByCourse(Course course) {
        String hsql = "From Student where course_id = " + course.getId();
        return getCurrentSession().createQuery(hsql).list();
    }

}
