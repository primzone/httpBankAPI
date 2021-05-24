package com.bank.api.DAO;

import com.bank.api.DAO.connection.MyConnection;
import com.bank.api.entity.Card;
import com.bank.api.exceptions.MyGlobalException;
import com.bank.api.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardDAOImpl implements CardDAO{

    private static CardDAOImpl instance;
    private CardDAOImpl(){}
    public static CardDAOImpl getInstance(){
        if(instance == null){
            instance = new CardDAOImpl();
        }
        return instance;
    }

    @Override
    public List<Card> findAllCardsByUserId(long id) {

        List<Card> cards = new ArrayList<>();

        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select c.id, c.card_number, c.confirmation from account " +
                    "join card c on account.id = c.account_id " +
                    "where account.user_id = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                cards.add(new Card(resultSet.getLong("id"),
                                   resultSet.getString("card_number"),
                                   resultSet.getBoolean("confirmation")));
            }
            return cards;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public void refillAccountByCard(String cardNumber, double amount) throws MyGlobalException {
        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update account set balance = (balance+ ?) " +
                    "where id = (select account_id from card where card_number = ?)")) {
            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(2, cardNumber);

            int i = preparedStatement.executeUpdate();
            if (i == 0) throw new MyGlobalException("Card " + cardNumber + " not found");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new MyGlobalException("Card " + cardNumber + " not found");
        }

    }

    @Override
    public void addCardToAccount(String accountNumber) throws MyGlobalException {


        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into card(account_id, card_number) " +
                         "values ((select id from account where account_number = ?), ?)")) {

            preparedStatement.setString(1, accountNumber);
            preparedStatement.setString(2, Utils.generateCardNumber());

            int i = preparedStatement.executeUpdate();
            if (i == 0) throw new MyGlobalException("Account " + accountNumber + " not found");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new MyGlobalException("Account " + accountNumber + " not found");
        }



    }

    @Override
    public void confirmCard(String cardNumber) throws SQLException {

        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update card set confirmation = true where card_number = ?")){

            preparedStatement.setString(1, cardNumber);
            int i = preparedStatement.executeUpdate();
            if (i == 0) throw new MyGlobalException("Card already confirmed");

        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            throw new MyGlobalException("failed to confirm card " + cardNumber);
        }




    }

    @Override
    public void checkCardConfirmation(String cardNumber) throws SQLException {
        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select confirmation from card where card_number =?")){

            preparedStatement.setString(1, cardNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (!resultSet.getBoolean("confirmation"))
                throw new MyGlobalException("Card" + cardNumber + " not confirmed");

        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            throw new MyGlobalException("Failed to check card confirmation" + cardNumber);
        }
    }
}
