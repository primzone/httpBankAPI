package com.bank.api.DAO;

import com.bank.api.DAO.connection.MyConnection;
import com.bank.api.entity.Account;
import com.bank.api.entity.Contractor;
import com.bank.api.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContractorDAOImplTest {


    @InjectMocks
    private ContractorDAOImpl contractorDAO;


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
    void addByUserId() throws SQLException {

        contractorDAO.addByUserId(1, "Testovich", false);

        Connection connection = new MyConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select id, name, corporation from contractor order by id desc limit 1");

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals(3, resultSet.getLong("id"));
        assertEquals("Testovich", resultSet.getString("name"));
        assertFalse(resultSet.getBoolean("corporation"));
        preparedStatement.close();
        PreparedStatement preparedStatement1 = connection.prepareStatement(
                "select user_id, contractor_id from users_contractors " +
                        "where user_id = 1 and contractor_id = 3"
        );

        ResultSet resultSet1 = preparedStatement1.executeQuery();
        resultSet1.next();

        assertEquals(1, resultSet1.getLong("user_id"));
        assertEquals(3, resultSet1.getLong("contractor_id"));


        preparedStatement1.close();
        connection.close();

    }

    @Test
    void findAllByUserId() throws SQLException {

        contractorDAO.findAllByUserId(1L);

        Connection connection = new MyConnection().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select id, name, corporation from contractor " +
                        "join users_contractors uc on contractor.id = uc.contractor_id " +
                        "where user_id = 1");
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        assertEquals(1L, resultSet.getLong("id"));
        assertEquals("Kate", resultSet.getString("name"));
        assertFalse(resultSet.getBoolean("corporation"));
        resultSet.next();
        assertEquals(2L, resultSet.getLong("id"));
        assertEquals("Viktor", resultSet.getString("name"));
        assertFalse(resultSet.getBoolean("corporation"));



        preparedStatement.close();
        connection.close();

    }

    @Test
    void getInstance() {


        assertNotNull(UserDAOImpl.getInstance());
    }
}