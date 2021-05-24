package com.bank.api.DAO;

import com.bank.api.DAO.connection.MyConnection;
import com.bank.api.entity.User;
import com.bank.api.service.AccountServiceImpl;
import com.bank.api.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOImplTest {

    @InjectMocks
    private UserDAOImpl userDAO;


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
    void findById() {

        User user = userDAO.findById(1L);
        assertEquals("Narek", user.getName());
        assertEquals(1L, user.getId());
        assertNull(user.getLastname());
        return;
    }

    @Test
    void findAll() {
        List<User> all = userDAO.findAll();
        List<User> checkAll = new ArrayList<>();
        checkAll.add(new User(1,"Narek"));
        checkAll.add(new User(2,"Roman"));
        assertEquals(all, checkAll);

    }

    @Test
    void save() {
        User user = new User(3,"Test", "Proverkov");
        userDAO.save(user);

        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select id, name, lastname from usr order by id desc limit 1")) {


            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            User chUser = new User(resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("lastname"));

            assertEquals(user, chUser);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}