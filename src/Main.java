import modle.Student;
import service.CourseServiceImpl;
import service.ExamServiceImpl;
import service.StudentServiceImpl;
import service.TeacherServiceImpl;

public class Main {
    public static void main (String[]args){
   /*     ExamServiceImpl examService = new ExamServiceImpl();
        examService.printAllExam();
        examService.PrintCountOfExam();
        CourseServiceImpl courseService = new CourseServiceImpl();
        courseService.printAllCourse();
        courseService.printCountCourse();*/
        StudentServiceImpl student = new StudentServiceImpl();
        student.printCountOfStudent();
        student.printAllStudentList();

    }
}
