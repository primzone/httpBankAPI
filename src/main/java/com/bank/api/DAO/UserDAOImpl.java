package com.bank.api.DAO;

import com.bank.api.DAO.connection.MyConnection;
import com.bank.api.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO{
    @Override
    public User findById(long id) {
        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from usr where id = ?")) {
            preparedStatement.setString(1, "1");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            User user = new User(resultSet.getLong("id"), resultSet.getString("name"));

            return  user;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {

        List<User> list = new ArrayList<>();

        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from usr ")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                list.add(new User(resultSet.getLong("id"), resultSet.getString("name")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;

    }

    @Override
    public void save(User user) {
        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into usr(name, lastname) values (?, ?)")) {

            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2, user.getLastname() ==null? user.getLastname() : "");

            preparedStatement.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
