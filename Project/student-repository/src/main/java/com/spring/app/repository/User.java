package com.spring.app.repository;

import javax.persistence.*;

/**
 * Created by ioana.diaconu on 2/12/2015.
 */
@Entity
@NamedQuery(name = "User.findByEmail",query = "select user from User user where user.email=(?1)")
@Table(name = "users")
public class User {
    @Id
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", unique = true, nullable = false)
    private String password;
    @Column(name = "enabled", unique = true, nullable = false)
    private int enables;
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    public User() {
    }
    public User(Builder builder) {
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
    }

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

    @Override
    public String toString() {
        return "Users{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static Builder getBuilder() {
        return new Builder();
    }


    public static class Builder{
        private String username;
        private String email;
        private String password;


        public Builder username(String username){
            this.username = username;
            return this;
        }

        public Builder email(String email){
            this.email = email;
            return this;
        }

       public Builder password(String password){
            this.password = password;
            return this;
        }

        public User build(){
            User build = new User(this);
            return build;
        }

    }
}
