package pl.edu.pjatk.tau.lab3.dao;

import pl.edu.pjatk.tau.lab3.domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CrayonsDBDAO implements DAO<Crayon> {
    
    public PreparedStatement preparedStatementGetAll;
    
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

}