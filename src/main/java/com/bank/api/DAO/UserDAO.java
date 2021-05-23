package com.bank.api.DAO;

import com.bank.api.entity.User;

import java.util.List;

public interface UserDAO {
    User findById(long id);
    List<User> findAll();

    void save(User user);
}
