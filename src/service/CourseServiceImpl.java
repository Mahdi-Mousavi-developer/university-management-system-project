package service;

import modle.Course;
import repository.CourseRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class CourseServiceImpl {
    private CourseRepositoryImpl courseRepository = new CourseRepositoryImpl();
    public  void printAllCourse(){
        try{
            List<Course> courseList = courseRepository.getAllCourse();
            for (Course course:courseList)
            {
                System.out.println(course);
            }
        }catch (SQLException sqlException){
            System.out.println("there is problem with connecting to database");
        }
    }
    public void printCountCourse(){
        try{
            int countOfCourse = courseRepository.getCountOfCourse();
            System.out.println("number of all course : ".concat(String.valueOf(countOfCourse)));
        }catch (SQLException sqlException){
            System.out.println("there is problem with connecting to database");
        }
    }
}
