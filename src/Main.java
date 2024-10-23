import modle.Student;
import service.CourseServiceImpl;
import service.ExamServiceImpl;
import service.StudentServiceImpl;
import service.TeacherServiceImpl;

public class Main {
    public static void main (String[]args){
   /*
        examService.printAllExam();
        examService.PrintCountOfExam();
        StudentServiceImpl student = new StudentServiceImpl();
        student.printCountOfStudent();
        student.printAllStudentList();;
        TeacherServiceImpl teacherService = new TeacherServiceImpl();
        teacherService.printAllTeacher();
        teacherService.delete(6);
        TeacherServiceImpl teacherService = new TeacherServiceImpl();

        examService.delete(2);

        CourseServiceImpl courseService = new CourseServiceImpl();
        courseService.printAllCourse();*/
        ExamServiceImpl examService = new ExamServiceImpl();
        examService.printWhatStudentSee(2);
    }
}
