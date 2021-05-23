package com.bank.api.utils;

import com.bank.api.DAO.connection.MyConnection;
import com.bank.api.exceptions.NoSuchUsersException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Utils {



    // Возвращает новый номер карты
    public static String generateCardNumber(){

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(generateRandomNumber4digit()).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    // Возвращает новый номер аккаунта
    public static String generateAccountNumber(){

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(generateRandomNumber4digit());
        }

        return sb.toString();
    }

    //генерация числа от 1000 до 9999
    static int generateRandomNumber4digit(){
        return 1000 + (int) (Math.random() * 8999);
    }

    public static Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        if (query == null) return result;
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }


    public static void createAndInitDb() throws IOException {


        Scanner scanner = new Scanner(Paths.get(
                Utils.class.getClassLoader().getResource("appRun.sql").getPath()),
                StandardCharsets.UTF_8.name());

        String data = scanner.useDelimiter("\\Z").next();
        scanner.close();



        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(data)) {


            preparedStatement.executeUpdate();



        } catch (Exception throwables) {
            throwables.printStackTrace();
            //throw new Exception("Create and init db failed");
        }



    }

}
