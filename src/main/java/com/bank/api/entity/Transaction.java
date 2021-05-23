package com.bank.api.entity;

public class Transaction {

    private long id;
    private double amount;
    private boolean confirmation;
    private long recipient_account_id;
    private long sender_account_id;
    private String recipient_card_number;
    private String sender_card_number;
    private long transaction_number;

    public Transaction() {
    }

    public Transaction(String sender_card_number, String recipient_card_number, double amount) {
        this.amount = amount;
        this.recipient_card_number = recipient_card_number;
        this.sender_card_number = sender_card_number;
    }

    public Transaction(long id, double amount, boolean confirmation, long recipient_account_id,
                       long sender_account_id, String recipient_card_number,
                       String sender_card_number, long transaction_number) {
        this.id = id;
        this.amount = amount;
        this.confirmation = confirmation;
        this.recipient_account_id = recipient_account_id;
        this.sender_account_id = sender_account_id;
        this.recipient_card_number = recipient_card_number;
        this.sender_card_number = sender_card_number;
        this.transaction_number = transaction_number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }

    public long getRecipient_account_id() {
        return recipient_account_id;
    }

    public void setRecipient_account_id(long recipient_account_id) {
        this.recipient_account_id = recipient_account_id;
    }

    public long getSender_account_id() {
        return sender_account_id;
    }

    public void setSender_account_id(long sender_account_id) {
        this.sender_account_id = sender_account_id;
    }

    public String getRecipient_card_number() {
        return recipient_card_number;
    }

    public void setRecipient_card_number(String recipient_card_number) {
        this.recipient_card_number = recipient_card_number;
    }

    public String getSender_card_number() {
        return sender_card_number;
    }

    public void setSender_card_number(String sender_card_number) {
        this.sender_card_number = sender_card_number;
    }

    public long getTransaction_number() {
        return transaction_number;
    }

    public void setTransaction_number(long transaction_number) {
        this.transaction_number = transaction_number;
    }
}
