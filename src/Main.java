import modle.Student;
import modle.Users;
import service.*;

import static java.awt.SystemColor.menu;

public class Main {
    public static void main (String[]args){
   /* UsersServiceImpl usersService =new UsersServiceImpl();

        usersService.logInUser("mmd","al");
        examService.printAllExam();
        examService.PrintCountOfExam();
        StudentServiceImpl student = new StudentServiceImpl();
        student.printCountOfStudent();
        student.printAllStudentList();;
        TeacherServiceImpl teacherService = new TeacherServiceImpl();
        teacherService.printAllTeacher();
        teacherService.delete(6);
        TeacherServiceImpl teacherService = new TeacherServiceImpl();
        ExamServiceImpl examService = new ExamServiceImpl();
        examService.delete(2);
        CourseServiceImpl courseService = new CourseServiceImpl();
        courseService.printAllCourse();*/
        util.menu.mainMenu();
    }
}
