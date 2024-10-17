package modle;

import java.util.Date;
import java.util.Objects;

public class Exam {
    private Integer examId;
    private Integer teacherId;
    private String nationalCode;
    private Integer courseId;
    private long examGrade;
    private Date examDate;


    public Exam() {
    }

    public Exam(Integer examId, Integer teacherId, String nationalCode, Integer courseId, long examGrade, Date examDate) {
        this.examId = examId;
        this.teacherId = teacherId;
        this.nationalCode = nationalCode;
        this.courseId = courseId;
        this.examGrade = examGrade;
        this.examDate = examDate;
    }


    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
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
