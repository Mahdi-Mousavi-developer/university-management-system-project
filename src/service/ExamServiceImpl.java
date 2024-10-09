package service;

import modle.Exam;
import repository.ExamRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class ExamServiceImpl {
    private ExamRepositoryImpl examRepository = new ExamRepositoryImpl();

    public void printAllExam (){
        try {
            List<Exam> examList = examRepository.getAllExam();
            for (Exam exam:examList)
            {
                System.out.println(exam);
            }


        }catch(SQLException sqlException) {
            System.out.println("there is problem with connecting to database");
        }
    }
    public void PrintCountOfExam (){
        try{
            int countOfExam = examRepository.getCountOfExam();
            System.out.println("number of all exam : ".concat(String.valueOf(countOfExam)));
        }catch (SQLException sqlException) {
            System.out.println("there is problem with connecting to database");
        }
        }
    }

