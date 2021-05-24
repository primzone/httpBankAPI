package com.bank.api.service;

import com.bank.api.DAO.AccountDAOImpl;
import com.bank.api.DAO.CardDAOImpl;
import com.bank.api.entity.Account;
import com.bank.api.entity.Card;
import com.bank.api.exceptions.MyGlobalException;
import com.bank.api.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardServiceImplTest {

    @InjectMocks
    CardServiceImpl cardService;

    @Mock
    CardDAOImpl cardDAOMOCK = CardDAOImpl.getInstance();

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
    void findAllCardsByUserId() {

        List<Card> cardsFromMock = new ArrayList<>();
        Mockito.when(cardDAOMOCK.findAllCardsByUserId(1L)).thenReturn(cardsFromMock);
        List<Card> cards = cardService.findAllCardsByUserId(1L);
        assertEquals(cards, cardsFromMock);
        Mockito.verify(cardDAOMOCK, Mockito.times(1)).findAllCardsByUserId(1L);

    }

    @Test
    void refillAccountByCard() {
        try {
            cardService.refillAccountByCard("3452 2345 4335 2109", 500);
            Mockito.verify(cardDAOMOCK, Mockito.times(1))
                    .refillAccountByCard("3452 2345 4335 2109", 500);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    void addCardToAccount() throws MyGlobalException {

            cardService.addCardToAccount("63745827461009847635");
            Mockito.verify(cardDAOMOCK, Mockito.times(1))
                    .addCardToAccount("63745827461009847635");

    }

    @Test
    void confirmCard() {
        try {
            cardService.confirmCard(ArgumentMatchers.anyString());
            Mockito.verify(cardDAOMOCK, Mockito.times(1))
                    .confirmCard(ArgumentMatchers.anyString());

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    void checkCardConfirmation() {

        try {
            cardService.confirmCard("3452 2345 4335 2109");
            Mockito.verify(cardDAOMOCK, Mockito.times(1))
                    .confirmCard("3452 2345 4335 2109");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }
}