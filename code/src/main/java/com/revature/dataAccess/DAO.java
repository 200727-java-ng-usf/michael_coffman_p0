package com.revature.dataAccess;

import com.revature.models.AppUser;
import com.revature.models.Role;
import com.revature.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
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
     * Gets the results from the query statement and puts the data into a Set of AppUser objects
     * @param results
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
