package org.example;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Application {
    private Scanner scanner;
    private Authenticator authenticator;
    private String input="";
    private String input2 = "";
    private int pageNumber = 1;
    private int offset = 0;

    public Application(){
        scanner = new Scanner(System.in);
        authenticator = new Authenticator();
    }
    public void startApplication(String args) throws IOException, InterruptedException {
        readInput(" ");
        while(!input.equals("exit")){
            if(input.equals("auth")){
                Controller.getAccessCode();;
                Controller.getAccessToken();
                System.out.println("---SUCCESS---");
                authenticator.setAuthenticated(true);
                readInput(" ");
            }
            if(authenticator.isAuthenticated()) {
                switch (input) {
                    case "new" -> {
                        HttpResponse <String> response = Controller.getRequest(Data.newReleasesEndPoint,offset);
                        View.validateGetNewReleasesResponse(response);
                        readInput("new");
                    }
                    case "featured" -> {

                        HttpResponse<String> response = Controller.getRequest(Data.featuredPlaylistEndpoint, offset);
                        View.validateFeaturedReleases(response);
                        readInput("featured");
                    }
                    case "categories" -> {
                        HttpResponse<String> response = Controller.getRequest(Data.getCategoriesEndpoint, offset);
                        View.validateCategories(response);
                        readInput("categories");
                    }
                    case "playlists" -> {
                        HttpResponse <String> response =Controller.getPlaylists(Data.getCategoriesEndpoint, offset, input2);
                        if(response != null) {
                            View.validatePlaylists(response);
                        }
                        readInput("playlists "+ input2);
                    }
                    case "exit" ->{

                    }
                    default -> {
                        System.out.println("wrong input");
                        readInput(" ");
                    }
                }
            }else{
                System.out.println("Please, provide access for application.");
                readInput(" ");
            }
        }
        System.out.println("---GOODBYE!---");
    }

    public void readInput(String mode){
        input = scanner.nextLine();
        if("prev".equals(input)){
            if(pageNumber <= 1){
                System.out.println("No more pages.");
                readInput(mode);
            }else {
                System.out.printf("---PAGE %d OF 5---\n", --pageNumber);
                offset -= Data.entriesPerPage;
                input = mode;
            }
        }else if ("next".equals(input)){
            if(pageNumber >= 5){
                System.out.println("No more pages.");
                readInput(mode);
            }else {
                System.out.printf("---PAGE %d OF 5---\n", ++pageNumber);
                offset += Data.entriesPerPage;
                input = mode;
            }
        }
        if(input != mode){
            pageNumber = 1;
            offset = 0;
        }

        String[] inputArray = input.split(" ");
        input = input.split(" ")[0];
        if(inputArray.length > 1) {
            input2 = "";
            for(int i = 1; i < inputArray.length-1; i++ ){
                input2 += inputArray[i] + " ";
            }
            input2 += inputArray[inputArray.length-1];
            System.out.printf("---PAGE %d OF 5---\n", pageNumber);
        }
    }
}
