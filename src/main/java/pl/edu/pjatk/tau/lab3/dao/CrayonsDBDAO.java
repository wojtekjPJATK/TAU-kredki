package pl.edu.pjatk.tau.lab3.dao;

import pl.edu.pjatk.tau.lab3.domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class CrayonsDBDAO implements DAO<Crayon> {
    
    public PreparedStatement preparedStatementGetAll, addSt;
    
    Connection connection;
    
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        preparedStatementGetAll = connection.prepareStatement(
                "SELECT id, color FROM Crayon ORDER BY id");
        addSt = connection.prepareStatement("INSERT INTO Crayon (COLOR) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
    }

    @Override
    public List<Crayon> getAllCrayons() {
        try {
            List<Crayon> ret = new LinkedList<>();
            ResultSet result = preparedStatementGetAll.executeQuery();
            while(result.next()) {
                Crayon c = new Crayon();
                c.setId(result.getLong("id"));
                c.setColor(result.getString("color"));
                ret.add(c);
            }
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

	@Override
	public int addCrayon(Crayon crayon) {
        int count = 0;
        try {
            System.out.println(crayon.getColor());
            System.out.println(addSt);
            addSt.setString(1, crayon.getColor());
            count = addSt.executeUpdate();
            ResultSet generatedKeys = addSt.getGeneratedKeys();
            if (generatedKeys.next()) {
                crayon.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            System.out.println("Adding error");
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return count;
	}

}