package pl.edu.pjatk.tau.lab2.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    Optional<T> get(Long id);
    List<T> getAll();
    void save(T o);
    void update(T o) throws IllegalArgumentException;
    void delete(T o);
}