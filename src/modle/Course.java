package modle;

import java.util.Objects;

public class Course {
    private Integer courseId;
    private String courseTitle;
    private long courseUnite;

    public Course(String courseTitle, long courseUnite) {
        this.courseTitle = courseTitle;
        this.courseUnite = courseUnite;
    }

    public Course() {
    }

    public Course(Integer courseId, String courseTitle, long courseUnite) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseId == course.courseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseUnite=" + courseUnite +
                '}';
    }
}
