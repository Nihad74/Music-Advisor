package org.example;

import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Application application = new Application();
        if(args.length >= 2 && Objects.equals(args[0], "-access")){
            Data.auth_server_path = args[1] ;
        }
        application.startApplication(null);
    }
}