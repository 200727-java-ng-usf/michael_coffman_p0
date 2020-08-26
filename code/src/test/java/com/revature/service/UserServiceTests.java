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

import static com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.XmlToken.Optional;
import static org.mockito.Mockito.*;


// Java Util Imports
import java.sql.SQLException;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Set;
import static com.revature.models.Role.BASIC_USER;
import static org.mockito.Mockito.mock;

public class UserServiceTests {

    private UserService sut;
    private DAO mockDAO = mock(DAO.class);
    AppUser Drew = new AppUser("Drew", "State", "dstate", "password", "dstate@gmail.com", 1,  BASIC_USER);


    @Before
    public void setup() {

        sut = new UserService(mockDAO);
        DAO mockDAO = mock(DAO.class);
    }

    @After
    public void tearDown() {

        // This clears the reference to UserService dependency after each test
        sut = null;
        mockDAO = null;
    }

    // Testing authentication method
    @Test(expected = InvalidRequestException.class)
    public void InvalidCredentialsEntered() {

        // Arrange


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

    @Test
    public void fullAuthentication() {

        // Arrange

        AppUser expected = new AppUser("Michael", "Coffman", "mcoffma04", "password", "michaelcoffman1991@gmail.com", 1,  BASIC_USER);
        AppUser actual;


        // Act

        when(mockDAO.findUserByLogin("mcoffma04", "password"))
                .thenReturn(java.util.Optional.of(actual = expected));


        // Assess

        Assert.assertEquals(expected, actual);


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

        String accountName = "Checking";
        double amount = 10.00;
        int updatedRows = 0;


        // Act

        try {
            when(mockDAO.deposit(accountName, 1, amount))
                    .thenReturn(updatedRows = 1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Assess

        Assert.assertEquals(1, updatedRows);

    }

    @Test
    public void didWithdrawalHappen() {

        // Arrange

        String accountName = "Savings";
        double amount = 20.00;
        int updatedRows = 0;


        // Act

        try {
            when(mockDAO.withdraw(accountName, 1, amount))
                    .thenReturn(updatedRows = 1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Assess

        Assert.assertEquals(1, updatedRows);
    }

    @Test
    public void noOverdraft() {

        // Arrange

        String accountName = "Savings";
        double amount = 20.00;
        int updatedRows = 0;


        // Act

        try {
            when(mockDAO.withdraw(accountName, 1, amount))
                    // the function to prevent overdraft is on persistence-tier
                    // but it returns an int of # of rows changed. So being 0, means the account
                    // did not have money withdrawn.
                    .thenReturn(updatedRows = 0);
        } catch (SQLException throwables) {
            System.out.println("You are trying to overdraft!");
        }

        // Assess

        Assert.assertEquals(0, updatedRows);

    }

    // Throws a numberFormatException in case user wants
    // to enter a String as their deposit/withdraw
    @Test(expected = NumberFormatException.class)
    public void incorrectDepositFormat() {

        // Arrange

        String accountName = "Checking";
        double amount = Double.parseDouble("ugh");
        int updatedRows = 0;


        // Act

        try {
            when(mockDAO.withdraw(accountName, 1, amount))
                    .thenReturn(updatedRows = 1);
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }

        // Assess

    }

    @Test
    public void incorrectAccountName() {

        // Arrange

        String accountName = "Checking";
        double amount = 50.50;
        int updatedRows = 0;

        // Act

        try {
            when(mockDAO.withdraw("WrongAccountName", 1, amount))
                    .thenReturn(updatedRows = 0);
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }

        // Assess

        Assert.assertEquals(0, updatedRows);

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
