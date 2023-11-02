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
        input = input.split(" ")[0];
        while(!input.equals("exit")){
            if(input.equals("auth")){
                Controller.getAccessCode();;
                Controller.getAccessToken();
                System.out.println("---SUCCESS---");
                //scanner.nextLine();
                authenticator.setAuthenticated(true);
            }
            if(authenticator.isAuthenticated()) {
                switch (input) {
                    case "new" -> {
                        Commands new_Command = new New();
                        System.out.println(new_Command.printInformation());
                        input = scanner.nextLine();
                    }
                    case "featured" -> {
                        Commands featured_Command = new Featured();
                        System.out.println(featured_Command.printInformation());
                        input = scanner.nextLine();
                    }
                    case "categories" -> {
                        Commands categories_Command = new Categories();
                        System.out.println(categories_Command.printInformation());
                        input = scanner.nextLine();
                    }
                    case "playlists" -> {
                        Commands playlist_Command = new Playlist();
                        System.out.println(playlist_Command.printInformation());
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
