package com.revature.p1.util.factory;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Jbialon
 * Date: 5/7/2021
 * Time: 8:25 AM
 * Description: Factory singleton that gives us a connection to the
 * database via jdbc.
 */
public class ConnectionFactory {

    private static ConnectionFactory connectionFactory;
    private Properties props = new Properties();

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    private ConnectionFactory() {
//        try {
//            ClassLoader loader = Thread.currentThread().getContextClassLoader();
//            InputStream input = loader.getResourceAsStream("application.properties");
//            System.out.println("connection factory " + input);
//            props.load(input);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private ConnectionFactory() {
        try {
            props.load(new FileReader("WEB-INF/application.properties"));
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    /**
     * Description: If the ConnectionFactory hasn't been instantiated instantiate
     * it and return it, otherwise return it without creating.
     *
     * @return ConnectionFactory
     */

    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
        }

        return connectionFactory;
    }

    /**
     * Description: Establishes connection to the database using credentials
     * from the application.properties file.
     *
     * @return Connection
     */

    public Connection getConnection() {

        Connection conn = null;

        try {

            conn = DriverManager.getConnection(
                    System.getenv("host_url"),
                    System.getenv("db_username"),
                    System.getenv("db_password"));
//                    props.getProperty("host-url"),
//                    props.getProperty("username"),
//                    props.getProperty("password"));


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
