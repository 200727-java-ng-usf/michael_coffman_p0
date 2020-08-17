package com.revature.screens;

import java.util.Scanner;

public class LoginScreen extends Screens{


    /**
     * This method override is for rendering the login screen of the application
     */
    @Override
    public void render() {

        Scanner input = new Scanner(System.in);

        String username;
        String password;

        try {
            System.out.println("Please provide your username and password.");

            System.out.print("Username: ");
            username = input.nextLine();

            System.out.print("Password: ");
            password = input.nextLine();

//          For testing input was correctly read
//          System.out.println(username + " " + password);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
