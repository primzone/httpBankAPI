package com.bank.api.DAO;

import com.bank.api.DAO.connection.MyConnection;
import com.bank.api.entity.Transaction;
import com.bank.api.exceptions.MyGlobalException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDAOImpl implements TransactionDAO{

    private static TransactionDAOImpl instance;
    private TransactionDAOImpl(){}
    public static TransactionDAOImpl getInstance(){
        if(instance == null){
            instance = new TransactionDAOImpl();
        }
        return instance;
    }

    @Override
    public void moneyTransferToContractorAccount(long senderAccountId,
                                                 long recipientAccountId,
                                                 String cardNumber,
                                                 String recipientCardNumber,
                                                 double amount) throws SQLException {


        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into transaction(sender_account_id, recipient_account_id, " +
                            "sender_card_number, recipient_card_number, amount) " +
                            "values (?, ?, ?, ?, ?)")) {

            preparedStatement.setLong(1, senderAccountId);
            preparedStatement.setLong(2, recipientAccountId);
            preparedStatement.setString(3, cardNumber);
            preparedStatement.setString(4, recipientCardNumber);
            preparedStatement.setDouble(5, amount);

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new MyGlobalException("Failed money transfer");
        }



    }

    @Override
    public Transaction findByTransactionNumber(long transactionNumber) throws SQLException {

        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select id, amount, confirmation, sender_account_id," +
                            " recipient_account_id, sender_card_number," +
                            " recipient_card_number, transaction_number " +
                            "from transaction where transaction_number = ?")) {

            preparedStatement.setLong(1, transactionNumber);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new Transaction(resultSet.getLong("id"),
                    resultSet.getDouble("amount"),
                    resultSet.getBoolean("confirmation"),
                    resultSet.getLong("recipient_account_id"),
                    resultSet.getLong("sender_account_id"),
                    resultSet.getString("recipient_card_number"),
                    resultSet.getString("sender_card_number"),
                    resultSet.getLong("transaction_number"));


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new MyGlobalException("Failed to find transaction " + transactionNumber);
        }


    }

    @Override
    public void confirmTransaction(Transaction transaction) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = new MyConnection().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(
                    "update transaction set confirmation = true where transaction_number = ?");


            preparedStatement.setLong(1, transaction.getTransaction_number());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    "update account set balance = (balance - ?) where id = ?"
            );
            preparedStatement.setDouble(1, transaction.getAmount());
            preparedStatement.setDouble(2, transaction.getSender_account_id());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(
                    "update account set balance = (balance + ?) where id = ?"
            );
            preparedStatement.setDouble(1, transaction.getAmount());
            preparedStatement.setDouble(2, transaction.getRecipient_account_id());
            preparedStatement.executeUpdate();


            connection.commit();
        }catch (SQLException throwables){
            if (connection != null) connection.rollback();
            throwables.printStackTrace();
            throw new MyGlobalException("failed to confirm transaction");
        }finally {
            if (connection != null) connection.close();
            if (preparedStatement != null) connection.close();

        }



    }
}
