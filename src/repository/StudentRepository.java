package repository;

import data.Database;
import modle.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private Database database= new Database();
    private static final String GET_ALL_STUDENT_QUERY = "SELECT * FROM student;";
    private static final String GET_COUNT_OF_STUDENT = "SELECT count(*) FROM student;";

    public List<Student> getAllStudent() throws SQLException {
        ResultSet studentResult = database.getSQLStatementS().executeQuery(GET_ALL_STUDENT_QUERY);
        List<Student> students = new ArrayList<>();
        while (studentResult.next()) {
            Student student = new Student(
                    studentResult.getLong("student_id"),
                    studentResult.getString("national_code"),
                    studentResult.getString("first_name"),
                    studentResult.getString("last_name"),
                    studentResult.getDate("dob"),
                    studentResult.getDouble("gpu")
            );
            students.add(student);


        }return students;
    }
    public int getCountOfStudent() throws SQLException{
        ResultSet countResult = database.getSQLStatementS().executeQuery(GET_COUNT_OF_STUDENT);
        int studentCount = 0;
        while(countResult.next()){
            studentCount = countResult.getInt("count");
        }
        return studentCount;
    }



}
