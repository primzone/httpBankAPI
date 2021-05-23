package com.bank.api.responses;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class SendMyResponses {
    static public void sendMyResponse(HttpExchange exchange, String s, int responseCode){

        try {
            exchange.sendResponseHeaders(responseCode, s.length());
            exchange.getResponseHeaders().set("Content-type", "application/json");
            OutputStream output = exchange.getResponseBody();
            output.write(s.getBytes());
            output.flush();
            exchange.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
