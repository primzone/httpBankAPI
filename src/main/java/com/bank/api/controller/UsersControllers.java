package com.bank.api.controller;

import com.bank.api.entity.User;
import com.bank.api.responses.MyInfoResponse;
import com.bank.api.responses.SendMyResponses;
import com.bank.api.service.UserService;
import com.bank.api.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.DataInput;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

public class UsersControllers implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserService userService = new UserServiceImpl();
        if ("GET".equals(exchange.getRequestMethod())) {


            List<User> user = userService.findAll();
            String s = mapper.writeValueAsString(user);
            SendMyResponses.sendMyResponse(exchange, s, 200);

        }
        else if ("POST".equals(exchange.getRequestMethod())){

            exchange.getResponseBody();
            User user = mapper.readValue(exchange.getRequestBody(), User.class);
            userService.save(user);

            SendMyResponses.sendMyResponse(exchange, MyInfoResponse.getMyInfoResponseJSON("Success"), 200);

        }
        else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }
}
