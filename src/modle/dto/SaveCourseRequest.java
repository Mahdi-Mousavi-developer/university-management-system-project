package modle.dto;

public class SaveCourseRequest {
    private Integer courseId;
    private String courseTitle;
    private long courseUnite;

    public SaveCourseRequest(String courseTitle, long courseUnite) {
        this.courseTitle = courseTitle;
        this.courseUnite = courseUnite;
    }

    public SaveCourseRequest(Integer courseId, String courseTitle, long courseUnite) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseUnite = courseUnite;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public long getCourseUnite() {
        return courseUnite;
    }

    public void setCourseUnite(long courseUnite) {
        this.courseUnite = courseUnite;
    }
}
