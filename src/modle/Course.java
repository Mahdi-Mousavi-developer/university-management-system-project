package modle;

import java.util.Objects;

public class Course {
    private long courseId;
    private String courseTitle;
    private long courseUnite;

    public Course() {
    }

    public Course(long courseId, String courseTitle, long courseUnite) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseUnite = courseUnite;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
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
