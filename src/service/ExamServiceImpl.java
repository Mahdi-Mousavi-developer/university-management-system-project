package service;

import exception.ExamNotFindException;
import modle.Exam;
import modle.dto.SaveExamRequest;
import modle.dto.SeeStudentRequest;
import repository.ExamRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class ExamServiceImpl {
    private ExamRepositoryImpl examRepository = new ExamRepositoryImpl();

    public ExamServiceImpl(ExamRepositoryImpl examRepository) {
        this.examRepository = examRepository;
    }

    public void printAllExam() {
        try {
            List<Exam> examList = examRepository.getAll();
            for (Exam exam : examList) {
                System.out.println(exam);
            }


        } catch (SQLException sqlException) {
            System.out.println("there is problem with connecting to database");
        }
    }

    public void PrintCountOfExam() {
        try {
            int countOfExam = examRepository.getCount();
            System.out.println("number of all exam : ".concat(String.valueOf(countOfExam)));
        } catch (SQLException sqlException) {
            System.out.println("there is problem with connecting to database");
        }
    }

    public void delete(Integer id) {
        checkIdExam(id);
        try {
            examRepository.delete(id);
            printAllExam();
        } catch (ExamNotFindException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("there is problem with connecting to database");
        }
    }

    public void checkIdExam(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("exam id can not be null");
        }
    }

    public void saveAndUpdate(SaveExamRequest request) {
        checkExam(request);
        try {
            examRepository.saveOrUpdate(new Exam(
                    request.getExamId(),
                    request.getTeacherId(),
                    request.getNationalCode(),
                    request.getCourseId(),
                    request.getExamGrade(),
                    request.getExamDate()

            ));
        } catch (SQLException e) {
            System.out.println("there is problem with connecting to database");
        }

    }

    public void addExamToStudent(int studentId, String nationalCode, int examId) {
        try {
            examRepository.setAddExamToStudent(studentId, nationalCode, examId);
        } catch (SQLException sqlException) {
            System.out.println("there is problem with connecting to database");
        }
    }

    public void checkExam(SaveExamRequest request) {

        if (request.getNationalCode() == null) {
            throw new IllegalArgumentException("national code can not be null please input something there bro");

        }
        if (request.getExamGrade() < 0.0 || request.getExamGrade() > 100.0) {
            throw new IllegalArgumentException("put a number between 0 & 100 in this field");

        }
    }

    public void GradeFinalized(int examId, int studentId) {
        try {
            examRepository.gradeFinalizedMethod(examId);
        } catch (SQLException e) {
            System.out.println("there is problem with connecting to database");
        }
    }

    public void changeGrade(int newGrade, int examId, int studentId) {
        try {
            examRepository.changeGrade(newGrade, examId, studentId);
        } catch (SQLException e) {
            System.out.println("there is problem with connecting to database");
        }
    }

    public void studentSeeHisGrade(int studentId) {
        try {
            List<SeeStudentRequest> seeStudentRequestList = examRepository.studentSeeHisGrade(studentId);
            for (SeeStudentRequest see : seeStudentRequestList) {
                System.out.println(see);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void printWhatStudentSee(int studentId) {
        try {
            double avg = examRepository.avgStudentGrade(studentId);
            System.out.println("your avg = " + avg);
            studentSeeHisGrade(studentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}



