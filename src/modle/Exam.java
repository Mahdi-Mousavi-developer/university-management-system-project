package modle;

import java.util.Date;
import java.util.Objects;

public class Exam {
    private long examId;
    private long teacherId;
    private String nationalCode;
    private long courseId;
    private long examGrade;
    private Date examDate;

    public Exam() {
    }

    public Exam(long examId, long teacherId, String nationalCode, long courseId, long examGrade, Date examDate) {
        this.examId = examId;
        this.teacherId = teacherId;
        this.nationalCode = nationalCode;
        this.courseId = courseId;
        this.examGrade = examGrade;
        this.examDate = examDate;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public long getExamGrade() {
        return examGrade;
    }

    public void setExamGrade(long examGrade) {
        this.examGrade = examGrade;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "examId=" + examId +
                ", teacherId=" + teacherId +
                ", nationalCode='" + nationalCode + '\'' +
                ", courseId=" + courseId +
                ", examGrade=" + examGrade +
                ", examDate=" + examDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return examId == exam.examId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(examId);
    }
}
