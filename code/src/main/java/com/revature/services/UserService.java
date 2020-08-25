package com.revature.services;

import com.revature.dataAccess.DAO;
import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.AppUser;
import com.revature.models.Role;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static com.revature.AppDriver.app;


public class UserService {

    private DAO dataObject;
    private int updatedRows;

    // This constructor takes in the DAO object. It's loose coupling
    // so when if the DAO is changed, it will not break the UserService object.
    // This is to prevent the "Ripple Effect"
    public UserService(DAO daoObject) {
        dataObject = daoObject;
    }

    /**
     * Verifies that the username and password the user provides does
     * have an account in our database. If this check passes, the user
     * will be granted access to their data.
     * @param username
     * @param password
     */
    public void authentication(String username, String password) {

        // This is an edge-case check to see if the user has entered either nothing at all
        // or accidentally put spaces between their entries such as username: 'use r'
        if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
            throw new InvalidRequestException("Invalid username or password, please try again.");
        }

        // This declares an AppUser object named authorizedUser. It will be set equal
        // the object returned by our DAO.mapResults() when a user attempts to login.
        AppUser authorizedUser = dataObject.findUserByLogin(username, password)
                                           .orElseThrow(AuthenticationException::new);

        app.setCurrentUser(authorizedUser);
    }

    /**
     * Registers a newUser if the credentials they've entered have passed
     * all of the checks. uses DAO.save() to save them into the database.
     * @param newUser
     */
    public void registration(AppUser newUser) {

        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid values entered!");
        }

        Optional<AppUser> existingUser = dataObject.findUserByUsername(newUser.getUsername());
        if(existingUser.isPresent()) {
            throw new RuntimeException("Username is already taken.");
        }

        // If the checks pass, the newUser will be saved into the database and set as the currentUser
        newUser.setRole(Role.BASIC_USER);
        dataObject.save(newUser);
        app.setCurrentUser(newUser);
    }

    /**
     * Adds a "checking" account by the name specified by currentUser
     * @param name
     */
    public void addUserAccount(String name) {
        dataObject.addAccount(name, dataObject.findUserById(app.getCurrentUser()));
    }

    /**
     * Retrieves the balance of all accounts associated with the user
     * @throws SQLException
     */
    public void getBalance() throws SQLException {
        dataObject.getBalances(dataObject.findUserById(app.getCurrentUser()));
    }

    /**
     * Deposits money into a user account
     * @param accountChoice
     * @param deposit
     * @return
     */
    public int deposit(String accountChoice, double deposit) {

        try {
            updatedRows = dataObject.deposit(accountChoice, app.getCurrentUser().getId(), deposit);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return updatedRows;
    }

    /**
     * Withdraws money from a user account
     * @param accountChoice
     * @param withdraw
     * @return
     */
    public int withdraw(String accountChoice, double withdraw) {

        try {
           updatedRows = dataObject.withdraw(accountChoice, app.getCurrentUser().getId(), withdraw);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return updatedRows;

    }

    /**
     * Gathers all the user's accounts to display just their names
     * @return ArrayList<String> accountNames
     */
    public ArrayList<String> getAccountNames() {
        ArrayList<String> accountNames = new ArrayList<>();
        try {
            accountNames = dataObject.getAccountNames(app.getCurrentUser().getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return accountNames;
    }

    /**
     * Assigns the currentUser their appropriate Id after registration
     * to allow smooth transition from registration to dashboard
     */
    public void getId() {
        app.getCurrentUser().setId(dataObject.findUserById(app.getCurrentUser()));
    }

    /**
     * Checks if credentials entered by user satisfy our requirements
     * @param user
     * @return boolean
     */
    public boolean isUserValid(AppUser user) {
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        if (user.getPassword() == null || user.getPassword().trim().equals("")) return false;
        if (user.getEmail() == null || user.getEmail().trim().equals("")) return false;

        return true;
    }
















}
