package com.bank.api.DAO;

import com.bank.api.DAO.connection.MyConnection;
import com.bank.api.controller.ServerController;
import com.bank.api.entity.Account;
import com.bank.api.entity.User;
import com.bank.api.exceptions.MyGlobalException;
import com.bank.api.exceptions.NoSuchUsersException;
import com.bank.api.responses.SendMyResponses;
import com.sun.net.httpserver.HttpExchange;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO{

    private static AccountDAOImpl instance;
    private AccountDAOImpl(){}
    public static AccountDAOImpl getInstance(){
        if(instance == null){
            instance = new AccountDAOImpl();
        }
        return instance;
    }


    @Override//доабвление нового счета юзеру
    public void addByUserId(long userId, String generateAccountNumber) throws MyGlobalException {

        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into account(user_id, account_number) values (?, ?)")) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, generateAccountNumber);
            int i = preparedStatement.executeUpdate();
            if (i == 0 ) throw new NoSuchUsersException("User by id = " + userId +  " not found");

        } catch (SQLException throwables) {

            throw new NoSuchUsersException("User by id = " + userId +  " not found");
        }

    }

    @Override
    public List<Account> findAllAccountsByUserId(long userId) {

        List<Account> list = new ArrayList<>();

        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select id, account_number, balance from account where user_id = ?")) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                list.add(new Account(resultSet.getLong("id"),
                        resultSet.getString("account_number"),
                        resultSet.getDouble("balance")));
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;


    }

    @Override//добавление нового счета контрагенту
    public void addByContractorId(long contractorId, String generateAccountNumber) throws SQLException {
        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into account(contractor_id, account_number) values (?, ?)")) {
            preparedStatement.setLong(1, contractorId);
            preparedStatement.setString(2, generateAccountNumber);
            int i = preparedStatement.executeUpdate();
            if (i == 0 ) throw new NoSuchUsersException("Contractor by id = " + contractorId +  " not found");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new NoSuchUsersException("Contractor by id = " + contractorId +  " not found");
        }
    }

    @Override //найти счет по номеру карты
    public long findAccountIdByCardNumber(String cardNumber) throws SQLException {

        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select account_id from card where card_number = ?")) {
            preparedStatement.setString(1, cardNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return resultSet.getLong("account_id");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new NoSuchUsersException("Account by card = " + cardNumber +  " not found");
        }

    }

    @Override //вернуть баланс по id счета
    public double getAccountBalanceById(long senderAccountId) throws SQLException {


        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select balance from account where id = ?")) {
            preparedStatement.setLong(1, senderAccountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return resultSet.getDouble("balance");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new NoSuchUsersException("Failed to get account balance");
        }


    }


}
