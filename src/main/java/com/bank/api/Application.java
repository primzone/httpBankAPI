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

//
//    private void executeSqlFile() {
//        try {
//            Runtime rt = Runtime.getRuntime();
//            String executeSqlCommand = "psql -U (user) -h (domain) -f (script_name) (dbName)";
//            Process pr = rt.exec();
//            int exitVal = pr.waitFor();
//            System.out.println("Exited with error code " + exitVal);
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//    }
//
//
//    private void executeSql(String sqlFilePath) {
//        final class SqlExecuter extends SQLExec {
//            public SqlExecuter() {
//                Project project = new Project();
//                project.init();
//                setProject(project);
//                setTaskType("sql");
//                setTaskName("sql");
//            }
//        }
//
//        SqlExecuter executer = new SqlExecuter();
//        executer.setSrc(new File(sqlFilePath));
//        executer.setDriver(args.getDriver());
//        executer.setPassword(args.getPwd());
//        executer.setUserid(args.getUser());
//        executer.setUrl(args.getUrl());
//        executer.execute();
//    }


}