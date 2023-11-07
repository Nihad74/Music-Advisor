package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class View {
    public static void validateGetNewReleasesResponse(HttpResponse<String> response) {
        //System.out.println(response.body());
        JsonObject responseObject = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject albumsObject = responseObject.getAsJsonObject("albums");
        JsonArray items = albumsObject.getAsJsonArray("items");
        for (JsonElement item : items) {
            JsonObject itemObject = item.getAsJsonObject();
            String albumName = itemObject.get("name").getAsString();
            String url =itemObject.getAsJsonObject("external_urls")
                    .get("spotify").getAsString();
            JsonArray artists = itemObject.getAsJsonArray("artists");
            List<String> artistList = new ArrayList<>();
            for (JsonElement artist : artists) {
                JsonObject artistObject = artist.getAsJsonObject();
                artistList.add(artistObject.get("name").getAsString());
            }
            System.out.println(albumName + "\n" + artistList + "\n" + url + "\n");
        }
    }

    public static void validateFeaturedReleases(HttpResponse<String> response) {
        JsonObject responseObject = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject playlistObject = responseObject.getAsJsonObject("playlists");
        JsonArray items = playlistObject.getAsJsonArray("items");
        for (JsonElement item : items) {
            String playlist_name = "";
            String url = "";
            JsonObject itemObject = item.getAsJsonObject();
            url = itemObject.getAsJsonObject("external_urls").get("spotify").getAsString();
            playlist_name = itemObject.get("name").getAsString();
            System.out.println(playlist_name + "\n" + url + "\n");
        }
    }

    public static void validateCategories(HttpResponse<String> response) {
        JsonObject responseObject = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonObject categories = responseObject.getAsJsonObject("categories");
        JsonArray items = categories.getAsJsonArray("items");
        for (JsonElement item : items) {
            String name = "";
            JsonObject itemObject = item.getAsJsonObject();
            name = itemObject.get("name").getAsString();
            System.out.println(name);
        }
    }

    public static void validatePlaylists(HttpResponse<String> response) {
        JsonObject responseObject = JsonParser.parseString(response.body()).getAsJsonObject();
        if(responseObject.has("error")){
            System.out.println("Specified id doesn't exist");
            return;
        }
        JsonObject playlistObject = responseObject.getAsJsonObject("playlists");
        JsonArray items = playlistObject.getAsJsonArray("items");
        for (JsonElement item : items) {
            String name = "";
            String url = "";
            JsonObject itemObject = item.getAsJsonObject();
            JsonObject external_url = itemObject.getAsJsonObject("external_urls");
            url = external_url.get("spotify").getAsString();
            name = itemObject.get("name").getAsString();
            System.out.println(name + "\n" + url + "\n");
        }
    }
}
