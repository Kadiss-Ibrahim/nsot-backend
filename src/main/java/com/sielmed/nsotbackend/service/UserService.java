package com.sielmed.nsotbackend.service;

import com.sielmed.nsotbackend.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User findByUsername(String username);
    User create(User user);
    User update(Long id, User updated);
    void delete(Long id);
}