package org.example;

public class Data {

    public static final String client_id ="607396c3701b4c6197129bcabb2205b2";
    public static final String redirect_URL ="http://localhost:8080";

    public static final String client_Secret ="3f500d7f11914114a2cceb0abb73a5e7";

    public static String auth_server_path ="https://accounts.spotify.com";
    public static String auth_link = auth_server_path + "/authorize?client_id=" + client_id +
            "&redirect_uri=" + redirect_URL+
            "&response_type=code";
    public static String playListAPI = "https://api.spotify.com";
    public static int entriesPerPage = 5;
    public static String newReleasesEndPoint = "/v1/browse/new-releases?country=US&offset=%d&limit=%d";
    public static String featuredPlaylistEndpoint = "/v1/browse/featured-playlists?country=US&offset=%d&limit=%d";
    public static String getCategoriesEndpoint = "/v1/browse/categories?country=US&offset=%d&limit=%d";
    public static String getPlaylists ="/v1/browse/categories/%s/playlists";

}
