package util;

import exception.UsersNotFindException;
import exception.uniqUsernameException;
import modle.dto.SaveCourseRequest;
import modle.dto.SaveExamRequest;
import modle.dto.SaveStudentRequest;
import modle.dto.SaveTeacherRequest;
import repository.*;
import service.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class menu {
    private final static UsersServiceImpl usersService = new UsersServiceImpl(new UsersRepository());
    private final static CourseServiceImpl courseService = new CourseServiceImpl(new CourseRepositoryImpl());
    private final static TeacherServiceImpl teacherService = new TeacherServiceImpl(new TeacherRepositoryImpl());
    private final static StudentServiceImpl studentService = new StudentServiceImpl(new StudentRepositoryImpl());
    private final static ExamServiceImpl examService = new ExamServiceImpl(new ExamRepositoryImpl());
    static Scanner scan = new Scanner(System.in);
    static boolean start = true;

    public static void mainMenu() {
        while (start) {
            start = logInMenu();
        }

    }

    public static boolean logInMenu() {
        System.out.println("***********welcome**********");
        int choose = utility.getIntInput("1-> login" + "\n2-> exit");
        while (true) {
            if (choose == 2) {
                return false;
            }
            if (choose == 1) {
                System.out.println("\n******* login menu *******\n");
                System.out.println("please enter your username ->");
                String username = scan.nextLine();
                System.out.println("please enter your password ->");
                String password = scan.nextLine();
                try {
                    String permission = usersService.logInUser(username, password);
                    if (permission.equals("admin")) {
                        adminMenu();
                    } else if (permission.equals("student")) {
                        studentMenu(username);
                    } else if (permission.equals("master")) {
                        masterMenu();
                    }
                } catch (UsersNotFindException usersNotFindException) {
                    System.out.println("@ username or password is wrong @" +
                            "\n$try again$");
                }


            }
        }
    }

    private static void masterMenu() {
        while (true) {
            System.out.println("**************master menu**************");
            System.out.println("1 -> create new user");
            System.out.println("2 -> delete user");
            System.out.println("3 -> see count of users and all users information");
            System.out.println("4 -> exit to login page");
            int choose2 = scan.nextInt();
            scan.nextLine();
            if (choose2 == 1) {
                System.out.println("****If you want to create a student user, be careful username must be student id***");
                System.out.println("what is username ");
                String username;
                username = scan.nextLine();
                System.out.println("what is password ");
                String password = scan.nextLine();

                System.out.println("what is user role ");
                String userRole = scan.nextLine();
                try {
                    usersService.createUser(username, password, userRole);
                } catch (uniqUsernameException un) {
                    System.out.println(un.getMessage());
                    masterMenu();
                }
            } else if (choose2 == 4) {
                logInMenu();
            } else if (choose2 == 2) {
                System.out.println("what is the id ?");
                int id = scan.nextInt();
                scan.nextLine();
                usersService.delete(id);
            } else if (choose2 == 3) {
                usersService.printCountUsers();
                usersService.printAllUsers();
            }
        }
    }

    private static void studentMenu(String username) {
        while (true) {
            System.out.println("*************student menu***********");
            System.out.println("1 -> see my grade and avg");
            System.out.println("2 -> exit");
            int choose4 = scan.nextInt();
            scan.nextLine();
            if (choose4 == 2) {
                logInMenu();
            } else if (choose4 == 1) {
                examService.printWhatStudentSee(Integer.parseInt(username));
            }
        }


    }

    private static void adminMenu() {
        while (true) {
            System.out.println("***************admin menu************");
            System.out.println("1 -> create course");
            System.out.println("2 -> update course");
            System.out.println("3 -> delete course");
            System.out.println("4 -> see count and all course information");
            System.out.println("5 -> create teacher");
            System.out.println("6 -> update teacher");
            System.out.println("7 -> delete teacher");
            System.out.println("8 -> see count and all teacher information");
            System.out.println("9 -> create student");
            System.out.println("10 -> update student");
            System.out.println("11 -> delete student");
            System.out.println("12 -> see count and all student information");
            System.out.println("13 -> create exam");
            System.out.println("14 -> update exam");
            System.out.println("15 -> delete exam");
            System.out.println("16 -> see count and all exam information");
            System.out.println("17 -> set teacher for course");
            System.out.println("18 -> add student to student & course table");
            System.out.println("19 -> set and change student grade in student & exam table");
            System.out.println("20 -> set student grade finalized");
            System.out.println("21 -> add new student to exam(in exam & student table)");
            System.out.println("22 -> exit");
            int choose3 = scan.nextInt();
            scan.nextLine();
            if (choose3 == 1) {
                System.out.println("what is course title ");
                String courseTitle = scan.nextLine();
                System.out.println("what is course unite ");
                Long courseUnite = Long.valueOf(scan.nextInt());
                SaveCourseRequest saveCourseRequest = new SaveCourseRequest(courseTitle, courseUnite);
                courseService.saveAndUpdate(saveCourseRequest);

            } else if (choose3 == 2) {
                System.out.println("what is course id ");
                int courseId = scan.nextInt();
                scan.nextLine();
                System.out.println("what is new course title ");
                String courseTitle = scan.nextLine();
                System.out.println("what is new course unite ");
                Long courseUnite = Long.valueOf(scan.nextInt());
                SaveCourseRequest saveCourseRequest = new SaveCourseRequest(courseId, courseTitle, courseUnite);
                courseService.saveAndUpdate(saveCourseRequest);

            } else if (choose3 == 3) {
                System.out.println("what is course id");
                int id = scan.nextInt();
                scan.nextLine();
                courseService.delete(id);

            } else if (choose3 == 4) {
                courseService.printCountCourse();
                courseService.printAllCourse();

            } else if (choose3 == 5) {
                System.out.println("what is national code");
                String nationalCode = scan.nextLine();
                System.out.println("what is firstname");
                String firstname = scan.nextLine();
                System.out.println("what is last name");
                String lastname = scan.nextLine();
                System.out.println("date born Enter a date (dd/mm/yyyy)");
                String dob = scan.nextLine();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date dateDob = sdf.parse(dob);

                    System.out.println("what is course id (it can be null)");
                    Long courseId = scan.nextLong();
                    SaveTeacherRequest saveTeacherRequest = new SaveTeacherRequest(nationalCode, firstname, lastname, dateDob, courseId);
                    teacherService.saveAndUpdate(saveTeacherRequest);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            } else if (choose3 == 6) {
                System.out.println("what is teacher id");
                int teacherId = scan.nextInt();
                scan.nextLine();
                System.out.println("what is national code");
                String nationalCode = scan.nextLine();
                System.out.println("what is new firstname");
                String firstname = scan.nextLine();
                System.out.println("what is new last name");
                String lastname = scan.nextLine();
                System.out.println("new date born Enter a date (dd/mm/yyyy)");
                String dob = scan.nextLine();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date dateDob = sdf.parse(dob);

                    System.out.println("what is new course id (it can be null)");
                    Long courseId = scan.nextLong();
                    SaveTeacherRequest saveTeacherRequest = new SaveTeacherRequest(teacherId, nationalCode, firstname, lastname, dateDob, courseId);
                    teacherService.saveAndUpdate(saveTeacherRequest);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            } else if (choose3 == 7) {
                System.out.println("what is teacher id");
                int id = scan.nextInt();
                scan.nextLine();
                teacherService.delete(id);

            } else if (choose3 == 8) {
                teacherService.PrintCountTeacher();
                teacherService.printAllTeacher();

            } else if (choose3 == 9) {
                System.out.println("what is national code");
                String nationalCode = scan.nextLine();
                System.out.println("what is firstname");
                String firstname = scan.nextLine();
                System.out.println("what is last name");
                String lastname = scan.nextLine();
                System.out.println("date born Enter a date (dd/mm/yyyy)");
                String dob = scan.nextLine();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date dateDob = sdf.parse(dob);
                    System.out.println("what is student gpu");
                    double gpu = scan.nextDouble();
                    SaveStudentRequest saveStudentRequest = new SaveStudentRequest(nationalCode, firstname, lastname, dateDob, gpu);
                    studentService.saveAndUpdate(saveStudentRequest);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            } else if (choose3 == 10) {
                System.out.println("what is student id");
                int id = scan.nextInt();
                scan.nextLine();
                System.out.println("what is new national code");
                String nationalCode = scan.nextLine();
                System.out.println("what is new firstname");
                String firstname = scan.nextLine();
                System.out.println("what is new last name");
                String lastname = scan.nextLine();
                System.out.println("new date born Enter a date (dd/mm/yyyy)");
                String dob = scan.nextLine();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date dateDob = sdf.parse(dob);
                    System.out.println("what is new student gpu");
                    double gpu = scan.nextDouble();
                    SaveStudentRequest saveStudentRequest = new SaveStudentRequest(id, nationalCode, firstname, lastname, dateDob, gpu);
                    studentService.saveAndUpdate(saveStudentRequest);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            } else if (choose3 == 11) {
                System.out.println("what is student id");
                int id = scan.nextInt();
                scan.nextLine();
                studentService.delete(id);
            } else if (choose3 == 12) {
                studentService.printCountOfStudent();
                studentService.printAllStudentList();

            } else if (choose3 == 13) {
                System.out.println("what is teacher id");
                int teacherId = scan.nextInt();
                scan.nextLine();
                System.out.println("what is teacher national code");
                String nationalCode = scan.nextLine();
                System.out.println("what is course id");
                int courseId = scan.nextInt();
                scan.nextLine();
                System.out.println("what is grade");
                Long gpu = scan.nextLong();
                scan.nextLine();

                try {
                    System.out.println(" exam date Enter a date (dd/mm/yyyy)");
                    String dob = scan.nextLine();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date dateDob = sdf.parse(dob);
                    SaveExamRequest saveExamRequest = new SaveExamRequest(teacherId, nationalCode, courseId, gpu, dateDob);
                    examService.saveAndUpdate(saveExamRequest);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else if (choose3 == 14) {
                System.out.println("what is exam id ");
                int examId = scan.nextInt();
                scan.nextLine();
                System.out.println("what is teacher id");
                int teacherId = scan.nextInt();
                scan.nextLine();
                System.out.println("what is teacher national code");
                String nationalCode = scan.nextLine();
                System.out.println("what is course id");
                int courseId = scan.nextInt();
                scan.nextLine();
                System.out.println("what is grade");
                Long gpu = scan.nextLong();
                scan.nextLine();

                try {
                    System.out.println(" exam date Enter a date (dd/mm/yyyy)");
                    String dob = scan.nextLine();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date dateDob = sdf.parse(dob);
                    SaveExamRequest saveExamRequest = new SaveExamRequest(examId, teacherId, nationalCode, courseId, gpu, dateDob);
                    examService.saveAndUpdate(saveExamRequest);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            } else if (choose3 == 15) {
                System.out.println("what is exam id");
                int id = scan.nextInt();
                scan.nextLine();
                examService.delete(id);
            } else if (choose3 == 16) {
                examService.PrintCountOfExam();
                examService.printAllExam();

            } else if (choose3 == 17) {
                System.out.println("what is course id");
                int courseId = scan.nextInt();
                scan.nextLine();
                System.out.println("what is teacher id");
                int teacherId = scan.nextInt();
                scan.nextLine();
                teacherService.setTeacherForCourse(courseId, teacherId);
            } else if (choose3 == 18) {
                System.out.println("what is course id");
                int courseId = scan.nextInt();
                scan.nextLine();
                System.out.println("what is student id");
                int studentId = scan.nextInt();
                scan.nextLine();
                System.out.println("what is student national code");
                String nationalCode = scan.nextLine();
                courseService.addStudentsToCourse(courseId,
                        studentId, nationalCode);
            } else if (choose3 == 19) {
                System.out.println("what is grade");
                int grade = scan.nextInt();
                scan.nextLine();
                System.out.println("what is exam id");
                int examId = scan.nextInt();
                scan.nextLine();
                System.out.println("what is student id");
                int studentId = scan.nextInt();
                scan.nextLine();
                examService.changeGrade(grade, examId, studentId);

            } else if (choose3 == 20) {
                System.out.println("which exam you want to finalizad ");
                int examId = scan.nextInt();
                scan.nextLine();
                System.out.println("and which student");
                int studentId = scan.nextInt();
                scan.nextLine();
                examService.GradeFinalized(examId, studentId);

            } else if (choose3 == 21) {
                System.out.println("what is student id");
                int id = scan.nextInt();
                scan.nextLine();
                System.out.println("what is student national code");
                String nationalCode = scan.nextLine();
                System.out.println("what is exam id");
                int examId = scan.nextInt();
                scan.nextLine();
                examService.addExamToStudent(id, nationalCode , examId);
            } else if (choose3 == 22) {
                logInMenu();
            }

        }
    }
}
