package com.bank.api.service;

import com.bank.api.DAO.CardDAO;
import com.bank.api.DAO.CardDAOImpl;
import com.bank.api.entity.Card;
import com.bank.api.exceptions.MyGlobalException;

import java.sql.SQLException;
import java.util.List;

public class CardServiceImpl implements CardService{
    CardDAO cardDAO = new CardDAOImpl();

    @Override
    public List<Card> findAllCardsByUserId(long id) {

        List<Card> list = cardDAO.findAllCardsByUserId(id);

        return list;

    }

    @Override
    public void refillAccountByCard(String cardNumber, double amount) throws SQLException {

        checkCardConfirmation(cardNumber);
        cardDAO.refillAccountByCard(cardNumber, amount);


    }

    @Override
    public void addCardToAccount(String accountNumber) throws MyGlobalException {

        cardDAO.addCardToAccount(accountNumber);

    }

    @Override
    public void confirmCard(String cardNumber) throws SQLException {
        cardDAO.confirmCard(cardNumber);

    }

    @Override
    public void checkCardConfirmation(String cardNumber) throws SQLException {

        cardDAO.checkCardConfirmation(cardNumber);

    }
}
