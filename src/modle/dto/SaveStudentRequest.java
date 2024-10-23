package modle.dto;

import java.util.Date;

public class SaveStudentRequest {


    private Integer studentId;
    private String nationalCode;
    private String firstName;
    private String lastName;
    private Date dob;
    private double gpu;

    public SaveStudentRequest(String nationalCode, String firstName, String lastName, Date dob, double gpu) {
        this.nationalCode = nationalCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gpu = gpu;
    }

    public SaveStudentRequest(Integer studentId, String nationalCode, String firstName, String lastName, Date dob, double gpu) {
        this.studentId = studentId;
        this.nationalCode = nationalCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gpu = gpu;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public double getGpu() {
        return gpu;
    }

    public void setGpu(double gpu) {
        this.gpu = gpu;
    }
}
