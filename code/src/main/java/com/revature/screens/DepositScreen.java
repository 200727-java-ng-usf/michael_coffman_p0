package com.revature.screens;

import com.revature.services.UserService;

import java.io.IOException;
import java.util.ArrayList;

import static com.revature.AppDriver.app;

public class DepositScreen extends Screens{

    private UserService userService;

    public DepositScreen(UserService userService) {
        super("DepositScreen", "/deposit");
        this.userService = userService;
    }

    @Override
    public void render() {

        int accoutChoice;
        double ammount;


        System.out.println("Which account would you like to deposit into? ");

        for (int i = 1; i <= userService.getAccountNames().size(); i++) {

            System.out.println(i + ") " + userService.getAccountNames().get(i));




        }

        try {

            app.getConsole().readLine();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
