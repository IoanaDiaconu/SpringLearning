package com.spring.app.repository;


import org.dozer.Mapping;

/**
 * Created by ioana.diaconu on 2/16/2015.
 */
public class UserDTO {
    @Mapping("username")
    private String username;
    @Mapping("password")
    private String password;
    @Mapping("enabled")
    private int enables;
    @Mapping("email")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnables() {
        return enables;
    }

    public void setEnables(int enables) {
        this.enables = enables;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDTO() {
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", enables=" + enables +
                ", email='" + email + '\'' +
                '}';
    }
}
