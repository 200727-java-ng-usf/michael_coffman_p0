package com.revature.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Setting up an "eager" singleton.
    // Private makes only this class be able to instantiate it.
    private static DatabaseConnection dataConnect = new DatabaseConnection();

    // Default Constructor
    // TODO change this to load from properties file
    private DatabaseConnection() {
        super();
    }

    // We make this method public, so if we need the DatabaseConnection,
    // it will only give the one that is already instantiated.
    public static DatabaseConnection getInstance() {
        return dataConnect;
    }

    /**
     * Establishes a connection with the database.
     * @return Connection conn
     */
    public Connection getConnection() {

        // Instantiates a Connection object to null to ensure
        // the app is not already connected.
        Connection conn = null;

        try {

            // This gets language specific driver; we are using PostGreSQL
            Class.forName("org.postgresql.Driver");

            // This is DriverManager actually establishing the connection with
            // the username and password we've set for our database. This is root
            // user access
            // TODO make the connection work with user and password we set specific for Project0 Schema
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://revature-training.cveu74hasekl.us-east-1.rds.amazonaws.com:5432/postgres",
                    "postgres",
                    "Ultimate1!"
            );

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        if (conn == null) {
            throw new RuntimeException("Failed to establish connection.");
        }
        return conn;
    }

    // This is part of our singleton setup. To ensure clones of dataConnect
    // do not get created.
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

}
