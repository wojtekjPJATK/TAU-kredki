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
    
    public PreparedStatement preparedStatementGetAll, addSt, delSt, updateSt, getSt;
    
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
        delSt = connection.prepareStatement("DELETE FROM Crayon where id = ?");
        updateSt = connection.prepareStatement("UPDATE Crayon SET color=? WHERE id = ?");
        getSt = connection.prepareStatement("SELECT id, color FROM Crayon WHERE id = ?");
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

	@Override
	public int deleteCrayon(Crayon crayon) {
		try {
            delSt.setLong(1, crayon.getId());
            return delSt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
}
	}

	@Override
	public int updateCrayon(Crayon crayon) throws SQLException {
		int count = 0;
        try {
            updateSt.setString(1, crayon.getColor());
            updateSt.setLong(2, crayon.getId());
            count = updateSt.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        if (count <= 0)
            throw new SQLException("crayon not found for update");
        return count;
	}

	@Override
	public Crayon getCrayon(long id) throws SQLException {
		try {
            getSt.setLong(1, id);
            ResultSet rs = getSt.executeQuery();

            System.out.println(rs);

            if (rs.next()) {
                Crayon c = new Crayon();
                c.setId(rs.getInt("id"));
                c.setColor(rs.getString("color"));
                return c;
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        throw new SQLException("Crayon with id " + id + " does not exist");
	}

}