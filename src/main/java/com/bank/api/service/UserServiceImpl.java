package com.bank.api.service;

import com.bank.api.DAO.UserDAO;
import com.bank.api.DAO.UserDAOImpl;
import com.bank.api.entity.User;


import java.util.List;

public class UserServiceImpl implements UserService{

    UserDAO userDAO = new UserDAOImpl();

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public void save(User user) {
        userDAO.save(user);
    }

}
