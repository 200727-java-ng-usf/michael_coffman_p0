package com.revature.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    // Setting up an "eager" singleton.
    // Private makes only this class be able to instantiate it.
    private static DatabaseConnection dataConnect = new DatabaseConnection();

    private Properties properties = new Properties();

    // Default Constructor
    // TODO change this to load from properties file
    private DatabaseConnection() {
        super();
        try {
            properties.load(new FileReader("src/main/resources/application.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

            // .properties file won't recognize url tag, and thinks the whole thing is null......
            conn = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("username"),
                    properties.getProperty("password")
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
