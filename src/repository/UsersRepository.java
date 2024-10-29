package repository;

import data.Database;
import exception.UsersNotFindException;
import exception.uniqUsernameException;
import modle.Course;
import modle.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepository implements BaseRepository<Users> {
    Database database = new Database();
    Users users = new Users();
    private static Connection conn;

    static {
        try {
            conn = Database.getConnectionStatic();
        } catch (SQLException sqlException) {
            System.out.println("there is problem with connecting to database");
        }
    }

    private static final String SAVE_USER = "insert into public.userss (username , password , user_role)" +
            "values(?,?,?)";
    private static final String UPDATE_USER = "update public.userss set username=? , password=?,user_role =?" +
            " where id = ?";
    private static final String GET_ALL_Users_QUERY = "select * from userss";
    private static final String DELETE_USER_BY_ID = "delete from userss where id = ?;";
    private static final String FIND_USER_BY_USERNAME_AND_PASSWORD = "select * from userss where username =? and password= ?";

    private static final String COUNT_OF_USER_BY_USERNAME = "SELECT count(*) from userss where username = ?";
    private static final String FIND_USER_BY_ID = "select * from userss where id= ?;";
    private static final String GET_COUNT = "select count(*) from userss";

    @Override
    public void saveOrUpdate(Users users) throws SQLException {
        if (users.getUserId() == null) {
            try {
                saveUsers(users);
            } catch (uniqUsernameException e) {
                System.out.println(e.getMessage());
            }
        } else {
            mergeUsers(users);
        }
    }


    public void saveUsers(Users users) throws SQLException, uniqUsernameException {
        int count = countUserForChecking(users.getUsername());
        if (count == 0) {
            PreparedStatement ps = conn.prepareStatement(SAVE_USER);
            ps.setString(1, users.getUsername());
            ps.setString(2, users.getPassword());
            ps.setString(3, users.getUserRole());
            ps.executeUpdate();
        } else if (count > 0) {
            throw new uniqUsernameException("user already exit");
        }

    }

    public void mergeUsers(Users users) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(UPDATE_USER);
        ps.setString(1, users.getUsername());
        ps.setString(2, users.getPassword());
        ps.setString(3, users.getUserRole());
        ps.executeUpdate();
    }

    @Override
    public List<Users> getAll() throws SQLException {
        ResultSet rs = database.getSQLStatementS().executeQuery(GET_ALL_Users_QUERY);
        List<Users> usersList = new ArrayList<>();
        while (rs.next()) {
            Users users = new Users(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("user_role")
            );
            usersList.add(users);
        }
        return usersList;
    }

    @Override
    public int getCount() throws SQLException {

        ResultSet rs = database.getSQLStatementS().executeQuery(GET_COUNT);
        int countOfUsers = 0;
        while (rs.next()) {
            countOfUsers = rs.getInt("count");
        }
        return countOfUsers;

    }

    @Override
    public void delete(int id) throws SQLException, UsersNotFindException {
        if (this.findById(id).isPresent()) {
            PreparedStatement ps = conn.prepareStatement(DELETE_USER_BY_ID);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Optional<Users> findById(int id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(FIND_USER_BY_ID);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Optional<Users> optionalUsers = Optional.empty();
        while (rs.next()) {
            Users user = new Users();
            user.setUserId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setUserRole(rs.getString("user_role"));
            optionalUsers = Optional.of(user);
        }
        return optionalUsers;
    }

    public Optional<Users> findByUsernameAndPass(String username, String password) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(FIND_USER_BY_USERNAME_AND_PASSWORD);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        Optional<Users> foundUser = Optional.empty();
        if (rs.next()) {
            Users users1 = new Users();
            users1.setUserId(rs.getInt("id"));
            users1.setUsername(rs.getString("username"));
            users1.setPassword(rs.getString("password"));
            users1.setUserRole(rs.getString("user_role"));
            foundUser = Optional.of(users1);
        }

        return foundUser;
    }

    public int countUserForChecking(String username) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(COUNT_OF_USER_BY_USERNAME);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        int count = 0;
        if (rs.next()) {
            count = rs.getInt("count");
        }
        return count;
    }
}
