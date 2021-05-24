package com.bank.api.DAO;

import com.bank.api.DAO.connection.MyConnection;
import com.bank.api.entity.Transaction;
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

import static org.junit.jupiter.api.Assertions.*;

class TransactionDAOImplTest {

    @InjectMocks
    private TransactionDAOImpl transactionDAO;


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
        assertNotNull(transactionDAO.getInstance());
    }

    @Test
    void moneyTransferToContractorAccount() {

        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select id, amount, confirmation, sender_account_id," +
                            " recipient_account_id, sender_card_number, recipient_card_number," +
                            " transaction_number " +
                            "from transaction order by id desc limit 1"
            )) {

            transactionDAO.moneyTransferToContractorAccount(1L,
                    4L,
                    "6743 8237 9032 8734",
                    "4322 6543 6436 1309",
                    50000);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            assertEquals(2L, resultSet.getLong("id"));
            assertEquals(1L, resultSet.getLong("sender_account_id"));
            assertEquals(4L, resultSet.getLong("recipient_account_id"));
            assertEquals("6743 8237 9032 8734", resultSet.getString("sender_card_number"));
            assertEquals("4322 6543 6436 1309", resultSet.getString("recipient_card_number"));
            assertEquals(2L, resultSet.getLong("transaction_number"));
            assertEquals(50000, resultSet.getDouble("amount"));
            assertFalse(resultSet.getBoolean("confirmation"));


        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }

    }

    @Test
    void findByTransactionNumber() {
        try {

            Transaction transaction = transactionDAO.findByTransactionNumber(1L);
           Transaction checkTransaction = new Transaction(1, 50000,false,
                   4,1,
                   "4322 6543 6436 1309",
                   "6743 8237 9032 8734",
                   1);


            assertEquals(transaction, checkTransaction);


        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    @Test
    void confirmTransaction() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = new MyConnection().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(
                    "select confirmation from transaction where transaction_number = 1"
            );

            Transaction checkTransaction = new Transaction(1, 50000,false,
                    4,1,
                    "4322 6543 6436 1309",
                    "6743 8237 9032 8734",
                    1);
            transactionDAO.confirmTransaction(checkTransaction);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            assertTrue(resultSet.getBoolean("confirmation"));

            preparedStatement = connection.prepareStatement(
                    "select balance from account where id = 1"
            );

            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            assertEquals(50000, resultSet.getDouble("balance"));

            preparedStatement = connection.prepareStatement(
                    "select balance from account where id = 4"
            );

            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            assertEquals(240000, resultSet.getDouble("balance"));




        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        finally {
            if (connection != null) connection.close();
            if (preparedStatement != null) connection.close();
        }
    }
}