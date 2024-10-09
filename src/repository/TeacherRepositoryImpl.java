package repository;

import data.Database;
import modle.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepositoryImpl {
    private Database database= new Database();
    private static final String GET_ALL_TEACHER_QUERY = "SELECT * FROM teachers;";
    private static final String GET_COUNT_OF_TEACHER = "SELECT count(*) FROM teachers";

    public List<Teacher> getAllTeacher() throws SQLException{
        ResultSet teacherResult = database.getSQLStatementS().executeQuery(GET_ALL_TEACHER_QUERY);
        List<Teacher> teachers = new ArrayList<>();
        while(teacherResult.next()){
            Teacher teacher = new Teacher(
                    teacherResult.getLong("teacher_id"),
                    teacherResult.getString("national_code"),
                    teacherResult.getString("first_name"),
                    teacherResult.getString("last_name"),
                    teacherResult.getDate("dob"),
                    teacherResult.getLong("course_id")

            );
            teachers.add(teacher);
        }return teachers;
    }
    public int getCountOfTeacher() throws SQLException{
        ResultSet countResult = database.getSQLStatementS().executeQuery(GET_COUNT_OF_TEACHER);
        int TeacherCount = 0;
        while(countResult.next()){
            TeacherCount = countResult.getInt("count");
        }
        return TeacherCount;
    }




}
