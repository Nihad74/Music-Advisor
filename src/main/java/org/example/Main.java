package org.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Application application = new Application();
        Map<String, String> map = new HashMap<>();
        for(int i = 0; i <args.length; i+=2){
            map.put(args[i], args[i+1]);
        }
        Data.auth_server_path = map.get("-access") == null ?  "https://accounts.spotify.com" : map.get("-access");
        application.startApplication(null);
    }
}