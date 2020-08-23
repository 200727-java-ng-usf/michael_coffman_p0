package com.revature.services;

import com.revature.dataAccess.DAO;
import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.AppUser;

public class UserService {

    private DAO dataObject;

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
        // the object returned by our DAO when a user attempts to login.
        AppUser authorizedUser = dataObject.findUserByLogin(username, password)
                                           .orElseThrow(AuthenticationException::new);

    }


}
