package com.bank.api.DAO.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnection {
    private Connection connection;

    private final Properties props = new Properties();


    public Connection getConnection() throws SQLException{
        if (connection == null){
            props.setProperty("user","postgres");
            props.setProperty("password","postgres");
            props.setProperty("ssl","false");
            String connectiobURL = "jdbc:postgresql://localhost:5432/bankdb";
            connection = DriverManager.getConnection(connectiobURL, props);
            return connection;
        }
        else return connection;

    }




}
