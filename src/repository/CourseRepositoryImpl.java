package repository;

import data.Database;
import exception.StudentNotFindException;
import modle.Course;
import modle.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseRepositoryImpl {
    private Database database = new Database();


    private static Connection conn;

    static {
        try {
            conn = Database.getConnectionStatic();
        } catch (SQLException sqlException) {
            System.out.println("there is problem with connecting to database");
        }
    }

    private static final String GET_ALL_Course_QUERY = "SELECT * FROM course;";
    private static final String GET_COUNT_OF_Course = "SELECT count(*) FROM course;";
    private static final String SAVE_COURSE = "insert into public.course (course_title , course_unite)" +
            "values(?,?)";
    private static final String UPDATE_COURSE = "update public.course set course_title=? , course_unite=?" +
            " where course_id = ?";
    private static final String UPDATE_EXAM_TABLE_FOR_COURSE = "update public.exam set course_id=null " +
            " where course_id = ?";
    private static final String UPDATE_TEACHER_TABLE_FOR_COURSE = "update public.teachers set course_id=null " +
            " where course_id = ?";
    private static final String DELETE_COURSE_STUDENT = "delete from students_course where course_id = ?;";
    private static final String DELETE_COURSE_BY_ID = "delete from course where course_id = ?;";
    private static final String FIND_COURSE_BY_ID = "select * from course where course_id= ?;";

    public List<Course> getAllCourse() throws SQLException {
        ResultSet courseResult = database.getSQLStatementS().executeQuery(GET_ALL_Course_QUERY);
        List<Course> courseList = new ArrayList<>();
        while (courseResult.next()) {
            Course course = new Course(
                    courseResult.getInt("course_id"),
                    courseResult.getString("course_title"),
                    courseResult.getLong("course_unite")
            );
            courseList.add(course);
        }
        return courseList;
    }

    public int getCountOfCourse() throws SQLException {
        ResultSet courseResult = database.getSQLStatementS().executeQuery(GET_COUNT_OF_Course);
        int countOfCourse = 0;
        while (courseResult.next()) {
            countOfCourse = courseResult.getInt("count");
        }
        return countOfCourse;
    }

    public void saveOrUpdateCourse(Course course) throws SQLException {
        if (course.getCourseId() == null) {
            saveCourse(course);
        } else {
            mergeCourse(course);
        }
    }

    public void saveCourse(Course course) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(SAVE_COURSE);
        ps.setString(1, course.getCourseTitle());
        ps.setLong(2, course.getCourseUnite());
        ps.executeUpdate();

    }

    public void mergeCourse(Course course) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(UPDATE_COURSE);
        ps.setString(1, course.getCourseTitle());
        ps.setLong(2, course.getCourseUnite());
        ps.setInt(3, course.getCourseId());
        ps.executeUpdate();
    }
    public void deleteCourse(int courseId) throws SQLException, StudentNotFindException {
        if(this.findById(courseId).isPresent()) {
            PreparedStatement ps = database.getPreparedStatement(DELETE_COURSE_STUDENT);
            ps.setInt(1, courseId);
            ps.executeUpdate();

            ps = conn.prepareStatement(UPDATE_EXAM_TABLE_FOR_COURSE);
            ps.setInt(1, courseId);
            ps.executeUpdate();

            ps = conn.prepareStatement(UPDATE_TEACHER_TABLE_FOR_COURSE);
            ps.setInt(1, courseId);
            ps.executeUpdate();

            ps = conn.prepareStatement(DELETE_COURSE_BY_ID);
            ps.setInt(1, courseId);
            ps.executeUpdate();

        }else{
            throw new StudentNotFindException("Student with id".concat(String.valueOf(courseId)).concat(" not found"));
        }


    }
    public Optional<Course> findById(int courseId) throws SQLException{
        PreparedStatement ps = conn.prepareStatement(FIND_COURSE_BY_ID);
        ps.setInt(1, courseId);
        ResultSet rs = ps.executeQuery();
        Optional<Course> optionalCourse = Optional.empty();
        while (rs.next()){
            Course course = new Course();
            course.setCourseId(rs.getInt("course_id"));
            course.setCourseTitle(rs.getString("course_title"));
            course.setCourseUnite(rs.getLong("course_unite"));
            optionalCourse = Optional.of(course);

        }
        return optionalCourse;
    }


 }
