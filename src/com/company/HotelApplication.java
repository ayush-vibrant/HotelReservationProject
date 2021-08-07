package com.company;


import java.util.Scanner;


public class HotelApplication {

    public static void main(String[] args) {
        boolean keepRunning = true;
        try (Scanner sc = new Scanner(System.in)) {
            while (keepRunning) {
                try {
                    MainMenu.display();
                    int option = Integer.parseInt(sc.nextLine());
                    keepRunning = MainMenu.runMainMenu(sc, option);
                } catch (Exception e){
                    System.out.println("Enter a number between 1 and 5! \n");
                }
            }
        } catch (Exception ex) {
            System.out.println("Error encountered - Exiting the application \n");
        }
    }
}