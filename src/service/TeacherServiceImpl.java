package service;


import exception.TeacherNotFindException;
import modle.Teacher;

import modle.dto.SaveTeacherRequest;
import repository.TeacherRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class TeacherServiceImpl {
    private TeacherRepositoryImpl teacherRepository = new TeacherRepositoryImpl();

    public void printAllTeacher() {
        try {
            List<Teacher> teacherList = teacherRepository.getAllTeacher();
            for (Teacher teacher : teacherList) {
                System.out.println(teacher);
            }
        } catch (SQLException sqlException) {
            System.out.println("there is problem with connecting to database");
        }
    }

    public void PrintCountTeacher() {

        try {
            int countOfTeacher = teacherRepository.getCountOfTeacher();
            System.out.println("number of teacher = ".concat(String.valueOf(countOfTeacher)));
        } catch (SQLException sqlException) {
            System.out.println("there is problem with connecting to database");
        }

    }

    public void delete(Integer id) {
        checkIdTeacher(id);
        try {
            teacherRepository.deleteTeacher(id);
            System.out.println("done");
            printAllTeacher();
        } catch (SQLException | TeacherNotFindException e) {
            System.out.println("there is problem with connecting to database");
        }
    }

    public void checkIdTeacher(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("teacher id can not be null");
        }


    }
    public void saveAndUpdate(SaveTeacherRequest request) {
        checkTeacher(request);
        try {
            teacherRepository.saveOrUpdateTeacher(new Teacher(
                    request.getTeacherId(),
                    request.getNationalCode(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getDob(),
                    request.getCourseId()
            ));
        } catch (SQLException e) {
            System.out.println("there is problem with connecting to database");
        }

    }
    public void checkTeacher(SaveTeacherRequest request){
        if (request.getFirstName()==null|| request.getLastName()==null){
            throw new IllegalArgumentException("first name or last name can not be null please input something there bro");
        }
        if (request.getNationalCode()==null){
            throw new IllegalArgumentException("national code can not be null please input something there bro");

        }

    }

}
