package com.bank.api.DAO;

import com.bank.api.entity.Transaction;

import java.sql.SQLException;

public interface TransactionDAO {
    void moneyTransferToContractorAccount(long senderAccountId, long recipientAccountId,
           String cardNumber, String recipientCardNumber, double amount) throws SQLException;

    Transaction findByTransactionNumber(long transactionNumber) throws SQLException;

    void confirmTransaction(Transaction transaction) throws SQLException;
}
