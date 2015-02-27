package com.spring.app.service;

import com.spring.app.repository.User;
import com.spring.app.repository.UserDTO;
import com.spring.app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ioana.diaconu on 2/13/2015.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDTO create(UserDTO user) {
        log.info("Creating a new user using {}", user);
        User newUser = User.getBuilder().username(user.getUsername()).email(user.getEmail()).password(user.getPassword()).build();
        newUser = userRepository.saveAndFlush(newUser);
        log.info("New user {} was created", newUser);
        return toUserDTO(newUser);
    }

    @Override
    public void delete(String username) {
        log.info("Deleting user with username {}", username);
        User userToDelete = findUserEntryByUsername(username);
        log.info("Found user with following info {}", userToDelete);
        userRepository.delete(userToDelete);
        log.info("User deleted {}", userToDelete);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<UserDTO> userDTOs = new ArrayList<UserDTO>();
        List<User> users = userRepository.findAll(sortByUsernameAsc());
        for (User user : users) {
            userDTOs.add(toUserDTO(user));
        }

        return userDTOs;
    }

    @Override
    public void update(UserDTO user) {
        User userToUpdate = userRepository.findOne(user.getUsername());
        if (user != null) {
            userToUpdate.setPassword(user.getPassword());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setEnables(user.getEnables());
            userRepository.saveAndFlush(userToUpdate);
        }
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepository.findOne(username);
        return toUserDTO(user);
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return toUserDTO(user);
    }

    @Override
    public List<UserDTO> findByUsernameAndEmail(String username, String email) {
        List<User> users = userRepository.findByUsernameAndEmail(username,email);
        List<UserDTO> userDTOs = new ArrayList<UserDTO>();
        for (User user : users) {
            userDTOs.add(toUserDTO(user));
        }
        return userDTOs;
    }

    private Sort sortByUsernameAsc(){
        return new Sort(Sort.Direction.ASC,"username");
    }

    private User findUserEntryByUsername(String username) {
        User user = userRepository.findOne(username);
        if (user != null) {
            return user;
        }
        throw new IllegalArgumentException("User not found!");

    }

    private UserDTO toUserDTO(User newUser) {
        if (newUser == null)
            return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(newUser.getUsername());
        userDTO.setPassword(newUser.getUsername());
        userDTO.setEmail(newUser.getEmail());
        return userDTO;
    }
}
