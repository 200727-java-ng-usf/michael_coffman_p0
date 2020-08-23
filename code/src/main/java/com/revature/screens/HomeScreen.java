package com.revature.screens;


import java.io.IOException;

import static com.revature.AppDriver.app;


public class HomeScreen extends Screens{

    public HomeScreen() {
        super("HomeScreen", "/homescreen");
    }


    @Override
    public void render() {

        System.out.println("Welcome to Revature Credit Union!");
        System.out.println("+-----------------------------------+");
        System.out.println("1) Login to existing account.");
        System.out.println("2) Register for a new account.");
        System.out.println("3) Exit the application.");

        try {
            System.out.print("-> ");
            String selection = app.getConsole().readLine();

            switch (selection) {
                case "1":
                    app.getRoute().navigate("/login");
                    break;
                case "2":
                    app.getRoute().navigate("/register");
                    break;
                case "3":
                    app.turnAppOff(false);
                    break;
                default:
                    System.out.println("Invalid Selection.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
