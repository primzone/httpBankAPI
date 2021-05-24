package com.bank.api.service;

import com.bank.api.DAO.AccountDAOImpl;
import com.bank.api.entity.Account;
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

class AccountServiceImplTest {

    @InjectMocks
    AccountServiceImpl accountService;

    @Mock
    AccountDAOImpl accountDAOMOCK = AccountDAOImpl.getInstance();

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
    void addByUserId() {

        try {
            accountService.addByUserId(1L);

            Mockito.verify(accountDAOMOCK, Mockito.times(1))
                    .addByUserId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
        } catch (MyGlobalException myGlobalException) {
            myGlobalException.printStackTrace();
        }
    }

    @Test
    void findAllAccountsByUserId() {

        List<Account> accountsFromMock = new ArrayList<>();
        Mockito.when(accountDAOMOCK.findAllAccountsByUserId(1L)).thenReturn(accountsFromMock);
        List<Account> accounts = accountService.findAllAccountsByUserId(1L);
        assertEquals(accounts, accountsFromMock);

    }

    @Test
    void addByContractorId() {

        try {
            accountService.addByContractorId(1L);
            Mockito.verify(accountDAOMOCK, Mockito.times(1))
                    .addByContractorId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    void findAccountIdbyCardNumber() {

        long l = 0;
        try {
            Mockito.when(accountDAOMOCK.findAccountIdByCardNumber("3452 4352 3257 3452")).thenReturn(l);
            long answer = accountService.findAccountIdbyCardNumber("3452 4352 3257 3452");
            assertEquals(answer, l);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Test
    void getAccountBalanceById() {

        double d = 0;
        try {
            Mockito.when(accountDAOMOCK.getAccountBalanceById(1L)).thenReturn(d);
            double answer = accountService.getAccountBalanceById(1L);
            assertEquals(answer, d);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}