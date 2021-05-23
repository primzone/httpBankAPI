package com.bank.api.DAO;

import com.bank.api.DAO.connection.MyConnection;
import com.bank.api.entity.Contractor;
import com.bank.api.exceptions.MyGlobalException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContractorDAOImpl implements ContractorDAO{

    @Override
    public void addByUserId(long userId, String name, boolean corporation) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = new MyConnection().getConnection();
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(
                    "insert into contractor(name, corporation) values (?, ?);",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, name);
            preparedStatement.setBoolean(2, corporation);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            long newContractorId;
            if (generatedKeys != null && generatedKeys.next()){
                 newContractorId = generatedKeys.getLong(1);
            }
            else throw new MyGlobalException("failed to add contractor");


            //-------------
            PreparedStatement preparedStatement1 = connection.prepareStatement(
                    "insert into users_contractors (user_id, contractor_id) VALUES (?, ?) ON CONFLICT (user_id, contractor_id) DO NOTHING"
            );
            preparedStatement1.setLong(1, userId);
            preparedStatement1.setLong(2, newContractorId);
            preparedStatement1.executeUpdate();


            int i = preparedStatement1.executeUpdate();
            //if (i == 0) throw new MyGlobalException("failed to add contractor");
            connection.commit();

        }catch (SQLException throwables){
            if (connection != null) connection.rollback();
            throwables.printStackTrace();
            throw new MyGlobalException("failed to add contractor");
        }finally {
            if (connection != null) connection.close();
            if (preparedStatement != null) connection.close();

        }







    }

    @Override
    public List<Contractor> findAllByUserId(long userId) throws SQLException {
        Connection connection = new MyConnection().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "select id, name, corporation, description from contractor " +
                        "join users_contractors uc on contractor.id = uc.contractor_id " +
                        "where uc.user_id = ?");
        preparedStatement.setLong(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<Contractor> contractorList = new ArrayList<>();

        while (resultSet.next()){
            contractorList.add(new Contractor(resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getBoolean("corporation"),
                    resultSet.getString("description")));
        }

        return contractorList;

    }
}
