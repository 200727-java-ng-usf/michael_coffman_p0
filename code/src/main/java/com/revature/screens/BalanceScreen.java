package com.revature.screens;

import com.revature.services.UserService;

public class BalanceScreen extends Screens{

    private UserService userService;

    public BalanceScreen(UserService userService) {
        super("BalanceScreen", "/balance");
        this.userService = userService;
    }

    @Override
    public void render() {






    }
}
