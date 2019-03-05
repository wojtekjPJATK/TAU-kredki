package pl.edu.pjatk.tau.lab2.dao;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import pl.edu.pjatk.tau.lab2.domain.Crayon;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@RunWith(BlockJUnit4ClassRunner.class)
public class CrayonsInMemoryTest {

    CrayonsInMemoryDAO dao;

    @Before
    public void setup() {
        Crayon c1 = new Crayon();
        Crayon c2 = new Crayon();
        c1.setId(1L);
        c1.setColor("Red");
        c2.setId(2L);
        c2.setColor("Blue");
        this.dao = new CrayonsInMemoryDAO();
        dao.crayons = new HashMap<Long, Crayon>();
        dao.crayons.put(1L,c1);
        dao.crayons.put(2L,c2);
    }

    @Test
    public void createDaoObjectTest() {
        assertNotNull(this.dao);
    }

    @Test
    public void getCrayon() {
        Optional<Crayon> p = dao.get(2L);
        assertThat(p.get().getColor(), is("Blue"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNotExisitingCrayonShouldThrowTest() {
        Crayon c1 = new Crayon();
        c1.setId(9);
        c1.setColor("Xyz");
        dao.update(c1);
    }

    @Test
    public  void updateOneRecordTest() {
        Crayon c1 = new Crayon();
        c1.setId(1);
        c1.setColor("Red");
        Crayon c2 = new Crayon();
        c2.setId(2);
        c2.setColor("Yellow");

        Collection<Crayon> listExpected = dao.crayons.values();
        for (Crayon p:listExpected) if (p.getId()==1) p.setColor("Red");

        dao.update(c1);

        Collection<Crayon> listAfter = dao.crayons.values();
        assertArrayEquals(listExpected.toArray(), listAfter.toArray());
    }

    @Test
    public void deleteOneRecordTest() {
        Crayon c1 = new Crayon();
        c1.setColor("Red");
        c1.setId(1L);
        dao.delete(c1);
        assertEquals(1, dao.crayons.values().size());
        assertThat(dao.crayons.values(), not(hasItem(c1)));
    }

    @Test
    public void saveOneRecordTest() {
        Crayon c = new Crayon();
        c.setColor("Yellow");
        c.setId(3L);
        dao.save(c);
        assertEquals(3, dao.crayons.values().size());
        assertThat(dao.crayons.values(), hasItem(c));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveExistsingRecordTest() {
        Crayon c = new Crayon();
        c.setId(1L);
        c.setColor("Red");
        dao.save(c);
        assertEquals(2, dao.crayons.values().size());
    }

    @Test
    public void getAllItemsTest() {
        List<Crayon> list = dao.getAll();
        assertEquals(2, list.size());

        Crayon c = new Crayon();
        c.setColor("Yellow");
        c.setId(3L);
        dao.save(c);

        list = dao.getAll();
        assertEquals(3, list.size());
        assertThat(list, hasItem(c));
    }

}