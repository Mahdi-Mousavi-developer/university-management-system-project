package repository;

import data.Database;
import modle.Student;
import modle.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepositoryImpl {
    private Database database= new Database();
    private  static Connection conn;
    static{
        try{
            conn =Database.getConnectionStatic();
        }catch (SQLException sqlException){
            System.out.println("there is problem with connecting to database");
        }
    }
    private static final String GET_ALL_TEACHER_QUERY = "SELECT * FROM teachers;";
    private static final String GET_COUNT_OF_TEACHER = "SELECT count(*) FROM teachers";

    private static final String SAVE_TEACHER = "insert into public.teachers (national_code,first_name,last_name,dob,course_id)"+
            "values(?,?,?,?,?)";
    private static final String UPDATE_TEACHER = "update public.teachers set (national_code,first_name,last_name,dob,course_id)"+
            "values(?,?,?,?,?) where teacher_id=?";

    private static final String DELETE_TEACHER ="delete from teachers where student_id = ?;";
    public List<Teacher> getAllTeacher() throws SQLException{
        ResultSet teacherResult = database.getSQLStatementS().executeQuery(GET_ALL_TEACHER_QUERY);
        List<Teacher> teachers = new ArrayList<>();
        while(teacherResult.next()){
            Teacher teacher = new Teacher(
                    teacherResult.getInt("teacher_id"),
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
    public void saveOrUpdateTeacher(Teacher teacher)throws  SQLException {
        if (teacher.getTeacherId()==null){
            saveTeacher(teacher);
        }else {
            mergeTeacher(teacher);
        }
    }
    public void saveTeacher(Teacher teacher) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(SAVE_TEACHER);
        ps.setString(1,teacher.getNationalCode());
        ps.setString(2,teacher.getFirstName());
        ps.setString(3,teacher.getLastName());
        ps.setDate(4,new Date(teacher.getDob().getTime()));
        ps.setLong(5,teacher.getCourseId());
        ps.executeUpdate();

    }
    public void mergeTeacher(Teacher teacher) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(UPDATE_TEACHER);
        ps.setString(1,teacher.getNationalCode());
        ps.setString(2,teacher.getFirstName());
        ps.setString(3,teacher.getLastName());
        ps.setDate(4,new Date(teacher.getDob().getTime()));
        ps.setLong(5,teacher.getCourseId());
        ps.setInt(6,teacher.getTeacherId());
        ps.executeUpdate();

    }

    public void DeleteTeacher (int teacherId) throws  SQLException{
        PreparedStatement ps = conn.prepareStatement(DELETE_TEACHER);
        ps.setInt(1,teacherId);
        ps.executeUpdate();


    }


}
