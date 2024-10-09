package repository;

import data.Database;
import modle.Exam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamRepositoryImpl {
    private Database database = new Database();
    private static final String GET_ALL_EXAM_QUERY = "SELECT * FROM exam;";
    private static final String GET_COUNT_OF_EXAM = "SELECT count(*) FROM exam;";


    public List<Exam> getAllExam() throws SQLException {
        ResultSet examResult = database.getSQLStatementS().executeQuery(GET_ALL_EXAM_QUERY);
        List<Exam> examList = new ArrayList<>();
        while (examResult.next()){
            Exam exam = new Exam(
                    examResult.getLong("exam_id"),
                    examResult.getLong("teacher_id"),
                    examResult.getString("national_code"),
                    examResult.getLong("course_id"),
                    examResult.getLong("exam_grade"),
                    examResult.getDate("exam_date")
            );
            examList.add(exam);
        }
        return examList;
    }
    public int getCountOfExam ()throws SQLException{
        ResultSet examSet = database.getSQLStatementS().executeQuery(GET_COUNT_OF_EXAM);
        int countOfExam = 0;
        while (examSet.next()){
            countOfExam = examSet.getInt("count");
        }
        return countOfExam;
    }





}
