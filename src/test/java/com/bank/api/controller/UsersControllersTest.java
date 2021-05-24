package com.bank.api.controller;

import com.bank.api.DAO.AccountDAOImpl;
import com.bank.api.DAO.connection.MyConnection;
import com.bank.api.entity.User;
import com.bank.api.utils.Utils;
import com.fasterxml.jackson.databind.JsonNode;
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
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.swing.text.html.parser.DTD;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class UsersControllersTest {

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
    void handlePOST2() throws IOException {

        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("name", "Test");
        objectNode.put("lastname", "Proverkovich");
        String ObjectJSON = mapper.writeValueAsString(objectNode);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8000/api/employee/users");
        httpPost.setEntity(new StringEntity(ObjectJSON, ContentType.APPLICATION_JSON));
        CloseableHttpResponse response = httpClient.execute(httpPost);

        assertEquals(200, response.getStatusLine().getStatusCode());


        try(Connection connection = new MyConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select id, name, lastname from usr order by id desc limit 1"
            )) {

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            assertEquals(3L, resultSet.getLong("id"));
            assertEquals("Test", resultSet.getString("name"));
            assertEquals("Proverkovich", resultSet.getString("lastname"));

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        httpClient.close();

    }


    @Test
    void handleGET() throws IOException {


        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8000/api/employee/users");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        assertEquals(200, response.getStatusLine().getStatusCode());

        Scanner sc = new Scanner(response.getEntity().getContent());
        String data = sc.useDelimiter("\\Z").next();
        JSONPObject jsonObject = new JSONPObject(data, JSONPObject.class);

        List<User> list = new ArrayList<>();
        list.add(new User(1, "Narek"));
        list.add(new User(2, "Roman"));

        String expected = mapper.writeValueAsString(list);

        assertEquals(expected, jsonObject.getFunction());


        httpClient.close();

    }


}