package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Controller {

    private static String authCode ="";
    private static String accessToken = "";
    public static void getAccessCode() throws IOException, InterruptedException {
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8080), 0);
        server.start();
        System.out.println("use this link to request the access code:");
        System.out.println(Data.auth_link);
        server.createContext("/",
                new HttpHandler() {
                    @Override
                    public void handle(HttpExchange exchange) throws IOException {
                        String query = exchange.getRequestURI().getQuery();
                        String result = "";
                        if(query != null && query.contains("code")){
                            authCode = query.substring(5);
                            result = "Got the code. Return back to your program.";
                        }else {
                            result = "Authorization code not found. Try again.";
                        }
                        exchange.sendResponseHeaders(200, result.length());
                        exchange.getResponseBody().write(result.getBytes());
                        System.out.println(result);
                    }
                }
        );
        while(authCode.equals("")){
            Thread.sleep(10);
        }
        server.stop(10);
    }

    public static void getAccessToken() throws IOException, InterruptedException {
        System.out.println("making http request for access token...");
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(
                        "&grant_type=" + "authorization_code"
                                + "&code=" + authCode
                                + "&redirect_uri=" + Data.redirect_URL
                                + "&client_id=" + Data.client_id
                                + "&client_secret=" + Data.client_Secret))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(Data.access_toke_from_url))
                .build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> responseWithToken = client.send(request, HttpResponse.BodyHandlers.ofString());
        accessToken = JsonParser.parseString(responseWithToken.body()).getAsJsonObject().get("access_token").getAsString();
        //System.out.println(accessToken);

    }

    public static void requestAuthCode(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Data.redirect_URL))
                .GET()
                .build();
    }}
