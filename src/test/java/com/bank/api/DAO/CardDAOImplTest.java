package com.bank.api.DAO;

import com.bank.api.DAO.connection.MyConnection;
import com.bank.api.entity.Card;
import com.bank.api.exceptions.MyGlobalException;
import com.bank.api.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
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

class CardDAOImplTest {

    @InjectMocks
    private CardDAOImpl cardDAO;


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
        assertNotNull(CardDAOImpl.getInstance());
    }

    @Test
    void findAllCardsByUserId() {

        List<Card> allCardsByUserId = cardDAO.findAllCardsByUserId(1L);

        List<Card> cardList = new ArrayList<>();

        cardList.add(new Card(1, "6743 8237 9032 8734", true));
        cardList.add(new Card(2, "2341 9832 4367 7777", true));
        cardList.add(new Card(4, "1241 3257 1970 3427", false));

        assertEquals(allCardsByUserId, cardList);

    }

    @Test
    void refillAccountByCard(){



        try( Connection connection = new MyConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select balance from account " +
                             "join card c on account.id = c.account_id " +
                             "where card_number = '6743 8237 9032 8734'"
             )) {

            cardDAO.refillAccountByCard("6743 8237 9032 8734", 50000);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            assertEquals(150000, resultSet.getDouble("balance"));

        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }

    }

    @Test
    void addCardToAccount() {

        try( Connection connection = new MyConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select id, card_number, confirmation, account_id from card " +
                             "where account_id = 2 " +
                             "order by id desc limit 1"
             )) {

            cardDAO.addCardToAccount("91381939012482346177");

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            assertEquals(5L, resultSet.getLong("id"));
            //assertEquals(ArgumentMatchers.anyString(), resultSet.getString("card_number"));
            assertFalse(resultSet.getBoolean("confirmation"));
            assertEquals(2L, resultSet.getLong("account_id"));

        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }



    }

    @Test
    void confirmCard() {
        try( Connection connection = new MyConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select confirmation from card " +
                             "where card_number = '1241 3257 1970 3427'"
             )) {

            cardDAO.confirmCard("1241 3257 1970 3427");

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            assertTrue(resultSet.getBoolean("confirmation"));

        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    @Test
    void checkCardConfirmation() {


    }
}