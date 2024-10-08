import modle.Student;
import service.StudentServiceImpl;

public class Main {
    public static void main (String[]args){
        StudentServiceImpl studentService = new StudentServiceImpl();
        studentService.printAllStudentList();
        studentService.printCountOfStudent();

    }
}
