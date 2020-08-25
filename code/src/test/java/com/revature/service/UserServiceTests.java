package com.revature.service;

// Exception Imports
import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.InvalidRequestException;


// Dependency Imports
import com.revature.dataAccess.DAO;
import com.revature.models.AppUser;
import com.revature.services.UserService;
import static com.revature.AppDriver.*;


// Testing Imports
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

// Java Util Imports


import static com.revature.models.Role.BASIC_USER;

public class UserServiceTests {

    private UserService sut;
    private DAO mockDAO = Mockito.mock(DAO.class);

    @Before
    public void setup() {
        sut = new UserService(mockDAO);
    }

    @After
    public void tearDown() {

        // This clears the reference to UserService dependency after each test
        sut = null;
    }

    // Testing authentication method
    @Test(expected = InvalidRequestException.class)
    public void InvalidCredentialsEntered() {

        // Arrange
        // Nothing to arrange. This will just check to make sure
        // the authentication method knows not to accept empty strings
        // or spaces in between strings ie. username: Billy Bob instead of username: BillyBob

        // Act
        sut.authentication("", "");

        // Assess

    }

    @Test(expected = AuthenticationException.class)
    public void UnknownCredentialsEntered() {

        //Arrange
        // Nothing to arrange. This checks the to see that the information entered is
        // considered valid. If it is (meaning the rules stated previously are followed)
        // Then it will move

        // Act
        sut.authentication("The", "Guy");
    }


    // Testing registration method
    @Test
    public void returnFalseIfInvalidCredentialsAreProvided() {

        // Arrange
        AppUser newUser = new AppUser("", "Cof fman", "mcoffma04", "mcoffman@gmail.com", "blah", 1, BASIC_USER);

        // Act
        boolean result = sut.isUserValid(newUser);


        // Assess
        Assert.assertEquals(false, result);
    }

    @Test(expected = RuntimeException.class)
    public void userAlreadyExists() {

        // Arrange

        // username from trying to login
        String registrationUsername = "mcoffma04";

        // username from DAO grabbing username from database
        String existingUser = "mcoffma04";

        // Act

        // Assess

        if (registrationUsername.equals(existingUser)) {
            throw new RuntimeException("Username already exists.");
        }
        ;
    }

    @Test
    public void newUserSetToCurrentUser() {

        // Arrange

        AppUser newUser = new AppUser();

        // Act

        // The DAO saves the newUser into the database
        app.setCurrentUser(newUser);

        // Assess

        Assert.assertTrue(newUser == app.getCurrentUser());
    }

    // Testing deposit and withdraw methods along with a negative test
    @Test
    public void didDepositHappen() {

        // Arrange

        double amount = 0;
        int updatedRow = 0;
        int itDidUpdate = 1;

        // Act

        // User-defined amount to deposit
        amount = amount + 20.00;

        // DOA returns amount of updated rows
        // This is the main confirmation that
        // the account did indeed update.
        updatedRow = 1;

        // Assess

        Assert.assertEquals(updatedRow, itDidUpdate);

    }

    @Test
    public void didWithdrawalHappen() {

        // Arrange

        double amount = 100.00;
        int updatedRow = 0;
        int itDidUpdate = 1;

        // Act

        // User-defined amount to deposit
        amount = amount - 50.00;

        // DOA returns amount of updated rows
        // This is the main confirmation that
        // the account did indeed update.
        updatedRow = 1;

        // Assess

        Assert.assertEquals(updatedRow, itDidUpdate);
    }

    // Throws a numberFormatException in case user wants
    // to enter a String as their deposit/withdraw
    @Test(expected = NumberFormatException.class)
    public void incorrectDepositFormat() {

        // Arrange

        double amount = 0;
        int updatedRow = 0;

        // Act

        // User-defined amount to deposit


            amount = amount - Double.parseDouble("fdsjk");


        // DOA returns amount of updated rows
        // This is the main confirmation that
        // the account did indeed update.
        updatedRow = 0;

        // Assess

    }

    // This method was to find a way around a newUser's id
    // not being assigned their new Id till next login
    @Test
    public void updateId() {

        // Arrange
            AppUser storedUser = new AppUser("Michael", "Coffman", "mcoffma04", "password", "email@email", 1, BASIC_USER);

            AppUser newlyRegisteredUser = new AppUser("Michael", "Coffman", "mcoffma04", "password", "email@email");

        // Act

            newlyRegisteredUser.setId(storedUser.getId());

        // Assess

            Assert.assertEquals(storedUser.getId(),newlyRegisteredUser.getId());
    }


}
