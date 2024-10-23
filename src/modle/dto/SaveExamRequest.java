package modle.dto;

import java.util.Date;

public class SaveExamRequest {
    private Integer examId;
    private Integer teacherId;
    private String nationalCode;
    private Integer courseId;
    private long examGrade;
    private Date examDate;

    public SaveExamRequest(Integer teacherId, String nationalCode, Integer courseId, long examGrade, Date examDate) {
        this.teacherId = teacherId;
        this.nationalCode = nationalCode;
        this.courseId = courseId;
        this.examGrade = examGrade;
        this.examDate = examDate;
    }

    public SaveExamRequest(Integer examId, Integer teacherId, String nationalCode, Integer courseId, long examGrade, Date examDate) {
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

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
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
}
