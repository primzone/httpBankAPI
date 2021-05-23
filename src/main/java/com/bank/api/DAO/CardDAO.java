package com.bank.api.DAO;

import com.bank.api.entity.Card;
import com.bank.api.exceptions.MyGlobalException;

import java.sql.SQLException;
import java.util.List;

public interface CardDAO {
    List<Card> findAllCardsByUserId(long id);

    void refillAccountByCard(String cardNumber, double amount) throws MyGlobalException;

    void addCardToAccount(String accountNumber) throws MyGlobalException;

    void confirmCard(String cardNumber) throws SQLException;

    void checkCardConfirmation(String cardNumber) throws SQLException;
}
