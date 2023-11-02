package org.example;

public class Data {
    public static final String client_id ="607396c3701b4c6197129bcabb2205b2";
    public static final String redirect_URL ="http://localhost:8080/";

    public static final String client_Secret ="3f500d7f11914114a2cceb0abb73a5e7";

    public static String auth_server_path ="https://accounts.spotify.com";
    public static String auth_link = auth_server_path + "/authorize?client_id=" + client_id +
            "&response_type=code" +
            "&redirect_uri=" + redirect_URL;
    public static String access_toke_from_url = "https://accounts.spotify.com/api/token";
}
