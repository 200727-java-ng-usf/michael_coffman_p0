package com.revature.screens;

import com.revature.dataAccess.DAO;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.AppUser;
import com.revature.services.UserService;

import static com.revature.AppDriver.app;

public class RegistrationScreen extends Screens{

    private UserService userService;
    private DAO dataObject;

    // This constructor takes in a UserService object because it will be
    // making use of the UserService .registration() method to register
    // new users.
    public RegistrationScreen(UserService userService) {
        super("RegistrationScreen", "/register");
        this.userService = userService;
    }


    @Override
    public void render() {

        String firstName, lastName, email, username, password;

        try {
            System.out.println("Enter the following information to get started banking today!");
            System.out.print("First name: ");
            firstName = app.getConsole().readLine();
            System.out.print("Last name: ");
            lastName = app.getConsole().readLine();
            System.out.print("Email: ");
            email = app.getConsole().readLine();
            System.out.print("Username: ");
            username = app.getConsole().readLine();
            System.out.print("Password: ");
            password = app.getConsole().readLine();

            // This creates a new AppUser object with the credentials entered
            // and uses userService.registration() to verify no username matches
            // or invalid inputs were entered. If everything passes, the newUser is
            // saved into the database.
            AppUser newUser = new AppUser(firstName, lastName, username, password, email);
            userService.registration(newUser);


            // Once the user passes registration, it gets set as the current user, which
            // validates the current session, allowing them to be taken directly to the dashboard
            // screen after registration.
            if (app.validSession()) {
                app.getRoute().navigate("/dashboard");
            }


        } catch (InvalidRequestException e) {
            System.err.println("Invalid values entered, registration was unsuccessful.");
        } catch (Exception e) {
            e.printStackTrace();
            app.turnAppOff(false);
        }

    }
}
