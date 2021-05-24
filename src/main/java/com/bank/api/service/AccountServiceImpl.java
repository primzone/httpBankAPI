package com.bank.api.service;

import com.bank.api.DAO.AccountDAO;
import com.bank.api.DAO.AccountDAOImpl;
import com.bank.api.entity.Account;
import com.bank.api.exceptions.MyGlobalException;
import com.bank.api.utils.Utils;

import java.sql.SQLException;
import java.util.List;

public class AccountServiceImpl implements AccountService{

    AccountDAO accountDAO = AccountDAOImpl.getInstance();

    @Override
    public void addByUserId(long userId) throws MyGlobalException {

        accountDAO.addByUserId(userId, Utils.generateAccountNumber());
    }

    @Override
    public List<Account> findAllAccountsByUserId(long userId) {

        return accountDAO.findAllAccountsByUserId(userId);
    }

    @Override
    public void addByContractorId(long contractorId) throws SQLException {
        accountDAO.addByContractorId(contractorId, Utils.generateAccountNumber());
    }

    @Override
    public long findAccountIdbyCardNumber(String cardNumber) throws SQLException {
        return accountDAO.findAccountIdByCardNumber(cardNumber);
    }

    @Override
    public double getAccountBalanceById(long senderAccountId) throws SQLException {
        return accountDAO.getAccountBalanceById(senderAccountId);
    }
}
