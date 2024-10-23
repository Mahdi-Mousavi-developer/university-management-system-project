package modle.dto;

import java.util.Objects;

public class SeeStudentRequest {
    private Integer studentId;
    private String nationalCode;
    private Integer examId;
    private Integer studentGrade;

    public SeeStudentRequest() {
    }

    public SeeStudentRequest(Integer studentId, String nationalCode, Integer examId, Integer studentGrade) {
        this.studentId = studentId;
        this.nationalCode = nationalCode;
        this.examId = examId;
        this.studentGrade = studentGrade;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public Integer getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(Integer studentGrade) {
        this.studentGrade = studentGrade;
    }

    @Override
    public String toString() {
        return "seeStudentRequest{" +
                "studentId= " + studentId +
                ", nationalCode= " + nationalCode + '\'' +
                ", examId= " + examId +
                ", studentGrade= " + studentGrade +
                '}';
    }


}
