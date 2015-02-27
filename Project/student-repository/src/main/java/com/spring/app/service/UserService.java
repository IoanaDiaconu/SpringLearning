package com.spring.app.service;

import com.spring.app.repository.UserDTO;
import com.spring.app.repository.User;

import java.util.List;

/**
 * Created by ioana.diaconu on 2/13/2015.
 */
public interface UserService {
    public UserDTO create(UserDTO user);

    public void delete(String username);

    public List<UserDTO> findAll();

    public void update(UserDTO user);

    public UserDTO findByUsername(String username);

    public UserDTO findByEmail(String email);

    public List<UserDTO> findByUsernameAndEmail(String username, String email);

}
