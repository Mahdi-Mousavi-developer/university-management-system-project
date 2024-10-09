package repository;

import data.Database;
import modle.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseRepositoryImpl {
    private Database database = new Database();

    private static final String GET_ALL_Course_QUERY = "SELECT * FROM course;";
    private static final String GET_COUNT_OF_Course = "SELECT count(*) FROM course;";


    public List<Course> getAllCourse() throws SQLException{
        ResultSet courseResult = database.getSQLStatementS().executeQuery(GET_ALL_Course_QUERY);
        List<Course> courseList = new ArrayList<>();
        while (courseResult.next()){
            Course course = new Course(
                    courseResult.getLong("course_id"),
                    courseResult.getString("course_title"),
                    courseResult.getLong("course_unite")
            );
            courseList.add(course);
        }return courseList;
    }

    public int getCountOfCourse()throws SQLException{
        ResultSet courseResult = database.getSQLStatementS().executeQuery(GET_COUNT_OF_Course);
        int countOfCourse = 0;
        while (courseResult.next()){
            countOfCourse = courseResult.getInt("count");
        } return  countOfCourse;
    }

}
