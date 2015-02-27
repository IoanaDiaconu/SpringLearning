package com.springapp.controller;

import com.springapp.dao.Course;
import com.springapp.dao.Student;
import com.springapp.service.CourseEditor;
import com.springapp.service.CourseService;
import com.springapp.service.StudentService;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.util.List;
import java.util.Locale;

@Controller
class MainController {

    private static Logger log = Logger.getLogger(MainController.class);

    @Autowired
    StudentService studentService;
    @Autowired
    MessageSource messageSource;
    @Autowired
    CourseService courseService;
    private Locale locale;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @InitBinder
    void initBinder(HttpServletRequest request, ServletRequestDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Course.class, new CourseEditor(this.courseService));
    }

    @RequestMapping("login")
    public ModelAndView getLoginForm(@RequestParam(required = false) String authFailed, String logout,
            String denied, HttpServletRequest request) {

        String message = "";
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if (authFailed != null) {
            message = "Invalid username of password, try again !";
            log.info("! Authentification failed: " + message);
        } else if (logout != null) {
            message = "Logged Out successfully, login again to continue !";
            log.info("! Authentification failed: " + message);
        } else if (denied != null) {
            message = "Access denied for this user "+ username + " !" ;
            log.info("! Authentification failed: " + message);
        }
        if (isRememberMeAuthenticated()) {
            setRememberMeTargetUrlToSession(request);
        }

        return new ModelAndView("login", "message", message);
    }

    private boolean isRememberMeAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }

        return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
    }

    private void setRememberMeTargetUrlToSession(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.setAttribute("targetUrl", "/list");
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUserPage() {
        String username, authority;
        ModelAndView modelAndView = new ModelAndView();

        username = getUserInfo().getUsername();
        modelAndView.addObject("username", username);

        authority = getUserInfo().getAuthorities().toString();
        if (authority.contains("ROLE_ADMIN")) {
            return "redirect:list";
        } else {
            return "redirect:courses";
        }
    }

    @RequestMapping("admin")
    public String getAdminPage() {
        return "admin";
    }

    @RequestMapping("403page")
    public String get403denied() {
        return "redirect:login?denied";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        Student student = new Student();
        student.setUsername(getUserInfo().getUsername());
        model.addAttribute("student", student);
        model.addAttribute("courses", courseService.listCourse());
        return "addstudent";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("student") @Valid Student student,
                       BindingResult br, @RequestParam("file") File file) {
        if (br.hasErrors()) {
            log.error("! Record cannot be saved. Error " + br.getAllErrors() + " occurred");
            return "addstudent";
        }

        if (student.getId() > 0) {
            student.setUsername(getUserInfo().getUsername());
            studentService.updateStudent(student);
        } else {
            byte[] picture = new byte[(int) file.length()];
            student.setUsername(getUserInfo().getUsername());
            try {
                FileInputStream inputStream = new FileInputStream(file.getCanonicalPath());
                inputStream.read(picture);
                inputStream.close();
                student.setPicture(picture);
            } catch (Exception e) {
                e.printStackTrace();
            }
            studentService.addStudent(student);
        }
        return "redirect:list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Student> students = studentService.listStudent();
        model.addAttribute("username", getUserInfo().getUsername());
        model.addAttribute("students", students);
        return "students";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("student", studentService.getStudent(id));
        model.addAttribute("courses",courseService.listCourse());
        return "addstudent";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Integer id) {
        studentService.deleteStudent(id);
        return "redirect:/list";
    }

    @RequestMapping("/download/{id}")
    public String download(@PathVariable("id")
                           Integer id, HttpServletResponse response) {

        Student student = studentService.getStudent(id);
        try {
            response.setHeader("Content-Disposition", "inline;filename=\"" + student.getPicture() + "\"");
            OutputStream out = response.getOutputStream();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(student.getPicture());
            response.setContentType(student.getPicture().toString());
            IOUtils.copy(inputStream, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    public UserDetails getUserInfo() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}