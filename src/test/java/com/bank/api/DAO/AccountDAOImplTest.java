package com.bank.api.DAO;

import com.bank.api.DAO.connection.MyConnection;
import com.bank.api.entity.Account;
import com.bank.api.entity.User;
import com.bank.api.exceptions.MyGlobalException;
import com.bank.api.exceptions.NoSuchUsersException;
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

class AccountDAOImplTest {

    @InjectMocks
    private AccountDAOImpl accountDAO;


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
    void getInstance() {
        assertNotNull(AccountDAOImpl.getInstance());
    }

    @Test
    void addByUserId() {


        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select id, account_number, balance, contractor_id, user_id from account order by id desc limit 1")) {

            accountDAO.addByUserId(1L, "25436728749036254738");

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            assertEquals(5L, resultSet.getLong("id"));
            assertEquals("25436728749036254738", resultSet.getString("account_number"));
            assertEquals(0, resultSet.getDouble("balance"));
            assertEquals(0, resultSet.getLong("contractor_id"));
            assertEquals(1L, resultSet.getLong("user_id"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Test
    void findAllAccountsByUserId() {
        List<Account> list = new ArrayList<>();
        list.add(new Account(1L, "64756938751523098125", 100000));
        list.add(new Account(2L, "91381939012482346177", 45000));
//        list.add(new Account(3L, "24682746248298534909", 34000));
//        list.add(new Account(4L, "34588534289090230111", 190000));



        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select id, account_number, balance, contractor_id, user_id from account where user_id = 1")) {
            accountDAO.findAllAccountsByUserId(1L);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Account> listFromDB = new ArrayList<>();
            while (resultSet.next()){
                listFromDB.add(new Account(resultSet.getLong("id"),
                        resultSet.getString("account_number"),
                        resultSet.getDouble("balance"))
                );
            }

            assertEquals(listFromDB, list);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Test
    void addByContractorId() {

    }

    @Test
    void findAccountIdByCardNumber() {

        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select account_id from card where card_number = '6743 8237 9032 8734'")) {
            accountDAO.findAccountIdByCardNumber("6743 8237 9032 8734");

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            long account_id = resultSet.getLong("account_id");
            assertEquals(1, account_id);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Test
    void getAccountBalanceById() {

        double accountBalanceById = 0;
        try {
            accountBalanceById = accountDAO.getAccountBalanceById(1);
            assertEquals(100000, accountBalanceById);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

}