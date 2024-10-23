package service;

import exception.StudentNotFindException;
import exception.UsersNotFindException;
import modle.Course;
import modle.Users;
import modle.dto.SaveCourseRequest;
import modle.dto.SaveUsersRequest;
import repository.UsersRepository;

import java.sql.SQLException;
import java.util.List;

public class UsersServiceImpl {

    private UsersRepository usersRepository = new UsersRepository();

    public void saveAndUpdate(Users users) {
        checkUsers(users);
        try {
            usersRepository.saveOrUpdate(new Users(
                    users.getUserId(),
                    users.getUsername(),
                    users.getUsername(),
                    users.getUserRole()
            ));
        } catch (SQLException e) {
            System.out.println("there is problem with connecting to database");
        }
    }

    public void checkUsers(Users users) {
        if (users.getUsername() == null) {
            throw new IllegalArgumentException("username can not be null please input something there bro");
        }
    }

    public void printAllUsers() {
        try {
            List<Users> usersList = usersRepository.getAll();
            for (Users users : usersList) {
                System.out.println(users);
            }
        } catch (SQLException sqlException) {
            System.out.println("there is problem with connecting to database");
        }
    }

    public void printCountUsers() {
        try {
            int countOfUsers = usersRepository.getCount();
            System.out.println("number of all users : ".concat(String.valueOf(countOfUsers)));
        } catch (SQLException sqlException) {
            System.out.println("there is problem with connecting to database");
        }
    }

    public void delete(Integer id) {
        try {
            usersRepository.delete(id);
            printAllUsers();
        } catch (UsersNotFindException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("there is problem with connecting to database");
        }

    }
}
