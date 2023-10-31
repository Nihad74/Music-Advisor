package org.example;

import java.util.Scanner;

public class Application {
    private Scanner scanner;

    public Application(){
        scanner = new Scanner(System.in);
    }
    public void startApplication(){
        String input = scanner.nextLine();
        input = input.split(" ")[0];
        while(!input.equals("exit")){
            switch (input) {
                case "new" -> {
                    System.out.println("---NEW RELEASES---\n" +
                            "Mountains [Sia, Diplo, Labrinth]\n" +
                            "Runaway [Lil Peep]\n" +
                            "The Greatest Show [Panic! At The Disco]\n" +
                            "All Out Life [Slipknot]");
                    input = scanner.nextLine();
                }
                case "featured" -> {
                    System.out.println("---FEATURED---\n" +
                            "Mellow Morning\n" +
                            "Wake Up and Smell the Coffee\n" +
                            "Monday Motivation\n" +
                            "Songs to Sing in the Shower");
                    input = scanner.nextLine();
                }
                case "categories" -> {
                    System.out.println("---CATEGORIES---\n" +
                            "Top Lists\n" +
                            "Pop\n" +
                            "Mood\n" +
                            "Latin");
                    input = scanner.nextLine();
                }
                case "playlists" -> {
                    System.out.println("---MOOD PLAYLISTS---\n" +
                            "Walk Like A Badass  \n" +
                            "Rage Beats  \n" +
                            "Arab Mood Booster  \n" +
                            "Sunday Stroll");
                    input = scanner.nextLine();
                }
                default -> {
                    System.out.println("wrong input");
                    input = scanner.nextLine();
                }
            }
        }
        System.out.println("---GOODBYE!---");
    }
}
