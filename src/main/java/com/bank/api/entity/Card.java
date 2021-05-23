package com.bank.api.entity;

public class Card {
    private long id;
    private String cardNumber;
    private boolean confirmation;


    public Card() {
    }

    public Card(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Card(String cardNumber, boolean confirmation) {
        this.cardNumber = cardNumber;
        this.confirmation = confirmation;
    }

    public Card(long id, String cardNumber, boolean confirmation) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.confirmation = confirmation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }
}
