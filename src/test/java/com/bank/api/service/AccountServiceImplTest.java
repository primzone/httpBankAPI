package com.bank.api.service;

import com.bank.api.DAO.AccountDAOImpl;
import com.bank.api.exceptions.MyGlobalException;
import com.bank.api.utils.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceImplTest {

    @InjectMocks
    AccountServiceImpl accountService;

    @Mock
    AccountDAOImpl accountDAO = new AccountDAOImpl();
    @Mock
    Utils utils = new Utils();

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void addByUserId() {
//        System.out.println(1);
//        try {
//            accountService.addByUserId(1L);
//
//            Mockito.verify(accountDAO, Mockito.times(1)).addByUserId(1L, utils);
//        } catch (MyGlobalException myGlobalException) {
//            myGlobalException.printStackTrace();
//        }
    }

    @Test
    void findAllAccountsByUserId() {
        accountService.findAllAccountsByUserId(1L);
        Mockito.verify(accountDAO, Mockito.times(1)).findAllAccountsByUserId(1L);
    }

    @Test
    void addByContractorId() {

        try {
            accountService.addByContractorId(1L);
            Mockito.verify(accountDAO, Mockito.times(1)).addByContractorId(1l, ArgumentMatchers.anyString());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    @Test
    void findAccountIdbyCardNumber() {
    }

    @Test
    void getAccountBalanceById() {
    }
}