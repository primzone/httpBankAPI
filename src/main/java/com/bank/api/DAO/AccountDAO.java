package com.bank.api.DAO;

import com.bank.api.entity.Account;
import com.bank.api.exceptions.MyGlobalException;

import java.sql.SQLException;
import java.util.List;

public interface AccountDAO {

    void addByUserId(long userId, String generateAccountNumber) throws MyGlobalException;

    List<Account> findAllAccountsByUserId(long userId);

    void addByContractorId(long contractorId, String generateAccountNumber) throws SQLException;

    long findAccountIdByCardNumber(String cardNumber) throws SQLException;

    double getAccountBalanceById(long senderAccountId) throws SQLException;
}
