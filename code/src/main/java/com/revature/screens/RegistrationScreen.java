package com.revature.screens;

import com.revature.services.UserService;

public class RegistrationScreen extends Screens{

    private UserService userService;

    // This constructor takes in a UserService object because it will be
    // making use of the UserService .registration() method to register
    // new users.
    public RegistrationScreen(UserService userService) {
        super("RegistrationScreen", "/register");
        this.userService = userService;
    }


    @Override
    public void render() {

    }
}
