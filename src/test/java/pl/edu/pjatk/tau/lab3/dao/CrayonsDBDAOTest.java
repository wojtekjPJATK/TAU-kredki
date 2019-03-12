package pl.edu.pjatk.tau.lab3.dao;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import pl.edu.pjatk.tau.lab3.domain.Crayon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.sql.*;

import java.util.logging.Level;
import java.util.logging.Logger;


@RunWith(JUnit4.class)
public class CrayonsDBDAOTest {
    Logger LOGGER = Logger.getLogger(CrayonsDBDAOTest.class.getCanonicalName());
    public static String url = "jdbc:hsqldb:hsql://localhost/workdb";
    Connection connection;
    Random random;
    List<Crayon> initialDatabaseState;
    CrayonsDBDAO dao;

    @Before
    public void setup() throws SQLException {
        dao = new CrayonsDBDAO();
        connection = DriverManager.getConnection(url);
        random = new Random();
        initialDatabaseState = new LinkedList<>();
        try {
            connection.createStatement()
                    .executeUpdate("CREATE TABLE " +
                            "Crayon(id bigint GENERATED BY DEFAULT AS IDENTITY, "
                            + "color varchar(20) NOT NULL)");

        } catch (SQLException e) {
            LOGGER.log(Level.INFO, e.getMessage());
        }
        PreparedStatement insert = connection.prepareStatement(
                "INSERT INTO Crayon (color) VALUES (?) ", Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < 5;i++) {
            Crayon crayon = new Crayon();
            crayon.setColor("asd"+random.nextInt(10));
            insert.setString(1, crayon.getColor());
            insert.executeUpdate();
            ResultSet keys = insert.getGeneratedKeys();
            if (keys .next()) {
                crayon.setId(keys.getLong(1));
            }
            initialDatabaseState.add(crayon);
        }
        dao.setConnection(connection);
    }

    @Test
    public void setConnectionCheck() throws SQLException {
        assertNotNull(dao.getConnection());
        assertEquals(dao.getConnection(), connection);
    }

    @Test
    public void setConnectionCreatesQueriesCheck() throws SQLException {
        assertNotNull(dao.preparedStatementGetAll);
    }

    @Test
    public void getAllCheck() throws SQLException {
        List<Crayon> result = dao.getAllCrayons();
        assertThat(result.size(), equalTo(initialDatabaseState.size()));
    }

    @Test
    public void getAllWithBadDatabaseCheck() throws SQLException {
        try {
            connection.createStatement()
                    .executeUpdate("DROP TABLE Crayon");

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"Could not drop table " + e.getMessage());
        }
        List<Crayon> retrievedPersons = dao.getAllCrayons();
        assertNull(retrievedPersons);
    }

    @Test
    public void addCrayonCheck() throws SQLException{
        Crayon c = new Crayon();
        c.setColor("Green");
        assertEquals(1, dao.addCrayon(c));
        assertEquals(dao.getAllCrayons().size(), initialDatabaseState.size() + 1);
    }

    @Test
    public void deleteCheck() throws SQLException {
        Crayon c = initialDatabaseState.get(3);
        assertEquals(1, dao.deleteCrayon(c));
        assertEquals(dao.getAllCrayons().size(), initialDatabaseState.size() - 1);
    }

    @Test
    public void updateCheck() throws SQLException {
        Crayon c = initialDatabaseState.get(3);
        String color = new String(c.getColor());
        assertEquals(1, dao.updateCrayon(c));
        List<Crayon> all = new LinkedList<>();
        all = dao.getAllCrayons();
        assertNotEquals(color, all.get(3));
    }

    @Test
    public void getCrayonCheck() throws SQLException {
        Crayon c = initialDatabaseState.get(2);
        assertEquals(c.getColor(), dao.getCrayon(c.getId()).getColor());
    }

    @After
    public void cleanup() throws SQLException{
        Connection connection = DriverManager.getConnection(url);
        try {
            connection.prepareStatement("DELETE FROM Crayon").executeUpdate();
        } catch (Exception e) {
        }
    }
}