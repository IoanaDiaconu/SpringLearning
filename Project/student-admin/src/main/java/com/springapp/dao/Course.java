package com.springapp.dao;

import com.springapp.annotation.CourseInfo;
import com.springapp.dao.Student;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ioana.diaconu on 1/14/2015.
 */
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private int course_id;
    @Column
    @NotEmpty(message = "Field required")
    private String course_name;
    @Column
    private String course_description;
    @OneToMany(mappedBy = "course",targetEntity = Student.class ,cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<Student>();

    public int getId() {
        return course_id;
    }

    public void setId(int id) {
        this.course_id = id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_description() {
        return course_description;
    }

    public void setCourse_description(String course_description) {
        this.course_description = course_description;
    }

    public List getStudents() {
        return students;
    }

    public void setStudents(List students) {
        this.students = students;
    }
    @Override
    public String toString() {
        return "Course{" +
                "id=" + course_id +
                ", course_name='" + course_name + '\'' +
                ", course_description='" + course_description + '\'' +
                '}';
    }
}

