package service;

import exception.ExamNotFindException;
import modle.Exam;
import modle.dto.SaveExamRequest;
import repository.ExamRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class ExamServiceImpl {
    private ExamRepositoryImpl examRepository = new ExamRepositoryImpl();

    public void printAllExam() {
        try {
            List<Exam> examList = examRepository.getAll();
            for (Exam exam : examList) {
                System.out.println(exam);
            }


        } catch (SQLException sqlException) {
            System.out.println("there is problem with connecting to database");
        }
    }

    public void PrintCountOfExam() {
        try {
            int countOfExam = examRepository.getCount();
            System.out.println("number of all exam : ".concat(String.valueOf(countOfExam)));
        } catch (SQLException sqlException) {
            System.out.println("there is problem with connecting to database");
        }
    }

    public void delete(Integer id) {
        checkIdExam(id);
        try {
            examRepository.delete(id);
            printAllExam();
        } catch (ExamNotFindException e) {
            System.out.println(e.getMessage());
        }catch (SQLException e){
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
            examRepository.saveOrUpdate(new Exam(
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
    public void addExamToStudent(int studentId , String nationalCode , int examId){
        try{
        examRepository.setAddExamToStudent(studentId , nationalCode , examId);
         }catch (SQLException sqlException){
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
    public void GradeFinalized ( int examId){
        try {
            examRepository.gradeFinalizedMethod(examId);
        } catch (SQLException e) {
            System.out.println("there is problem with connecting to database");
        }
    }
    public void changeGrade ( int newGrade ,int examId) {
        try {
            examRepository.changeGrade(newGrade , examId);
        } catch (SQLException e) {
            System.out.println("there is problem with connecting to database");
        }
    }
    public void studentSeeHisGrade (int studentId){
        try {
            examRepository.studentSeeHisGrade(studentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void printWhatStudentSee(int studentId) {
        try {
            double avg = examRepository.avgStudentGrade(studentId);
            System.out.println(avg);
            int[] whatStudentSee = examRepository.studentSeeHisGrade(studentId);
            System.out.println("student id : "+whatStudentSee[0]+" national code : "+whatStudentSee[1]+" exam id :"+whatStudentSee[2]+" grade :"+whatStudentSee);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}



