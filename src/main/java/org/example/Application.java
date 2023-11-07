package org.example;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Application {
    private Scanner scanner;
    private Authenticator authenticator;
    private String input="";
    private String input2 = "";

    public Application(){
        scanner = new Scanner(System.in);
        authenticator = new Authenticator();
    }
    public void startApplication(String args) throws IOException, InterruptedException {
        readInput();
        while(!input.equals("exit")){
            if(input.equals("auth")){
                Controller.getAccessCode();;
                Controller.getAccessToken();
                System.out.println("---SUCCESS---");
                authenticator.setAuthenticated(true);
                readInput();
            }
            if(authenticator.isAuthenticated()) {
                switch (input) {
                    case "new" -> {
                        HttpResponse<String> response = Controller.getRequest(Data.newReleasesEndPoint);
                        View.validateGetNewReleasesResponse(response);
                        readInput();
                    }
                    case "featured" -> {
                        HttpResponse<String> response = Controller.getRequest(Data.featuredPlaylistEndpoint);
                        View.validateFeaturedReleases(response);
                        readInput();
                    }
                    case "categories" -> {
                        HttpResponse<String> response = Controller.getRequest(Data.getCategoriesEndpoint);
                        View.validateCategories(response);
                        readInput();
                    }
                    case "playlists" -> {
                        HttpResponse <String> response =Controller.getPlaylists(Data.getCategoriesEndpoint, input2);
                        if(response != null) {
                            View.validatePlaylists(response);
                        }
                        readInput();
                    }
                    case "exit" ->{

                    }
                    default -> {
                        System.out.println("wrong input");
                        readInput();
                    }
                }
            }else{
                System.out.println("Please, provide access for application.");
                readInput();
            }
        }
        System.out.println("---GOODBYE!---");
    }

    public void readInput(){
        input = scanner.nextLine();
        String[] inputArray = input.split(" ");
        input = input.split(" ")[0];
        if(inputArray.length > 1) {
            input2 = "";
            for(int i = 1; i < inputArray.length-1; i++ ){
                input2 += inputArray[i] + " ";
            }
            input2 += inputArray[inputArray.length-1];
        }
    }
}
