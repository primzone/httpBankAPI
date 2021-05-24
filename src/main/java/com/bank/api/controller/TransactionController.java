package com.bank.api.controller;

import com.bank.api.responses.MyInfoResponse;
import com.bank.api.responses.SendMyResponses;
import com.bank.api.service.TransactionService;
import com.bank.api.service.TransactionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;

public class TransactionController implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        TransactionService transactionService = new TransactionServiceImpl();

        if ("PUT".equals(exchange.getRequestMethod())) {
            //потверждение перевода
            ObjectNode objectNode = mapper.readValue(exchange.getRequestBody(), ObjectNode.class);
            try{

                transactionService.confirmTransaction(objectNode.get("transactionNumber").asLong());

                SendMyResponses.sendMyResponse(exchange, MyInfoResponse.getMyInfoResponseJSON("Success"), 200);
            }catch (SQLException sqlException){
                String info = mapper.writeValueAsString(new MyInfoResponse(sqlException.getMessage()));
                SendMyResponses.sendMyResponse(exchange, info, 400);
            }


        }
        else if ("POST".equals(exchange.getRequestMethod())){
            //перевод средств контрагенту
            ObjectNode objectNode = mapper.readValue(exchange.getRequestBody(), ObjectNode.class);

            try {
                transactionService.moneyTransferToAccount(objectNode.get("cardNumber").asText(),
                        objectNode.get("contractorCardNumber").asText(),
                        objectNode.get("amount").asDouble());

                SendMyResponses.sendMyResponse(exchange, MyInfoResponse.getMyInfoResponseJSON("Success"), 200);

            }catch (SQLException sqlException){
                String info = mapper.writeValueAsString(new MyInfoResponse(sqlException.getMessage()));
                SendMyResponses.sendMyResponse(exchange, info, 400);
            }catch (Exception e){
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }


        }
        else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();



    }
}
