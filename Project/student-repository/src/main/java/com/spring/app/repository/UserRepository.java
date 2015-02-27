package com.spring.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by ioana.diaconu on 2/13/2015.
 */
public interface UserRepository extends JpaRepository<User,String> {
    public User findByEmail(String email);

    @Query("select user from User user where user.username=:username and user.email=:email")
    public List<User> findByUsernameAndEmail(@Param("username")String username,@Param("email")String email);
}
