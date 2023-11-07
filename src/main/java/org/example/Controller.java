package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

    public static void getFeaturedPlaylists() throws IOException, InterruptedException {
        HttpRequest getFeaturedPlaylists = HttpRequest.newBuilder()
                .header("Authorization", "Authorization: Bearer "+ accessToken)
                .uri(URI.create(Data.featuredPlaylist + "?country=US&offset=0&limit=5"))
                .GET()
                .build();
        HttpClient client = HttpClient.newBuilder()
                .build();
        HttpResponse<String> response = client.send(getFeaturedPlaylists, HttpResponse.BodyHandlers.ofString());
        validateFeaturedReleases(response);
    }

    public static void validateFeaturedReleases(HttpResponse<String> response){
        JsonObject responseObject = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject playlistObject = responseObject.getAsJsonObject("playlists");
        JsonArray items = playlistObject.getAsJsonArray("items");
        for(JsonElement item : items){
            String playlist_name ="";
            String url ="";
            JsonObject itemObject = item.getAsJsonObject();
            url = itemObject.getAsJsonObject("external_urls").get("spotify").getAsString();
            playlist_name = itemObject.get("name").getAsString();
            System.out.println(playlist_name +"\n" + url + "\n");
        }
    }

    public static void validateGetNewReleasesResponse(HttpResponse<String> response){
        JsonObject responseObject = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject albumsObject = responseObject.getAsJsonObject("albums");
        JsonArray items = albumsObject.getAsJsonArray("items");
        for(JsonElement item : items){
            String albumName = "";
            StringBuilder bandName = new StringBuilder();
            bandName.append("[");
            String url = "";
            JsonObject itemObject = item.getAsJsonObject();
            JsonArray artists = itemObject.getAsJsonArray("artists");
            for(JsonElement artist: artists){
                JsonObject artistObject = artist.getAsJsonObject();
                url = artistObject.getAsJsonObject("external_urls")
                        .get("spotify").getAsString();
                bandName.append( artist.getAsJsonObject().get("name").getAsString());
                bandName.append("]");

            }
            albumName = itemObject.get("name").getAsString();
            System.out.println(albumName+ "\n" + bandName + "\n" + url + "\n");
        }
    }

    public static void getCategories() throws IOException, InterruptedException {
        HttpRequest getFeaturedPlaylists = HttpRequest.newBuilder()
                .header("Authorization", "Authorization: Bearer "+ accessToken)
                .uri(URI.create(Data.getCategories + "?country=US&offset=0&limit=10"))
                .GET()
                .build();
        HttpClient client = HttpClient.newBuilder()
                .build();
        HttpResponse<String> response = client.send(getFeaturedPlaylists, HttpResponse.BodyHandlers.ofString());
        validateCategories(response);
    }

    private static void validateCategories(HttpResponse<String> response) {
        JsonObject responseObject = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject categories = responseObject.getAsJsonObject("categories");
        JsonArray items = categories.getAsJsonArray("items");
        for(JsonElement item :items){
            String name ="";
            JsonObject itemObject = item.getAsJsonObject();
            name = itemObject.get("name").getAsString();
            System.out.println(name);
        }
    }

   /* public static void getPlaylists(String input) {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Authorization: Bearer " + accessToken)

    }*/
}
