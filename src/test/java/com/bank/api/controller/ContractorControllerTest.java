package com.bank.api.controller;

import com.bank.api.entity.Account;
import com.bank.api.entity.Contractor;
import com.bank.api.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ContractorControllerTest {

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



    @Test//проверка списка контрагентов юзера
    void handle() throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8000/api/users/contractors?id=1");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        assertEquals(200, response.getStatusLine().getStatusCode());

        Scanner sc = new Scanner(response.getEntity().getContent());
        String data = sc.useDelimiter("\\Z").next();
        JSONPObject jsonObject = new JSONPObject(data, JSONPObject.class);


        List<Contractor> contractorList = new ArrayList<>();
        contractorList.add(new Contractor(1, "Kate", false));
        contractorList.add(new Contractor(2, "Viktor", false));

        String expected = mapper.writeValueAsString(contractorList);

        assertEquals(expected, jsonObject.getFunction());
    }


    @Test//проверка списка контрагентов юзера по несуществующему айди
    void handleGET2() throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8000/api/users/contractors?id=100");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        assertEquals(200, response.getStatusLine().getStatusCode());

        Scanner sc = new Scanner(response.getEntity().getContent());
        String data = sc.useDelimiter("\\Z").next();
        JSONPObject jsonObject = new JSONPObject(data, JSONPObject.class);

        List<Contractor> contractorList = new ArrayList<>();
        String expected = mapper.writeValueAsString(contractorList);

        assertEquals(expected, jsonObject.getFunction());
    }
}