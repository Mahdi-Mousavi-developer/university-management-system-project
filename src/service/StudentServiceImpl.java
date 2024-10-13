package service;

import exception.StudentNotFindException;
import modle.Student;
import modle.dto.SaveStudentRequest;
import repository.StudentRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class StudentServiceImpl {
    private StudentRepositoryImpl studentRepositoryImpl = new StudentRepositoryImpl();
    public  void printAllStudentList(){
        try{
            List<Student> students = studentRepositoryImpl.getAll();
            for (Student student : students){
                System.out.println(student);
            }
        }catch (SQLException sqlException){
            System.out.println("there is problem with connecting to database");
        }


    }

    public void printCountOfStudent() {
        try {
            int countStudent = studentRepositoryImpl.getCount();
            System.out.println("number of student : ".concat(String.valueOf(countStudent)));
        }catch(SQLException sqlException){
            System.out.println("there is problem with connecting to database");
        }
    }

    public void saveAndUpdate(SaveStudentRequest request) {
        checkStudent(request);
        try {
            studentRepositoryImpl.saveOrUpdate(new Student(
                    request.getStudentId(),
                    request.getNationalCode(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getDob(),
                    request.getGpu()
            ));
        } catch (SQLException e) {
            System.out.println("there is problem with connecting to database");
        }

    }

    public void delete (Integer id){
        checkIdStudent(id);
        try {
            studentRepositoryImpl.delete(id);
            printAllStudentList();
        } catch (StudentNotFindException e) {
            System.out.println(e.getMessage());
        }catch (SQLException e){
            System.out.println("there is problem with connecting to database");
        }
    }
    public  void checkIdStudent(Integer id){
        if (id == null){
            throw new IllegalArgumentException("student id can not be null");
        }
    }
    public void checkStudent(SaveStudentRequest request){
        if (request.getFirstName()==null|| request.getLastName()==null){
            throw new IllegalArgumentException("first name or last name can not be null please input something there bro");
        }
        if (request.getNationalCode()==null){
            throw new IllegalArgumentException("national code can not be null please input something there bro");

        }
        if ( request.getGpu()<0.0 || request.getGpu()>100.0){
            throw new IllegalArgumentException("put a number between 0 & 100 in this field");

        }
    }
}
