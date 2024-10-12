package repository;

import data.Database;
import exception.ExamNotFindException;
import modle.Exam;
import modle.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private static final String DELETE_EXAM_STUDENT= "delete from exams_students where exam_id = ?;";

    private static final String DELETE_EXAM_BY_ID = "delete from exam where exam_id = ?;";

    private static final String FIND_COURSE_BY_ID = "select * from exam where exam_id=?";

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
    public void deleteExam(int examId) throws SQLException, ExamNotFindException{
        if (findId(examId).isPresent()){
            PreparedStatement ps = conn.prepareStatement(DELETE_EXAM_STUDENT);
            ps.setInt(1,examId);
            ps.executeUpdate();

            ps = conn.prepareStatement(DELETE_EXAM_BY_ID);
            ps.setInt(1 , examId);
            ps.executeUpdate();

        }else{
            throw new ExamNotFindException("this exam with this id is not exist");
        }

    }
    public Optional<Exam> findId(int examId)throws  SQLException{
       PreparedStatement ps = conn.prepareStatement(FIND_COURSE_BY_ID);
       ps.setInt(1,examId);
       ResultSet rs = ps.executeQuery();
        Optional<Exam> optionalExam = Optional.empty();
       while(rs.next()){
           Exam exam = new Exam();
           exam.setExamId(rs.getInt("exam_id"));
           exam.setTeacherId(rs.getInt("teacher_id"));
           exam.setNationalCode(rs.getString("national_code"));
           exam.setCourseId(rs.getInt("course_id"));
           exam.setExamGrade(rs.getLong("exam_grade"));
           exam.setExamDate(rs.getDate("exam_date"));
           optionalExam = Optional.of(exam) ;
       }
       return optionalExam;
    }
}
