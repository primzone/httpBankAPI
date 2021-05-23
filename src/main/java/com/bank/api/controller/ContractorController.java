package com.bank.api.controller;

import com.bank.api.entity.Account;
import com.bank.api.entity.Contractor;
import com.bank.api.exceptions.MyGlobalException;
import com.bank.api.responses.MyInfoResponse;
import com.bank.api.responses.SendMyResponses;
import com.bank.api.service.AccountService;
import com.bank.api.service.AccountServiceImpl;
import com.bank.api.service.ContractorService;
import com.bank.api.service.ContractorServiceImpl;
import com.bank.api.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ContractorController implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ContractorService contractorService = new ContractorServiceImpl();

        if ("GET".equals(exchange.getRequestMethod())) {
           //список всех контрагентов юзера
            Map<String, String> queryMap = Utils.queryToMap(exchange.getRequestURI().getRawQuery());
        //mapper.writeValueAsString(mapper.createObjectNode().put("Success", true));

            try {
                List<Contractor> contractorList = contractorService.findAllByUserId(Long.parseLong(queryMap.get("id")));
                String s = mapper.writeValueAsString(contractorList);
                SendMyResponses.sendMyResponse(exchange,
                        mapper.writeValueAsString(contractorList),
                        200);

            }catch (SQLException sqlException){
                String info = mapper.writeValueAsString(new MyInfoResponse(sqlException.getMessage()));
                SendMyResponses.sendMyResponse(exchange, info, 400);
            }

        }
        else if ("POST".equals(exchange.getRequestMethod())){
            //добавить контрагента юзеру
            ObjectNode objectNode = mapper.readValue(exchange.getRequestBody(), ObjectNode.class);

            try{
                contractorService.addByUserId(objectNode.get("userId").asLong(),
                        objectNode.get("name").asText(),
                        objectNode.get("corporation").asBoolean());

                SendMyResponses.sendMyResponse(exchange, "Success", 200);
            }
            catch(SQLException sqlException){
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
