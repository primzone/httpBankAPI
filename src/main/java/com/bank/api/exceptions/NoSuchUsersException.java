package com.bank.api.exceptions;

public class NoSuchUsersException extends MyGlobalException{

    public NoSuchUsersException() {
    }

    public NoSuchUsersException(String message) {
        super(message);
    }

}
