package data;

import java.sql.*;

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
    public static Connection getConnectionStatic() throws SQLException{
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
    }
    public PreparedStatement getPreparedStatement(String sql) throws SQLException{
        return this.getConnectionToDataBase().prepareStatement(sql);
    }
}
