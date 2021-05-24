package com.bank.api.controller;

import com.bank.api.entity.Account;
import com.bank.api.entity.Card;
import com.bank.api.exceptions.MyGlobalException;
import com.bank.api.responses.MyInfoResponse;


import com.bank.api.responses.SendMyResponses;
import com.bank.api.service.AccountService;
import com.bank.api.service.AccountServiceImpl;
import com.bank.api.service.CardService;
import com.bank.api.service.CardServiceImpl;
import com.bank.api.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CardsController implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CardService cardService = new CardServiceImpl();

        if ("GET".equals(exchange.getRequestMethod())) {

           Map<String, String> queryMap = Utils.queryToMap(exchange.getRequestURI().getRawQuery());

           List<Card> cards = cardService.findAllCardsByUserId(Long.parseLong(queryMap.get("id")));
           String response = mapper.writeValueAsString(cards);
           SendMyResponses.sendMyResponse(exchange, response, 200);


        }
        else if ("POST".equals(exchange.getRequestMethod()) && exchange.getRequestURI().toString().equals("/api/users/cards/refill")){
            //внесение средств на счет по карте (пополнение баланса)

            ObjectNode objectNode = mapper.readValue(exchange.getRequestBody(), ObjectNode.class);

            try{
                cardService.refillAccountByCard(objectNode.get("cardNumber").asText(), objectNode.get("amount").asDouble());
                SendMyResponses.sendMyResponse(exchange, MyInfoResponse.getMyInfoResponseJSON("Success"), 200);
            }
            catch (SQLException sqlException) {

                String info = mapper.writeValueAsString(new MyInfoResponse(sqlException.getMessage()));
                SendMyResponses.sendMyResponse(exchange, info, 400);

            }
        }
        else if ("POST".equals(exchange.getRequestMethod())){
            //выпуск новой карты по счету
            ObjectNode objectNode = mapper.readValue(exchange.getRequestBody(), ObjectNode.class);

            try{
                cardService.addCardToAccount(objectNode.get("accountNumber").asText());
               // mapper.writeValueAsString(new MyResponse(true));
                SendMyResponses.sendMyResponse(exchange, MyInfoResponse.getMyInfoResponseJSON("Success"), 200);
            }
            catch (MyGlobalException myGlobalException) {

                String info = mapper.writeValueAsString(new MyInfoResponse(myGlobalException.getMessage()));
                SendMyResponses.sendMyResponse(exchange, info, 400);

            }

        }
        else if ("PUT".equals(exchange.getRequestMethod())){
            //потверждение выпуска карты
            ObjectNode objectNode = mapper.readValue(exchange.getRequestBody(), ObjectNode.class);

            try {
                cardService.confirmCard(objectNode.get("cardNumber").asText());

                SendMyResponses.sendMyResponse(exchange, MyInfoResponse.getMyInfoResponseJSON("Success"), 200 );

            }catch (SQLException sqlException) {

                String info = mapper.writeValueAsString(new MyInfoResponse(sqlException.getMessage()));
                SendMyResponses.sendMyResponse(exchange, info, 400);

            }

        }
        else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();

    }
}
