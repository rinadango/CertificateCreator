package CertificateCreator;

import java.sql.*;

/**
 * Connects to a database and retrieves oll of the contents from a specified table by 'tableName'
 */
public class DatabaseConnector {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:****/****?serverTimezone=UTC"; // must be a sql database
    private static final String DATABASE_USER = "****"; // must be your own set user name
    private static final String DATABASE_PASSWORD = "****"; // must be your own set password

    /**
     * @param query SQL query of your choice
     */
    public ResultSet connectionToDatabase(String query) throws SQLException {

        // Establishes and holds connection related data
        Connection myConnection = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);

        // Allows creating and execute statements towards connected database
        // We use STATEMENT object to EXECUTE QUERIES
        Statement statement = myConnection.createStatement();

        // SELECT * FROM people WHERE grades_average > 5;
        // Holds the results from the executed query towards the database
        ResultSet resultSet = statement.executeQuery(query);

        return resultSet;
    }

}
