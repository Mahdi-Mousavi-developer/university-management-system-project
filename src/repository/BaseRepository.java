package repository;

import modle.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    int getCount() throws SQLException;
    List<T> getAll() throws SQLException;
    void saveOrUpdate(T t) throws SQLException;
    void delete(int id) throws SQLException,Exception;
    Optional<T> findById(int id) throws  SQLException;

}
