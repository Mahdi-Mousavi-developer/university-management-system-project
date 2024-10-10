package service;

import modle.Exam;
import modle.Student;
import modle.dto.SaveExamRequest;
import modle.dto.SaveStudentRequest;
import repository.ExamRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class ExamServiceImpl {
    private ExamRepositoryImpl examRepository = new ExamRepositoryImpl();

    public void printAllExam() {
        try {
            List<Exam> examList = examRepository.getAllExam();
            for (Exam exam : examList) {
                System.out.println(exam);
            }


        } catch (SQLException sqlException) {
            System.out.println("there is problem with connecting to database");
        }
    }

    public void PrintCountOfExam() {
        try {
            int countOfExam = examRepository.getCountOfExam();
            System.out.println("number of all exam : ".concat(String.valueOf(countOfExam)));
        } catch (SQLException sqlException) {
            System.out.println("there is problem with connecting to database");
        }
    }

    public void delete(Integer id) {
        checkIdExam(id);
        try {
            examRepository.deleteExam(id);
            printAllExam();
        } catch (SQLException e) {
            System.out.println("there is problem with connecting to database");
        }
    }

    public void checkIdExam(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("exam id can not be null");
        }
    }
    public void saveAndUpdate(SaveExamRequest request) {
        checkExam(request);
        try {
            examRepository.saveOrUpdateExam(new Exam(
                    request.getExamId(),
                    request.getTeacherId(),
                    request.getNationalCode(),
                    request.getCourseId(),
                    request.getExamGrade(),
                    request.getExamDate()
            ));
        } catch (SQLException e) {
            System.out.println("there is problem with connecting to database");
        }

    }
    public void checkExam(SaveExamRequest request){

        if (request.getNationalCode()==null){
            throw new IllegalArgumentException("national code can not be null please input something there bro");

        }
        if ( request.getExamGrade()<0.0 || request.getExamGrade()>100.0){
            throw new IllegalArgumentException("put a number between 0 & 100 in this field");

        }
    }
}



