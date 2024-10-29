package service;

import exception.StudentNotFindException;
import modle.Course;

import modle.dto.SaveCourseRequest;

import repository.CourseRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class CourseServiceImpl {
    private CourseRepositoryImpl courseRepository = new CourseRepositoryImpl();

    public CourseServiceImpl(CourseRepositoryImpl courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void printAllCourse() {
        try {
            List<Course> courseList = courseRepository.getAll();
            for (Course course : courseList) {
                System.out.println(course);
            }
        } catch (SQLException sqlException) {
            System.out.println("there is problem with connecting to database");
        }
    }

    public void printCountCourse() {
        try {
            int countOfCourse = courseRepository.getCount();
            System.out.println("number of all course : ".concat(String.valueOf(countOfCourse)));
        } catch (SQLException sqlException) {
            System.out.println("there is problem with connecting to database");
        }
    }

    public void delete(Integer id) {
        checkIdCourse(id);
        try {
            courseRepository.delete(id);
            printAllCourse();
        } catch (StudentNotFindException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("there is problem with connecting to database");
        }
    }

    public void saveAndUpdate(SaveCourseRequest request) {
        checkCourse(request);
        try {

                   courseRepository.saveOrUpdate(new Course(
                       request.getCourseId(),
                       request.getCourseTitle(),
                       request.getCourseUnite()
                   ));



        } catch (SQLException e) {
            System.out.println("there is problem with connecting to database");
        }

    }

    public void addStudentsToCourse(int courseId, int studentId, String nationalCode) {
        try {
            courseRepository.addStudentToCourse(courseId, studentId, nationalCode);
        } catch (SQLException e) {
            System.out.println("there is problem with connecting to database");
        }
    }


    public void checkIdCourse(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("course id can not be null");
        }
    }

    public void checkCourse(SaveCourseRequest request) {
        if (request.getCourseTitle() == null) {
            throw new IllegalArgumentException("title can not be null please input something there bro");
        }
    }
}
