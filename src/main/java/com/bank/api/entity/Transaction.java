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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (id != that.id) return false;
        if (Double.compare(that.amount, amount) != 0) return false;
        if (confirmation != that.confirmation) return false;
        if (recipient_account_id != that.recipient_account_id) return false;
        if (sender_account_id != that.sender_account_id) return false;
        if (transaction_number != that.transaction_number) return false;
        if (recipient_card_number != null ? !recipient_card_number.equals(that.recipient_card_number) : that.recipient_card_number != null)
            return false;
        return sender_card_number != null ? sender_card_number.equals(that.sender_card_number) : that.sender_card_number == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (confirmation ? 1 : 0);
        result = 31 * result + (int) (recipient_account_id ^ (recipient_account_id >>> 32));
        result = 31 * result + (int) (sender_account_id ^ (sender_account_id >>> 32));
        result = 31 * result + (recipient_card_number != null ? recipient_card_number.hashCode() : 0);
        result = 31 * result + (sender_card_number != null ? sender_card_number.hashCode() : 0);
        result = 31 * result + (int) (transaction_number ^ (transaction_number >>> 32));
        return result;
    }
}
