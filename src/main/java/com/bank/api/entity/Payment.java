package com.bank.api.entity;

public class Payment {
    private long id;
    private long accountId;
    private long transactionId;

    public Payment() {
    }

    public Payment(long accountId, long transactionId) {
        this.accountId = accountId;
        this.transactionId = transactionId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }
}
