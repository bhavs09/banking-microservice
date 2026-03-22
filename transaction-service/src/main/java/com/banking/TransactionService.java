package com.banking;

import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class TransactionService {
    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8082), 0);

        // Endpoint 1: GET /accounts
        server.createContext("/transactions", exchange -> {
            String response = "{\"transactions\": [{\"id\": 1, \"from\": \"Bhavana\", \"to\": \"Rahul\", \"amount\": 5000}, {\"id\": 2, \"from\": \"Rahul\", \"to\": \"Bhavana\", \"amount\": 2000}]}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        // Endpoint 2: Health check for Kubernetes
        server.createContext("/health", exchange -> {
            String response = "{\"status\": \"UP\", \"service\": \"transaction-service\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        server.start();
        System.out.println("transaction service started on port 8082");
    }
}