package com.bank.api.exceptions;

import java.sql.SQLException;

public class MyGlobalException extends SQLException {

    public MyGlobalException() {
    }

    public MyGlobalException(String message) {
        super(message);
    }

}
