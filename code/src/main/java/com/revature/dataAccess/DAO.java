package com.revature.dataAccess;

import com.revature.models.Accounts;
import com.revature.models.AppUser;
import com.revature.models.Role;
import com.revature.util.DatabaseConnection;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * This class handles all the CRUD operations between the persistence
 * and application tiers.
 */
public class DAO {

    // This constructor is unecessary in this case, but I like to
    // explicitly declare incase I need to make changes to it later.
    public DAO() {
        super();
    }

    /**
     * Finds the user in the database specified by the parameters
     * username and password
     * @param username
     * @param password
     * @return AppUser object containing correct information
     */
    public Optional<AppUser> findUserByLogin(String username, String password) {

        // This is to set a blank Optional<AppUser> object. We can't set it to null
        // here because that goes against what an Optional is.
        Optional<AppUser> opUser = Optional.empty();

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {

            // This is just setting a string field to the specific query we want to execute
            // when calling findUserByLogin method.
            String query = "SELECT * FROM project0.app_users au " +
                           "JOIN project0.user_roles ur " +
                           "ON au.role_id = ur.id " +
                           "WHERE username = ? AND password = ?";

            // This block of code is just setting up which '?' will equal what.
            // Using prepared statements helps prevent malicious SQLi attacks.
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            opUser = mapResultSet(result).stream().findFirst();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return opUser;
    }


    /**
     * Returns AppUser by just username. Used for ensuring no username is used between
     * user accounts.
     * @param username from registration screen
     * @return AppUser.username
     */
    public Optional<AppUser> findUserByUsername(String username) {

        Optional<AppUser> opUser = Optional.empty();

        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {

            String query = "SELECT username FROM project0.app_users " +
                           "WHERE username = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet result = statement.executeQuery();

            opUser = mapResultSet(result).stream().findFirst();


        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
       return opUser;
    }


    /**
     * Saves the newUser into the database during the UserService.registration()
     * @param newUser
     */
    public void save(AppUser newUser) {

        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {

            String query = "INSERT INTO project0.app_users (first_name, last_name, username, password, email, role_id) " +
                           "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newUser.getFirstName());
            statement.setString(2, newUser.getLastName());
            statement.setString(3, newUser.getUsername());
            statement.setString(4, newUser.getPassword());
            statement.setString(5, newUser.getEmail());
            statement.setInt(6, newUser.getRole().ordinal() + 1);

            statement.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }


    public int findUserById(AppUser currentUser) {

        int id = 0;

        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {

            String query = "SELECT id FROM project0.app_users " +
                           "WHERE username = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, currentUser.getUsername());

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                id = result.getInt(1);
            }


        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return id;
    }

    /**
     * Adds the new account into the database
     * @param accountName This is user-defined in the dashboard screen when opting to make a new account
     * @param id This is grabbed from the current user's id assigned to them by their existing spot in the database
     */
    public void addAccount(String accountName, int id) {

        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {

            String query = "INSERT INTO project0.accounts (name, user_id) " +
                           "VALUES (?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, accountName);
            statement.setInt(2, id);

            statement.executeUpdate();


        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }




    /**
     * Gets the results from the query statement and puts the data into a Set of AppUser objects
     * @param results from DAO methods
     * @return Set<AppUser>
     * @throws SQLException
     */
    private Set<AppUser> mapResultSet(ResultSet results) throws SQLException {

        Set<AppUser> user = new HashSet<>();

        while (results.next()) {
            AppUser temp = new AppUser();
            temp.setId(results.getInt("id"));
            temp.setFirstName(results.getString("first_name"));
            temp.setLastName(results.getString("last_name"));
            temp.setUsername(results.getString("username"));
            temp.setPassword(results.getString("password"));
            temp.setEmail(results.getString("email"));
            // this is where we'll get the string of which Role an account is.
            // our method .getRoleName() from our Role class converts the String
            // to Role object.
            temp.setRole(Role.getRoleName(results.getString("name")));
            // Add all the mapped data into a Set<AppUser> object
            user.add(temp);
        }

        return user;

    }

}
