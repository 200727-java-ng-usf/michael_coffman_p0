package com.revature.screens;

import com.revature.services.UserService;

public class DepositScreen extends Screens{

    private UserService userService;

    public DepositScreen(UserService userService) {
        super("DepositScreen", "/deposit");
        this.userService = userService;
    }

    @Override
    public void render() {

    }
}
