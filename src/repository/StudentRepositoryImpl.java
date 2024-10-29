package repository;

import data.Database;
import exception.StudentNotFindException;
import modle.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepositoryImpl implements BaseRepository<Student> {
    private Database database = new Database();
    private static Connection conn;

    static {
        try {
            conn = Database.getConnectionStatic();
        } catch (SQLException sqlException) {
            System.out.println("there is problem with connecting to database");
        }
    }

    private static final String GET_ALL_STUDENT_QUERY = "SELECT * FROM student;";
    private static final String GET_COUNT_OF_STUDENT = "SELECT count(*) FROM student;";
    private static final String SAVE_STUDENT = "insert into public.student (national_code,first_name,last_name,dob,gpu)" +
            "values(?,?,?,?,?)";
    private static final String UPDATE_STUDENT = "update public.student set national_code=?,first_name=?,last_name=?,dob=?,gpu=?" +
            " where student_id=?";
    private static final String DELETE_STUDENT_COURSE = "delete from students_course where student_id = ?;";
    private static final String DELETE_STUDENT_BY_ID = "delete from student where student_id = ?;";
    private static final String FIND_STUDENT_BY_ID = "select * from student where student_id = ?;";


    @Override
    public List<Student> getAll() throws SQLException {
        ResultSet studentResult = database.getSQLStatementS().executeQuery(GET_ALL_STUDENT_QUERY);
        List<Student> students = new ArrayList<>();
        while (studentResult.next()) {
            Student student = new Student(
                    studentResult.getInt("student_id"),
                    studentResult.getString("national_code"),
                    studentResult.getString("first_name"),
                    studentResult.getString("last_name"),
                    studentResult.getDate("dob"),
                    studentResult.getDouble("gpu")
            );
            students.add(student);
        }
        return students;
    }

    @Override
    public int getCount() throws SQLException {
        ResultSet countResult = database.getSQLStatementS().executeQuery(GET_COUNT_OF_STUDENT);
        int studentCount = 0;
        while (countResult.next()) {
            studentCount = countResult.getInt("count");
        }
        return studentCount;
    }

    @Override
    public void saveOrUpdate(Student student) throws SQLException {
        if (student.getStudentId() == null) {
            saveStudent(student);
        } else {
            mergeStudent(student);
        }
    }

    public void saveStudent(Student student) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(SAVE_STUDENT);
        ps.setString(1, student.getNationalCode());
        ps.setString(2, student.getFirstName());
        ps.setString(3, student.getLastName());
        ps.setDate(4, new Date(student.getDob().getTime()));
        ps.setDouble(5, student.getGpu());
        ps.executeUpdate();

    }

    public void mergeStudent(Student student) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(UPDATE_STUDENT);
        ps.setString(1, student.getNationalCode());
        ps.setString(2, student.getFirstName());
        ps.setString(3, student.getLastName());
        ps.setDate(4, new Date(student.getDob().getTime()));
        ps.setDouble(5, student.getGpu());
        ps.setInt(6, student.getStudentId());
        ps.executeUpdate();

    }

    @Override
    public void delete(int studentId) throws SQLException, StudentNotFindException {
        if (this.findById(studentId).isPresent()) {
            PreparedStatement ps = conn.prepareStatement(DELETE_STUDENT_COURSE);
            ps.setInt(1, studentId);
            ps.executeUpdate();

            ps = conn.prepareStatement(DELETE_STUDENT_BY_ID);
            ps.setInt(1, studentId);
            ps.executeUpdate();

        } else {
            throw new StudentNotFindException("Student with id".concat(String.valueOf(studentId)).concat(" not found"));
        }


    }

    @Override
    public Optional<Student> findById(int studentId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(FIND_STUDENT_BY_ID);
        ps.setInt(1, studentId);
        ResultSet rs = ps.executeQuery();
        Optional<Student> optionalStudent = Optional.empty();
        while (rs.next()) {
            Student student = new Student();
            student.setStudentId(rs.getInt("student_id"));
            student.setNationalCode(rs.getString("national_code"));
            student.setFirstName(rs.getString("first_name"));
            student.setLastName(rs.getString("last_name"));
            student.setDob(rs.getDate("dob"));
            student.setGpu(rs.getDouble("gpu"));
            optionalStudent = Optional.of(student);

        }
        return optionalStudent;
    }


}
