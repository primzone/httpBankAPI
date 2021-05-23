package com.bank.api.service;

import java.sql.SQLException;

public interface TransactionService {
    void moneyTransferToAccount(String cardNumber,
                                String contractorCardNumber,
                                double amount) throws SQLException;

    void confirmTransaction(long transactionNumber) throws SQLException;

}
