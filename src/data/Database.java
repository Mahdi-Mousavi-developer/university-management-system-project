package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/schoolmanagment";
    private static final String DATABASE_USERNAME = "postgres";
    private static final String DATABASE_PASSWORD = "2";

    public Connection getConnectionToDataBase() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
    }

    public Statement getSQLStatementS() throws SQLException {
        return getConnectionToDataBase().createStatement();
    }

}
