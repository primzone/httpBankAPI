package com.bank.api.controller;

import com.sun.net.httpserver.HttpServer;

import java.util.concurrent.Executor;

public class ServerController {

    public  static  HttpServer addAllContexts(HttpServer server){

        server.createContext("/api/employee/users", new UsersControllers());//просмотр и добавление нового юзера
        server.createContext("/api/employee/accounts", new AccountsController());//просмотр баланса и добавление счета
        server.createContext("/api/employee/cards", new CardsController());//подтверждение карты
        server.createContext("/api/employee/contractors/accounts", new AccountsController());// добавление счета контрагенту
        server.createContext("/api/employee/transactions", new TransactionController());// подтверждение транзакции
        server.createContext("/api/users/cards", new CardsController());// выпуск карты и
        server.createContext("/api/users/cards/refill", new CardsController());//просмотр баланса
        server.createContext("/api/users/contractors", new ContractorController());//добавление и просмотр контрагентов
        server.createContext("/api/users/cards/transfer", new TransactionController());// перевод контрагенту средств



        server.setExecutor(null); // creates a default executor
        return server;
    }

    public static void start(HttpServer server) {
        server = addAllContexts(server);
        server.start();

    }


}
