package com.bank.api.controller;

import com.bank.api.DAO.connection.MyConnection;
import com.bank.api.entity.Card;
import com.bank.api.responses.MyInfoResponse;
import com.bank.api.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CardsControllerTest {

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        try {
            Utils.createAndInitDb();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    ObjectMapper mapper = new ObjectMapper();



    @Test
    void handleGET() throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8000/api/users/cards?id=1");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        assertEquals(200, response.getStatusLine().getStatusCode());

        Scanner sc = new Scanner(response.getEntity().getContent());
        String data = sc.useDelimiter("\\Z").next();
        JSONPObject jsonObject = new JSONPObject(data, JSONPObject.class);

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(1, "6743 8237 9032 8734", true));
        cards.add(new Card(2, "2341 9832 4367 7777", true));
        cards.add(new Card(4, "1241 3257 1970 3427", false));

        String expected = mapper.writeValueAsString(cards);

        assertEquals(expected, jsonObject.getFunction());

    }


    @Test
    void handlePOST() throws IOException {

        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("accountNumber", "91381939012482346177");
        String ObjectJSON = mapper.writeValueAsString(objectNode);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8000/api/users/cards");
        httpPost.setEntity(new StringEntity(ObjectJSON, ContentType.APPLICATION_JSON));
        CloseableHttpResponse response = httpClient.execute(httpPost);

        assertEquals(200, response.getStatusLine().getStatusCode());

        //проверка боди
        Scanner sc = new Scanner(response.getEntity().getContent());
        String data = sc.useDelimiter("\\Z").next();
        JSONPObject jsonObject = new JSONPObject(data, JSONPObject.class);
        assertEquals(MyInfoResponse.getMyInfoResponseJSON("Success"), jsonObject.getFunction());

        //проверка в бд
        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select id, card_number, confirmation, account_id from card order by id desc limit 1"
            )) {

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            assertEquals(5L, resultSet.getLong("id"));
            assertEquals(19, resultSet.getString("card_number").length());
            assertFalse(resultSet.getBoolean("confirmation"));
            assertEquals(2L, resultSet.getLong("account_id"));

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        httpClient.close();
    }



    @Test//проверка внесения средств
    void handlePOST2() throws IOException {

        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("cardNumber", "6743 8237 9032 8734");
        objectNode.put("amount", 100000f);
        String ObjectJSON = mapper.writeValueAsString(objectNode);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8000/api/users/cards/refill");
        httpPost.setEntity(new StringEntity(ObjectJSON, ContentType.APPLICATION_JSON));
        CloseableHttpResponse response = httpClient.execute(httpPost);

        assertEquals(200, response.getStatusLine().getStatusCode());

        //проверка боди
        Scanner sc = new Scanner(response.getEntity().getContent());
        String data = sc.useDelimiter("\\Z").next();
        JSONPObject jsonObject = new JSONPObject(data, JSONPObject.class);
        assertEquals(MyInfoResponse.getMyInfoResponseJSON("Success"), jsonObject.getFunction());


        //проверка в бд
        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select balance from account " +
                            "join card c on account.id = c.account_id" +
                            " where card_number = '6743 8237 9032 8734'"
            )) {

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            assertEquals(200000, resultSet.getLong("balance"));


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        httpClient.close();


    }

    @Test//проверка внесения средств
    void handlePOST3() throws IOException {

        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("cardNumber", "1241 3257 1970 3427");//непотвержденная карта
        objectNode.put("amount", 100000f);
        String ObjectJSON = mapper.writeValueAsString(objectNode);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8000/api/users/cards/refill");
        httpPost.setEntity(new StringEntity(ObjectJSON, ContentType.APPLICATION_JSON));
        CloseableHttpResponse response = httpClient.execute(httpPost);

        assertEquals(400, response.getStatusLine().getStatusCode());


    }

}