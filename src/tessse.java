import java.sql.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class tessse{
    public static void main (String[]args){
        String databaseUrl = "jdbc:postgresql://localhost:5432/schoolmanagment";
        String databaseUsername = "postgres";
        String databasepassword = "2";
        String studentQuery = "SELECT * FROM student";
        try{
            Connection conn =DriverManager.getConnection(databaseUrl,databaseUsername,databasepassword);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery (studentQuery);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("first_name"));

            }
            System.out.print ("connected");

        } catch (SQLException sqlException){
            System.out.println("failed");
        }


    }
}
