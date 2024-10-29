package service;

import exception.StudentNotFindException;
import exception.UsersNotFindException;
import exception.uniqUsernameException;
import modle.Course;
import modle.Users;
import modle.dto.SaveCourseRequest;
import modle.dto.SaveUsersRequest;
import repository.UsersRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsersServiceImpl {

    private UsersRepository usersRepository = new UsersRepository();

    public UsersServiceImpl(UsersRepository usersRepository) {
    }

    public void createUser(String username, String password, String userRole) throws uniqUsernameException {
        try {
            Users user = new Users();
            user.setUsername(username);
            user.setPassword(password);
            user.setUserRole(userRole);
            usersRepository.saveUsers(user);
        } catch (SQLException e) {
            System.out.println("there is problem with connecting to database");
        } catch (uniqUsernameException uniq) {
            System.out.println(uniq.getMessage());
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

        }

    }

    public String logInUser(String username, String password) throws UsersNotFindException {

        try {
            Optional<Users> foundUser = usersRepository.findByUsernameAndPass(username, password);
            if (!foundUser.isPresent())
                throw new UsersNotFindException("username or password is wrong");

            return foundUser.get().getUserRole();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
