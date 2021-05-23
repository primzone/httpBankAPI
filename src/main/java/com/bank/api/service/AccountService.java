package com.bank.api.service;

import com.bank.api.entity.Account;
import com.bank.api.exceptions.MyGlobalException;

import java.sql.SQLException;
import java.util.List;

public interface AccountService {
    void addByUserId(long userId) throws MyGlobalException;

    List<Account> findAllAccountsByUserId(long userId);

    void addByContractorId(long contractorId) throws SQLException;

    long findAccountIdbyCardNumber(String cardNumber) throws SQLException;

    double getAccountBalanceById(long senderAccountId) throws SQLException;

}
