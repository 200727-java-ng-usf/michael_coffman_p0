package com.revature.screens;

import com.revature.services.UserService;

public class WithdrawalScreen extends Screens{

    private UserService userService;

    public WithdrawalScreen(UserService userService) {
        super("WithdrawalScreen", "/withdrawal");
        this.userService = userService;
    }


    @Override
    public void render() {

    }
}
