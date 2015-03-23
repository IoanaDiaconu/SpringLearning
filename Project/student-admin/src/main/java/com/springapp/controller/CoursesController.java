package com.springapp.controller;

import com.springapp.dao.Course;
import com.springapp.dao.Student;
import com.springapp.service.CourseService;
import com.springapp.service.StudentService;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ioana.diaconu on 1/15/2015.
 */
@Controller
public class CoursesController {

    private static Logger log = Logger.getLogger(CoursesController.class);

    @Autowired
    CourseService courseService;
    @Autowired
    StudentService studentService;
    @Autowired
    SessionRegistry sessionRegistry;

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public String availableCourses(Model model){
        log.trace("<< availableCourses()");
        List<Course> courses = courseService.listCourse();
        for(Course c : courses){
            List<Student> students = studentService.getStudentsByCourse(c);
            c.setStudents(students);
        }
        String username = getUserInfo().getUsername();
        log.info("Courses available " + courses);
        model.addAttribute("username", username);
        model.addAttribute("courses", courses);
        model.addAttribute("role",getUserInfo().getAuthorities().toString());
        return "courses";
    }

    @RequestMapping(value = "/addCourse", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("courses", new Course());
        return "addCourse";
    }

    @RequestMapping(value = "/saveCourse", method = RequestMethod.POST)
    public String save(@ModelAttribute("course")  Course course,
                       BindingResult br) {
        if (br.hasErrors()) {
            return "addCourse";
        }

        if (course.getId() > 0) {
            log.info("Course "+course.getCourse_name() + " was updated with the following info " +course);
            courseService.updateCourse(course);
        } else {
            log.info("A new course was added with the following info "+ course);
            courseService.addCourse(course);
        }
        return "redirect:courses";
    }

    @RequestMapping(value = "/editCourse/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("courses", courseService.getCourse(id));
        return "addCourse";
    }

    @RequestMapping(value = "/viewStudents", method = RequestMethod.GET)
    public String viewStudents(@PathVariable Integer id, Model model) {
        Course course = courseService.getCourse(id);
        List<Student> students = studentService.getStudentsByCourse(course);
        course.setStudents(students);
        courseService.updateCourse(course);
        model.addAttribute("course",course);
        return "viewStudents";
    }

    @RequestMapping(value = "/deleteCourse/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Integer id) {
        log.info("Course " + courseService.getCourse(id) + " was deleted");
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }

    public UserDetails getUserInfo() {
        List<Object> value = sessionRegistry.getAllPrincipals();
        log.info("Users " + value);
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @RequestMapping(value = "/courses/available", method = RequestMethod.GET)
    public
    @ResponseBody
    String listOfAvailableCourses(){
        JSONArray courses =  new JSONArray();
        List<Course> availabaleCourses = courseService.listCourse();
        for(Course course : availabaleCourses){
            JSONObject courseJSON =  new JSONObject();
            courseJSON.put("course_id",course.getId());
            courseJSON.put("course_name",course.getCourse_name());
            courseJSON.put("course_description",course.getCourse_description());
            courseJSON.put("course_students",course.getStudents().toString());
            courses.put(courseJSON);
        }
        return courses.toString();
    }
}
