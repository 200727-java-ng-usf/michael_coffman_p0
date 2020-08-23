package com.revature.screens;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.services.UserService;

// We need to import the static AppState object instantiated in
// the AppDriver so we can use AppStates methods.
import static com.revature.AppDriver.*;

public class LoginScreen extends Screens{

    // LoginScreen needs to utilize the UserService class
    private UserService userService;

    // When the LoginScreen class is created, it needs the UserService class to handle authentication
    public LoginScreen(UserService userService) {
        super("LoginScreen", "/login");
        this.userService = userService;
    }

    @Override
    public void render() {

        String username, password;

        try {

            // Displays the lines the user will enter to login to their account
            System.out.println("Enter your username and password.");
            System.out.print("Username: ");
            username = app.getConsole().readLine();
            System.out.print("Password: ");
            password = app.getConsole().readLine();

            // Takes in the username and password entered, and executes the
            // authentication method to make sure they're an authorized user.
            userService.authentication(username, password);

            // If the user is authorized, a currentUser will be instantiated
            // and validSession() will return true, allowing access to
            // currentUser's dashboard
            if (app.validSession()) {
                app.getRoute().navigate("/dashboard");
            }


        } catch (InvalidRequestException | AuthenticationException e) {
            System.err.println("Incorrect username or password, please try again.");
        } catch (Exception e) {
            e.printStackTrace();
            // If a user somehow reaches this statement, shut it all down.
            app.turnAppOff(false);
        }
    }
}
