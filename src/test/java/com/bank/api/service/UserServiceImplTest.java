package com.bank.api.service;

import com.bank.api.DAO.ContractorDAOImpl;
import com.bank.api.DAO.UserDAOImpl;
import com.bank.api.entity.Contractor;
import com.bank.api.entity.User;
import com.bank.api.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserDAOImpl userDAOMOCK = UserDAOImpl.getInstance();

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        try {
            Utils.createAndInitDb();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void findAll() {

        List<User> usersFromMock = new ArrayList<>();
        Mockito.when(userDAOMOCK.findAll()).thenReturn(usersFromMock);
        List<User> users = userService.findAll();
        assertEquals(users, usersFromMock);

    }

    @Test
    void save() {
        User user = new User("Pasha");
        userService.save(user);
        Mockito.verify(userDAOMOCK, Mockito.times(1)).save(user);
    }
}