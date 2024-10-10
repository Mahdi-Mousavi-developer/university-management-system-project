package repository;

import data.Database;
import modle.Course;
import modle.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    private static final String UPDATE_COURSE = "update public.course set(course_title , course_unite)" +
            "values(?,?) where course_id = ?";
    private static final String DELETE_COURSE = "delete from course where course_id = ?;";

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
    public void deleteCourse(int courseId) throws  SQLException {
        PreparedStatement ps = conn.prepareStatement(DELETE_COURSE);
        ps.setInt(1, courseId);
        ps.executeUpdate();
    }



 }
