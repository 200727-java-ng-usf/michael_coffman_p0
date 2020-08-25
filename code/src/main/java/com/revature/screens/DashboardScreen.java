package com.revature.screens;

import com.revature.services.UserService;

import java.io.IOException;
import java.sql.SQLException;

import static com.revature.AppDriver.app;

public class DashboardScreen extends Screens{

    private UserService userService;

    public DashboardScreen(UserService userService) {
        super("DashboardScreen", "/dashboard");
        this.userService = userService;
    }

    @Override
    public void render() {

        // Welcome message for the user who logs in successfully
        System.out.println("Welcome to your dashboard " + app.getCurrentUser().getFirstName());
        System.out.println("+---------------------------------------------------+\n");

        // String for determining which screen the user would like to go to next
        String selection;

        // We make a WHILE loop here in case the user provides invalid selections
        // It will continuously loop the dashboard till a valid selection is chosen.

        while (app.validSession()) {

            System.out.println("1) Check Balance");
            System.out.println("2) Withdrawal");
            System.out.println("3) Deposit");
            System.out.println("4) Add Account");
            System.out.println("5) Log Out\n");

            try {
                System.out.print("What would you like to do? ");
                selection = app.getConsole().readLine();

                switch (selection) {
                    case "1":

                        try {
                            System.out.println("\n");
                            userService.getBalance();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        break;

                    case "2":
                        System.out.println("Withdrawal needs implementing");
                        break;
                    case "3":
                        app.getRoute().navigate("/deposit");
                        break;
                    case "4":
                        System.out.println("You are about to add a new account to " + app.getCurrentUser().getUsername() + "'s profile.");
                        System.out.println("Please enter the name you would like to give this account.");
                        System.out.print("-> ");
                        String name = app.getConsole().readLine();
                        userService.addUserAccount(name);
                        break;
                    case "5":
                        app.invalidateCurrentSession();
                        break;
                    default:
                        System.out.println("Invalid Selection:");

                }





            } catch (IOException ioe) {
                ioe.printStackTrace();
            }


        }

    }
}
