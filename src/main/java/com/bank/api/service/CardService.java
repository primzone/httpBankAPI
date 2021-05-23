package com.bank.api.service;

import com.bank.api.entity.Card;
import com.bank.api.exceptions.MyGlobalException;

import java.sql.SQLException;
import java.util.List;

public interface CardService {
    List<Card> findAllCardsByUserId(long id);

    void refillAccountByCard(String cardNumber, double amount) throws SQLException;
    void addCardToAccount(String accountNumber) throws MyGlobalException;

    void confirmCard(String cardNumber) throws SQLException;

    void checkCardConfirmation(String cardNumber) throws SQLException;
}
