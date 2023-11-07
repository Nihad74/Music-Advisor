package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Application {
    private Scanner scanner;
    private Authenticator authenticator;

    public Application(){
        scanner = new Scanner(System.in);
        authenticator = new Authenticator();
    }
    public void startApplication(String args) throws IOException, InterruptedException {
        String input = scanner.nextLine();
        while(!input.equals("exit")){
            input = input.split(" ")[0];
            String input2 = input.split(" ")[1];
            if(input.equals("auth")){
                Controller.getAccessCode();;
                Controller.getAccessToken();
                System.out.println("---SUCCESS---");
                authenticator.setAuthenticated(true);
                input = scanner.nextLine();
            }
            if(authenticator.isAuthenticated()) {
                switch (input) {
                    case "new" -> {
                        Controller.getNewReleases();
                        input = scanner.nextLine();
                    }
                    case "featured" -> {
                        Controller.getFeaturedPlaylists();
                        input = scanner.nextLine();
                    }
                    case "categories" -> {
                        Controller.getCategories();
                        input = scanner.nextLine();
                    }
                    case "playlists" -> {
                        Controller.getPlaylists(input2);
                        input = scanner.nextLine();
                    }
                    default -> {
                        System.out.println("wrong input");
                        input = scanner.nextLine();
                    }
                }
            }else{
                System.out.println("Please, provide access for application.");
                input = scanner.nextLine();
            }
        }
        System.out.println("---GOODBYE!---");
    }
}
