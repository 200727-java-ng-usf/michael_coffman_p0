package com.revature.util;

//importing other package classes
import com.revature.dataAccess.DAO;
import com.revature.models.AppUser;
import com.revature.screens.*;
import com.revature.services.UserService;

//importing built-in utils
import java.io.BufferedReader;
import java.io.InputStreamReader;


/**
 * Used for instantiation of all other classes that need to instantiate upon startup. It
 * also provides useful methods for handling the current state of the program.
 */
public class AppState {

    private BufferedReader console;
    private Router route;
    private boolean isRunning;
    private AppUser currentUser;


    /*
        This is the constructor for the AppState class.
        When constructed, it will instantiate all the other
        necessary classes needed for the app to run.

        This will be useful so we only instantiate these classes
        once on startup, and not each time the class itself gets
        referenced / used.
     */

    public AppState() {

        // These 2 statements essentially turn the app to 'ON' mode
        // and instantiate the BufferedReader, which will be responsible
        // for handling AppUser input.
        isRunning = true;
        console = new BufferedReader(new InputStreamReader(System.in));


        // We declare final on these objects to prevent anything in them from being
        // changed after instantiation.
        final DAO dataObject = new DAO();
        final UserService userService = new UserService(dataObject);

        // Instantiating the new Router object which will be responsible for
        // navigation through our screens; and instantiating all the screens to
        // the Set<Screens> for the Router to navigate through
        route = new Router();
        route.addToScreens(new HomeScreen())
             .addToScreens(new RegistrationScreen(userService))
             .addToScreens(new LoginScreen(userService))
             .addToScreens(new DashboardScreen(userService))
             .addToScreens(new WithdrawalScreen(userService))
             .addToScreens(new DepositScreen(userService));




    }


    /**
     * Returns a BufferedReader object named console; used for user input
     * @return BufferedReader console
     */
    public BufferedReader getConsole() {
        return console;
    }

    /**
     * For telling a class which AppUser object is currently using the screen
     * @return AppUser currentUser
     */
    public AppUser getCurrentUser() {
        return currentUser;
    }

    /**
     * Gives AppUser the appropriate field values
     * @param currentUser currentUser comes from the DAO
     */
    public void setCurrentUser(AppUser currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * A boolean check that needs to be true for the app to run; Used
     * for exiting the app safely when set to false.
     * @return Boolean isRunning
     */
    public boolean running() {
        return isRunning;
    }

    /**
     * Used to turn the boolean isRunning off when a user chooses to close
     * the app.
     * @param running Set to TRUE/FALSE in other methods when you want
     *                to shut the app down.
     */
    public void turnAppOff(boolean running) {
        this.isRunning = running;
    }

    /**
     *  Used to clear the currentUser of its fields. This will be closed normally when our
     *  console application closes, but for persisting apps, this becomes more necessary.
     */
    public void invalidateCurrentSession() {
        currentUser = null;
    }

    /**
     * Checks to see that a currentUser has been processed. Helps ensure invalid users
     * don't have access to other parts of our program.
     * @return boolean validSession
     */
    public boolean validSession() {
        return (this.currentUser != null);
    }

    /**
     * Tells the Router object which screen the user has chosen
     * @return Router route
     */
    public Router getRoute() {
        return route;
    }

}
