package com.banking;

import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class AccountService {
    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);

        // Endpoint 1: GET /accounts
        server.createContext("/accounts", exchange -> {
            String response = "{\"accounts\": [{\"id\": 1, \"name\": \"Bhavana\", \"balance\": 50000}, {\"id\": 2, \"name\": \"Rahul\", \"balance\": 75000}]}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        // Endpoint 2: Health check for Kubernetes
        server.createContext("/health", exchange -> {
            String response = "{\"status\": \"UP\", \"service\": \"account-service\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });


        
        server.start();
        System.out.println("Account Service started on port 8081");
    }
}