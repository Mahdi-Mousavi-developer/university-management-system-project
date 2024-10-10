package modle.dto;

import java.util.Date;

public class SaveTeacherRequest {
    private Integer teacherId;
    private String nationalCode;
    private String firstName;
    private String lastName;
    private Date dob;
    private long courseId;

    public SaveTeacherRequest(Integer teacherId, String nationalCode, String firstName, String lastName, Date dob, long courseId) {
        this.teacherId = teacherId;
        this.nationalCode = nationalCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.courseId = courseId;
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

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }
}
