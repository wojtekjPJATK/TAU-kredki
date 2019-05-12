package pl.tau.lab9wj.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.CoreMatchers.*;

import static org.mockito.Mockito.*;

import pl.tau.lab9wj.domain.Crayon;
import pl.tau.lab9wj.domain.MyDate;
import pl.tau.lab9wj.domain.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@Rollback
//@Commit
@Transactional(transactionManager = "txManager")
public class CrayonFactoryTest {

	@Autowired
    CrayonFactory crayonFactory;

	List<Long> crayonIds;
	List<Long> workerIds;

	Person janusz, zdzisiek;

	@Mock
	MyDate date;

	private void addCrayonHelper(String color, Person creator) {
		Long crayonId;
		Crayon c = new Crayon();
		c.setColor(color);
		c.setCreator(creator);
		c.setId(null);
		crayonIds.add(crayonId = crayonFactory.addCrayon(c));
		assertNotNull(crayonId);
		return;
	}

	private Person addWorkerHelper(String name) {
		Long personId;
		Person person;
		person = new Person();
		person.setName(name);
		person.setId(null);
		workerIds.add(personId = crayonFactory.addWorker(person));
		assertNotNull(personId);
		return person;
	}


	@Before
	public void setup() {
		crayonIds = new LinkedList<>();
		workerIds = new LinkedList<>();
		janusz = addWorkerHelper("Janusz");
		zdzisiek = addWorkerHelper("Zdzisiek");

		addCrayonHelper("Red", janusz);
		addCrayonHelper("Blue", zdzisiek);
		addCrayonHelper("Green", janusz);
		addCrayonHelper("Red", null);
	}

	@Test
	public void addCrayonTest() {
		MyDate date = mock(MyDate.class);
		when(date.getCurrentTime()).thenReturn(new Long(43));
		assertTrue(crayonIds.size() > 0);
		Crayon c = crayonFactory.findAllCrayons().get(0);
		assertEquals((new Date(43L)).toString(), c.getTimestamp().toString());
	}

	@Test
	public void addWorkerTest() {
		assertTrue(workerIds.size() > 0);
	}

	@Test
	public void getAllCrayonsTest() {
		List <Long> foundIds = new LinkedList<>();
		for (Crayon c: crayonFactory.findAllCrayons()) {
			if (crayonIds.contains(c.getId())) foundIds.add(c.getId());
		}
		assertEquals(crayonIds.size(), foundIds.size());
	}

	@Test
	public void findWorkerByNameTest() {
		Person person = (Person) crayonFactory.findWorker("nusz");
		assertEquals("Janusz", person.getName());
	}

	@Test
	public void findCrayonsByColorTest() {
		List<Crayon> results = crayonFactory.findCrayonsByColor("Red");
		for(Crayon c : results) {
			assertEquals("Red", c.getColor());
		}
	}

	@Test
	public void deleteCrayonTest() {
		int prevSize = crayonFactory.findAllCrayons().size();
		Crayon c = crayonFactory.findAllCrayons().get(0);
		assertNotNull(c);
		crayonFactory.deleteCrayon(c);
		assertEquals(prevSize-1,crayonFactory.findAllCrayons().size());
	}

	@Test
	public void updateCrayonTest() {
		int size = crayonFactory.findCrayonsByColor("New").size();
		Crayon c = crayonFactory.findAllCrayons().get(0);
		assertNotNull(c);
		c.setColor("New");
		crayonFactory.updateCrayon(c);
		assertEquals(size+1,crayonFactory.findCrayonsByColor("New").size());
	}

	@Test
	public void findCrayonsByCreatorTest() {
		List<Crayon> crayons = crayonFactory.findCrayonsByCreator(janusz);
		for(Crayon crayon: crayons) assertEquals("Janusz", crayon.getCreator().getName());
	}

	@Test
	public void transferCrayonToAnotherCreatorTest() {
		List<Crayon> januszCrayons = crayonFactory.findCrayonsByCreator(janusz);
		int januszCrayonsInitial = januszCrayons.size();
		Crayon c = januszCrayons.get(0);
		assertEquals(true, crayonFactory.transferCrayonToAnotherCreator(c, zdzisiek));
		assertTrue(januszCrayonsInitial > crayonFactory.findCrayonsByCreator(janusz).size());
	}

	@Test(expected=IllegalArgumentException.class)
	public void transferCrayonToSamePersonThrowsAnExceptionTest() {
		List<Crayon> januszCrayons = crayonFactory.findCrayonsByCreator(janusz);
		int januszCrayonsInitial = januszCrayons.size();
		Crayon c = januszCrayons.get(0);
		crayonFactory.transferCrayonToAnotherCreator(c, janusz);
	}

}
