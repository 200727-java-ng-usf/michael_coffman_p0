package com.revature.screens;

import com.revature.services.UserService;

import java.io.IOException;

import static com.revature.AppDriver.app;

/**
 * Responsible for displaying a screen that holds the printouts and methods
 * for users to withdraw money from their accounts
 */
public class WithdrawalScreen extends Screens {

    private UserService userService;

    public WithdrawalScreen(UserService userService) {
        super("WithdrawalScreen", "/withdraw");
        this.userService = userService;
    }


    @Override
    public void render() {

        System.out.println("This is where you can withdraw your money.");
        System.out.println("Please make a decision below. \n");
        System.out.println("+-------------------------------------------+");

        String accountChoice;
        double amount;
        String selection;
        int updatedRows = 0;

        while (app.validSession()) {

            System.out.println("1) Return to dashboard");
            System.out.println("2) Withdraw from an account.");

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

                        // We are going to print out all of the accounts from the user's profile
                        // for them to see what their accounts are named.
                        for (int i = 0; i < userService.getAccountNames().size(); i++) {

                            System.out.println(i + 1 + ") " + userService.getAccountNames().get(i));
                        }

                        try {
                            System.out.println("Enter the name of the account.");
                            System.out.print("> ");
                            accountChoice = app.getConsole().readLine();

                            System.out.println("+---------------------------------+");

                            System.out.print("Amount: ");
                            amount = Double.parseDouble(app.getConsole().readLine());

                            updatedRows = userService.withdraw(accountChoice, amount);

                            if (updatedRows == 0) {
                                System.out.println("You are trying to overdraft or you've entered an invalid account name.");
                            }


                        } catch (NumberFormatException nfe) {
                            System.err.println("Invalid input. Enter a double");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    default:
                        System.err.println("Invalid selection.");
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }















        }
    }
}
