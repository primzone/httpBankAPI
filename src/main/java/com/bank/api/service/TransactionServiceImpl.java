package com.bank.api.service;

import com.bank.api.DAO.TransactionDAO;
import com.bank.api.DAO.TransactionDAOImpl;
import com.bank.api.entity.Transaction;
import com.bank.api.exceptions.MyGlobalException;

import java.sql.SQLException;

public class TransactionServiceImpl implements TransactionService{

    TransactionDAO transactionDAO = new TransactionDAOImpl();
    AccountService accountService = new AccountServiceImpl();
    CardService cardService = new CardServiceImpl();

    @Override
    public void moneyTransferToAccount(String cardNumber, String recipientCardNumber, double amount) throws SQLException {

        long senderAccountId = accountService.findAccountIdbyCardNumber(cardNumber);
        long recipientAccountId = accountService.findAccountIdbyCardNumber(recipientCardNumber);
        cardService.checkCardConfirmation(cardNumber);
        double senderAccountBalance = accountService.getAccountBalanceById(senderAccountId);
        if (senderAccountBalance < amount)
            throw new MyGlobalException("not enough money on the card" + cardNumber);

        transactionDAO.moneyTransferToContractorAccount(senderAccountId, recipientAccountId,
                cardNumber, recipientCardNumber, amount);
    }

    @Override
    public void confirmTransaction(long transactionNumber) throws SQLException {

        Transaction transaction = transactionDAO.findByTransactionNumber(transactionNumber);
        if (transaction.isConfirmation())
            throw new MyGlobalException("Transaction already confirmed");

        double senderAccountBalance = accountService.getAccountBalanceById(transaction.getSender_account_id());
        if (senderAccountBalance < transaction.getAmount())
            throw new MyGlobalException("not enough money on to confirm transaction");

        transactionDAO.confirmTransaction(transaction);


    }
}
