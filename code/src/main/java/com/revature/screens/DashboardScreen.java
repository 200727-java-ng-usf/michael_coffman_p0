package com.revature.screens;

import static com.revature.AppDriver.app;

public class DashboardScreen extends Screens{

    public DashboardScreen() {
        super("DashboardScreen", "/dashboard");
    }

    @Override
    public void render() {

        // Welcome message for the user who logs in successfully
        System.out.println("Welcome to your dashboard " + app.getCurrentUser().getFirstName());

        // String for determining which screen the user would like to go to next
        String selection;

        // We make a WHILE loop here in case the user provides invalid selections
        // It will continuously loop the dashboard till a valid selection is chosen.

        while (app.validSession()) {

            System.out.println("1) Check Balance");
            System.out.println("2) Withdrawal");
            System.out.println("3) Deposit");
            System.out.println("4) Add Account");
            System.out.println("8) Log Out");


        }

    }
}
