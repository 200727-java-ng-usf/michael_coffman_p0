package com.revature.screens;

import com.revature.services.UserService;

import java.io.IOException;

import static com.revature.AppDriver.app;

/**
 * Responsible for displaying a screen that holds the printouts and methods
 * for users to deposit money into their accounts
 */
public class DepositScreen extends Screens {

    private UserService userService;

    public DepositScreen(UserService userService) {
        super("DepositScreen", "/deposit");
        this.userService = userService;
    }

    @Override
    public void render() {

        System.out.println("This is where you can deposit your money.");
        System.out.println("Please make a decision below. \n");
        System.out.println("+-------------------------------------------+");

        String accountChoice;
        double amount;
        String selection;
        int updatedRows;

        while (app.validSession()) {


            System.out.println("1) Return to dashboard.");
            System.out.println("2) Deposit into an account.");

            try {
                System.out.println("What would you like to do?");
                System.out.print("> ");
                selection = app.getConsole().readLine();
                System.out.println("\n");

                switch (selection) {

                    case "1":

                        app.getRoute().navigate("/dashboard");
                        break;

                    case "2":

                        System.out.println("Which account would you like to deposit into? ");

                        for (int i = 0; i < userService.getAccountNames().size(); i++) {
                            System.out.println(i + 1 + ") " + userService.getAccountNames().get(i));
                        }

                        try {
                            System.out.println("Enter the name of the account.");
                            accountChoice = app.getConsole().readLine();

                            System.out.println("+---------------------------------+");

                            System.out.println("Amount: ");
                            amount = Double.parseDouble(app.getConsole().readLine());

                            updatedRows = userService.deposit(accountChoice, amount);

                            if (updatedRows == 0) {
                                System.err.println("Invalid account name.");
                            }

                        } catch (NumberFormatException nfe) {
                            System.err.println("Invalid input. Enter a double");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    default:
                        System.err.println("Invalid Selection");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

