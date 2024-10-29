package repository;

import data.Database;
import exception.ExamNotFindException;
import modle.Exam;
import modle.dto.SeeStudentRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExamRepositoryImpl implements BaseRepository<Exam> {
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
    private static final String GET_ALL_EXAM_STUDENT = "select * from exams_students";
    private static final String GET_COUNT_OF_EXAM = "SELECT count(*) FROM exam;";
    private static final String SAVE_EXAM = "insert into public.exam (teacher_id,national_code,course_id,exam_grade,exam_date)" +
            "values(?,?,?,?,?)";
    private static final String UPDATE_EXAM = "update public.exam set teacher_id=?,national_code=?,course_id=?,exam_grade=?,exam_date=?" +
            "where exam_id=?";
    private static final String DELETE_EXAM_STUDENT = "delete from exams_students where exam_id = ?;";

    private static final String DELETE_EXAM_BY_ID = "delete from exam where exam_id = ?;";

    private static final String FIND_COURSE_BY_ID = "select * from exam where exam_id=?";
    private static final String ADD_EXAM_TO_STUDENT = "insert into public.exams_students (student_id,national_code,exam_id)" +
            "values(?,?,?)";
    private static final String SET_GRADE_FINALIZED = "update public.exams_students set grade_finalized = true " +
            "where exam_id=?";
    private static final String CHANGE_GRADE_FROM_STUDENT_EXAM_TABLE = "update public.exams_students set student_grade = ? " +
            "where exam_id=? and student_id = ?";
    private static final String FINALIZED_GRADE_CHECK = "select grade_finalized from exams_students " +
            "WHERE exam_id = ? and student_id = ?";
    private static final String STUDENT_SEE_HIS_GRADE = "select student_id ,national_code ,exam_id,student_grade from exams_students" +
            " where student_id = ?";
    private static final String STUDENT_SEE_HIS_AVG = "select AVG(student_grade) from exams_students" +
            " where student_id = ?";

    @Override
    public List<Exam> getAll() throws SQLException {
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

    @Override
    public int getCount() throws SQLException {
        ResultSet examSet = database.getSQLStatementS().executeQuery(GET_COUNT_OF_EXAM);
        int countOfExam = 0;
        while (examSet.next()) {
            countOfExam = examSet.getInt("count");
        }
        return countOfExam;
    }

    @Override
    public void saveOrUpdate(Exam exam) throws SQLException {
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
        ps.setInt(6, exam.getExamId());

        ps.executeUpdate();
    }

    @Override
    public void delete(int examId) throws SQLException, ExamNotFindException {
        if (findById(examId).isPresent()) {
            PreparedStatement ps = conn.prepareStatement(DELETE_EXAM_STUDENT);
            ps.setInt(1, examId);
            ps.executeUpdate();

            ps = conn.prepareStatement(DELETE_EXAM_BY_ID);
            ps.setInt(1, examId);
            ps.executeUpdate();

        } else {
            throw new ExamNotFindException("this exam with this id is not exist");
        }

    }

    @Override
    public Optional<Exam> findById(int examId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(FIND_COURSE_BY_ID);
        ps.setInt(1, examId);
        ResultSet rs = ps.executeQuery();
        Optional<Exam> optionalExam = Optional.empty();
        while (rs.next()) {
            Exam exam = new Exam();
            exam.setExamId(rs.getInt("exam_id"));
            exam.setTeacherId(rs.getInt("teacher_id"));
            exam.setNationalCode(rs.getString("national_code"));
            exam.setCourseId(rs.getInt("course_id"));
            exam.setExamGrade(rs.getLong("exam_grade"));
            exam.setExamDate(rs.getDate("exam_date"));
            optionalExam = Optional.of(exam);
        }
        return optionalExam;
    }

    public void setAddExamToStudent(int studentId, String nationalCode, int examId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(ADD_EXAM_TO_STUDENT);
        ps.setInt(1, studentId);
        ps.setString(2, nationalCode);
        ps.setInt(3, examId);
        ps.executeUpdate();


    }

    public void gradeFinalizedMethod(int examId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(SET_GRADE_FINALIZED);
        ps.setInt(1, examId);
        ps.executeUpdate();
    }

    public void changeGrade(int newGrade, int examId, int studentId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(FINALIZED_GRADE_CHECK);
        ps.setInt(1, examId);
        ps.setInt(2, studentId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Boolean finalized = rs.getBoolean("grade_finalized");
            if (finalized == false) {
                ps = conn.prepareStatement(CHANGE_GRADE_FROM_STUDENT_EXAM_TABLE);
                ps.setInt(1, newGrade);
                ps.setInt(2, examId);
                ps.setInt(3, studentId);
                ps.executeUpdate();
            } else {
                System.out.println("this grade is finalized and can not be change!!");
            }
        }
    }

    public List<SeeStudentRequest> studentSeeHisGrade(int studentId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(STUDENT_SEE_HIS_GRADE);
        ps.setInt(1, studentId);
        ResultSet rs = ps.executeQuery();
        List<SeeStudentRequest> seeStudentRequestList = new ArrayList<>();
        while (rs.next()) {
            SeeStudentRequest seeStudentRequest = new SeeStudentRequest();
            seeStudentRequest.setStudentId(rs.getInt("student_id"));
            seeStudentRequest.setNationalCode(rs.getString("national_code"));
            seeStudentRequest.setExamId(rs.getInt("exam_id"));
            seeStudentRequest.setStudentGrade(rs.getInt("student_grade"));
            seeStudentRequestList.add(seeStudentRequest);
        }
        return seeStudentRequestList;
    }


    public double avgStudentGrade(int studentId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(STUDENT_SEE_HIS_AVG);
        ps.setInt(1, studentId);
        ResultSet rs = ps.executeQuery();
        double avg = 0;
        if (rs.next()) {
            avg = rs.getDouble("avg");
        }
        return avg;
    }

}

