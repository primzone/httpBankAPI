package com.bank.api;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;


import com.bank.api.controller.ServerController;
import com.bank.api.utils.Utils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

class Application {

    public static void main(String[] args) throws IOException {
        Utils.createAndInitDb();
        int serverPort = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

        ServerController.start(server);
    }


}