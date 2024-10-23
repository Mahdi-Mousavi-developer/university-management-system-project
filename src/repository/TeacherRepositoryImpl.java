package repository;

import data.Database;
import exception.TeacherNotFindException;
import modle.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeacherRepositoryImpl implements BaseRepository<Teacher> {
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
    private static final String UPDATE_TEACHER = "update public.teachers set national_code=?,first_name=?,last_name=?,dob=?,course_id=?"+
            " where teacher_id=?";
    private static final String UPDATE_EXAM_TABLE_FOR_DELETE_TEACHER = "update public.exam set teacher_id = null , national_code = null"+
    " where teacher_id=?";

    private static final String DELETE_TEACHER_BY_ID_ = "delete from teachers where teacher_id = ?;";
    private static final String FIND_TEACHER_BY_ID = "select * from teachers where teacher_id = ?;";
    private static final String SET_COURSE_ID = "update public.teachers set course_id=? " +
            " where teacher_id = ?";


    @Override
    public List<Teacher> getAll() throws SQLException{
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
    @Override
    public int getCount() throws SQLException{
        ResultSet countResult = database.getSQLStatementS().executeQuery(GET_COUNT_OF_TEACHER);
        int TeacherCount = 0;
        while(countResult.next()){
            TeacherCount = countResult.getInt("count");
        }
        return TeacherCount;
    }
    @Override
    public void saveOrUpdate(Teacher teacher)throws  SQLException {
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

    @Override
    public void delete(int teacherId) throws SQLException, TeacherNotFindException {
        if (this.findById(teacherId).isPresent()) {
            PreparedStatement ps = conn.prepareStatement(UPDATE_EXAM_TABLE_FOR_DELETE_TEACHER);
            ps.setInt(1, teacherId);
            ps.executeUpdate();

              ps = conn.prepareStatement(DELETE_TEACHER_BY_ID_);
              ps.setInt(1, teacherId);
            ps.executeUpdate();

        } else {
            throw new TeacherNotFindException("teacher with id".concat(String.valueOf(teacherId)).concat(" not found"));
        }
    }

    @Override
    public Optional<Teacher> findById(int teacherId) throws SQLException{
        PreparedStatement ps = conn.prepareStatement(FIND_TEACHER_BY_ID);
        ps.setInt(1, teacherId);
        ResultSet rs = ps.executeQuery();
        Optional<Teacher> optionalTeacher = Optional.empty();
        while (rs.next()){
           Teacher teacher = new Teacher();
           teacher.setTeacherId(rs.getInt("teacher_id"));
           teacher.setNationalCode(rs.getString("national_code"));
           teacher.setFirstName(rs.getString("first_name"));
           teacher.setLastName(rs.getString("last_name"));
           teacher.setDob(rs.getDate("dob"));
           teacher.setCourseId(rs.getLong("course_id"));
            optionalTeacher = Optional.of(teacher);

        }
        return optionalTeacher;
    }



    public void setTeacherForCourse(int courseId ,int teacherId) throws SQLException {
        PreparedStatement ps = database.getPreparedStatement(SET_COURSE_ID);
        ps.setInt(1,courseId);
        ps.setInt(2,teacherId);
        ps.executeUpdate();
        }
    }
