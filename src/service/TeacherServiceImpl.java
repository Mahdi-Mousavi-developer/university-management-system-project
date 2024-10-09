package service;

import modle.Teacher;
import repository.TeacherRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class TeacherServiceImpl {
    private TeacherRepositoryImpl teacherRepository = new TeacherRepositoryImpl();

    public void printAllTeacher(){
        try{
            List<Teacher> teacherList = teacherRepository.getAllTeacher();
            for (Teacher teacher:teacherList)
            {
                System.out.println(teacher);
            }
        }catch (SQLException sqlException){
            System.out.println("there is problem with connecting to database");
        }
    }
    public void PrintCountTeacher (){

        try{
            int countOfTeacher = teacherRepository.getCountOfTeacher();
            System.out.println("number of teacher = ".concat(String.valueOf(countOfTeacher)));
        }catch (SQLException sqlException){
            System.out.println("there is problem with connecting to database");
        }

    }


}
