package com.spring.app.controller;

import com.spring.app.aop.NotifyClients;
import com.spring.app.repository.User;
import com.spring.app.repository.UserDTO;
import com.spring.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ioana.diaconu on 2/16/2015.
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<UserDTO> usersAvailable() {
        return userService.findAll();
    }


    @NotifyClients
    @RequestMapping(value = "/users/{username}", method = RequestMethod.PUT)
    public UserDTO updateUser(@PathVariable String username, @RequestBody UserDTO user) {
        user.setUsername(username);
        userService.update(user);
        return user;
    }

    @NotifyClients
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public UserDTO create(@RequestBody UserDTO user) {
        return userService.create(user);
    }


    @NotifyClients
    @RequestMapping(value="/users/{username}",method = RequestMethod.DELETE)
    public void delete(@PathVariable String username){
        userService.delete(username);
    }

}