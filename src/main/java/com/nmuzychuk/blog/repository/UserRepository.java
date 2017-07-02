package com.nmuzychuk.blog.repository;

import com.nmuzychuk.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    User findById(Long userId);

    User findByUsername(String username);

}
