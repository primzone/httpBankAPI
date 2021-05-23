package com.bank.api.service;

import com.bank.api.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    void save(User user);
}
