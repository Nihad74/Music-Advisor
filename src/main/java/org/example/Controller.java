package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonParser;

public class Controller {

    private static String authCode = "";
    private static String accessToken = "";

    public static void getAccessCode() throws IOException, InterruptedException {
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8080), 0);
        server.start();
        System.out.println("use this link to request the access code:");
        System.out.println(Data.auth_link);
        server.createContext("/",
                exchange -> {
                    String query = exchange.getRequestURI().getQuery();
                    String response ;
                    if(query != null && query.contains("code")){
                        authCode = query.substring(5);
                        response="Got the code. Return back to your program.";
                    }else{
                        response = "Authorization code not found. Try again.";
                    }
                    exchange.sendResponseHeaders(200, response.length());
                    exchange.getResponseBody().write(response.getBytes());
                    exchange.getResponseBody().close();
                }
        );
        System.out.println("waiting for code...");
        while (authCode.isEmpty()) {
            makeSimpleGreet();
            Thread.sleep(10);
        }
        server.stop(10);
    }

    private static void makeSimpleGreet() throws IOException, InterruptedException {
        HttpClient.newBuilder().build().send(
                HttpRequest.newBuilder()
                        .uri(URI.create(Data.redirect_URL))
                        .GET()
                        .build()
                , HttpResponse.BodyHandlers.ofString());
    }

    public static void getAccessToken() throws IOException, InterruptedException {
        System.out.println("making http request for access_token...");
        System.out.println("response:");

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(Data.auth_server_path + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=authorization_code" +
                                "&code=" + authCode +
                                "&client_id=" + Data.client_id +
                                "&client_secret=" + Data.client_Secret +
                                "&redirect_uri=" + Data.redirect_URL))
                .build();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response != null) {
                parseAccessToken(response.body());
                System.out.println(response.body());
            }

        } catch (IOException | NullPointerException | InterruptedException e) {
            System.out.println("Server error");
        }
    }
    private static void parseAccessToken(String body) throws NullPointerException {
        JsonObject object = JsonParser.parseString(body).getAsJsonObject();
        accessToken = object.get("access_token").getAsString();

    }

    public static void getNewReleases() throws IOException, InterruptedException {

        HttpRequest getReleases = HttpRequest.newBuilder()
                .header("Authorization", "Authorization: Bearer "+ accessToken)
                .uri(URI.create(Data.newReleases + "?country=US&offset=0&limit=5"))
                .GET()
                .build();
        HttpClient client = HttpClient.newBuilder()
                .build();
        HttpResponse<String> response = client.send(getReleases, HttpResponse.BodyHandlers.ofString());
        validateGetNewReleasesResponse(response);

    }

    public static void validateGetNewReleasesResponse(HttpResponse<String> response){
        JsonObject responseObject = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject albumsObject = responseObject.getAsJsonObject("albums");
        JsonArray items = albumsObject.getAsJsonArray("items");
        for (int i = 0; i < items.size() ; i++ ){

        }
    }
}
