package repository;

import data.Database;
import modle.Exam;
import modle.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamRepositoryImpl {
    private Database database = new Database();
    private static Connection conn;

    static {
        try {
            conn = Database.getConnectionStatic();
        } catch (SQLException sqlException) {
            System.out.println("there is problem with connecting to database");
        }
    }

    private static final String GET_ALL_EXAM_QUERY = "SELECT * FROM exam;";
    private static final String GET_COUNT_OF_EXAM = "SELECT count(*) FROM exam;";
    private static final String SAVE_EXAM = "insert into public.exam (teacher_id,national_code,course_id,exam_grade,exam_date)" +
            "values(?,?,?,?,?)";
    private static final String UPDATE_EXAM = "update public.exam set teacher_id=?,national_code=?,course_id=?,exam_grade=?,exam_date=?" +
            "where exam_id=?";

    private static final String DELETE_EXAM = "delete from exam where exam_id = ?;";


    public List<Exam> getAllExam() throws SQLException {
        ResultSet examResult = database.getSQLStatementS().executeQuery(GET_ALL_EXAM_QUERY);
        List<Exam> examList = new ArrayList<>();
        while (examResult.next()) {
            Exam exam = new Exam(
                    examResult.getInt("exam_id"),
                    examResult.getInt("teacher_id"),
                    examResult.getString("national_code"),
                    examResult.getInt("course_id"),
                    examResult.getLong("exam_grade"),
                    examResult.getDate("exam_date")
            );
            examList.add(exam);
        }
        return examList;
    }

    public int getCountOfExam() throws SQLException {
        ResultSet examSet = database.getSQLStatementS().executeQuery(GET_COUNT_OF_EXAM);
        int countOfExam = 0;
        while (examSet.next()) {
            countOfExam = examSet.getInt("count");
        }
        return countOfExam;
    }

    public void saveOrUpdateExam(Exam exam) throws SQLException {
        if (exam.getExamId() == null) {
            saveExam(exam);
        } else {
            mergeExam(exam);
        }
    }

    public void saveExam(Exam exam) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(SAVE_EXAM);
        ps.setLong(1, exam.getTeacherId());
        ps.setString(2, exam.getNationalCode());
        ps.setLong(3, exam.getCourseId());
        ps.setDouble(4, exam.getExamGrade());
        ps.setDate(5, new Date(exam.getExamDate().getTime()));
        ps.executeUpdate();
    }
    public void mergeExam(Exam exam) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(UPDATE_EXAM);
        ps.setLong(1, exam.getTeacherId());
        ps.setString(2, exam.getNationalCode());
        ps.setLong(3, exam.getCourseId());
        ps.setDouble(4, exam.getExamGrade());
        ps.setDate(5, new Date(exam.getExamDate().getTime()));
        ps.setInt(6,exam.getExamId());
        ps.executeUpdate();
    }
    public void deleteExam (int examId) throws  SQLException{
        PreparedStatement ps = conn.prepareStatement(DELETE_EXAM);
        ps.setInt(1,examId);
        ps.executeUpdate();


    }
}
