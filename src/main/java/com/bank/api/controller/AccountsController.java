package com.bank.api.controller;

import com.bank.api.entity.Account;
import com.bank.api.exceptions.MyGlobalException;
import com.bank.api.responses.MyInfoResponse;

import com.bank.api.responses.SendMyResponses;
import com.bank.api.service.AccountService;
import com.bank.api.service.AccountServiceImpl;
import com.bank.api.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AccountsController implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        AccountService accountService = new AccountServiceImpl();

        if ("GET".equals(exchange.getRequestMethod())) {
            //проверка баланса
            Map<String, String> queryMap = Utils.queryToMap(exchange.getRequestURI().getRawQuery());

            List<Account> accounts = accountService.findAllAccountsByUserId(Long.parseLong(queryMap.get("id")));

            String s = mapper.writeValueAsString(accounts);
            SendMyResponses.sendMyResponse(exchange, s, 200);

        }
        else if ("POST".equals(exchange.getRequestMethod()) && exchange.getRequestURI().toString().equals("api/employee/contractors/accounts")){
            //кароче нужно удалить эту обработку и делать все в одной проверив какое поле пришло из реквеста

            ObjectNode objectNode = mapper.readValue(exchange.getRequestBody(), ObjectNode.class);

            Account account = new Account(Utils.generateAccountNumber());
            long userId = objectNode.get("contr").asLong();
            try {
                accountService.addByUserId(userId);
                SendMyResponses.sendMyResponse(exchange, "Success", 200);
            } catch (MyGlobalException myGlobalException) {

                String info = mapper.writeValueAsString(new MyInfoResponse(myGlobalException.getMessage()));
                SendMyResponses.sendMyResponse(exchange, info, 400);

            }
        }
        else if ("POST".equals(exchange.getRequestMethod())){


            ObjectNode objectNode = mapper.readValue(exchange.getRequestBody(), ObjectNode.class);


            if (objectNode.get("userId") != null && objectNode.get("contractorId") == null){
                long userId = objectNode.get("userId").asLong();
                try {
                    accountService.addByUserId(userId);
                    SendMyResponses.sendMyResponse(exchange, "Success", 200);
                } catch (SQLException sqlException) {

                    String info = mapper.writeValueAsString(new MyInfoResponse(sqlException.getMessage()));
                    SendMyResponses.sendMyResponse(exchange, info, 400);

                }
            }
            else if (objectNode.get("contractorId") != null && objectNode.get("userId") == null){
                long contractorId = objectNode.get("contractorId").asLong();
                try {
                    accountService.addByContractorId(contractorId);
                    SendMyResponses.sendMyResponse(exchange, "Success", 200);
                } catch (SQLException sqlException) {

                    String info = mapper.writeValueAsString(new MyInfoResponse(sqlException.getMessage()));
                    SendMyResponses.sendMyResponse(exchange, info, 400);

                }

            }
            else exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed





        }
        else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();

    }
}
