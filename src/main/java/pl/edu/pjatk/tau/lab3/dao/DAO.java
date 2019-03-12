package pl.edu.pjatk.tau.lab3.dao;

import java.util.List;
import java.util.Optional;

import pl.edu.pjatk.tau.lab3.domain.Crayon;

import java.sql.Connection;
import java.sql.SQLException;

public interface DAO<T> {
    public Connection getConnection();
	public void setConnection(Connection connection) throws SQLException;
	public List<Crayon> getAllCrayons();
	public int addCrayon(Crayon crayon);
	public int deleteCrayon(Crayon crayon);
	// public int updateCrayon(Crayon crayon) throws SQLException;
    // public Crayon getCrayon(long id) throws SQLException;
}