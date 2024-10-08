package service;

import modle.Student;
import repository.StudentRepository;

import java.sql.SQLException;
import java.util.List;

public class StudentServiceImpl {
    private StudentRepository studentRepository = new StudentRepository();
    public  void printAllStudentList(){
        try{
            List<Student> students = studentRepository.getAllStudent();
            for (Student student : students){
                System.out.println(student);
            }
        }catch (SQLException sqlException){
            System.out.println("there is problem with connecting to database");
        }


    }

    public void printCountOfStudent() {
        try {
            int countStudent = studentRepository.getCountOfStudent();
            System.out.println("number of student : ".concat(String.valueOf(countStudent)));
        }catch(SQLException sqlException){
            System.out.println("there is problem with connecting to database");
        }
    }

}
