package pl.puzniakowski.shdemo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.puzniakowski.shdemo.domain.Book;
import pl.puzniakowski.shdemo.domain.Person;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
//@ComponentScan({"pl.puzniakowski"})
@PropertySource("classpath:jdbc.properties")
@ContextConfiguration(locations = { "classpath:/beans-tests.xml" })
//@Rollback
@Commit
@Transactional ("txManager")
public class LibraryManagerTest {

	@Autowired
    LibraryManager libraryManager;

	List<Long> bookIds;
	List<Long> clientIds;

	/**
	 * Helper method allowing for easier adding books to tests
	 * @param name
	 * @param author
	 * @return
	 */
	private Book addBookHelper(String name, String author) {
		Long bookId;
		Book book;
		book = new Book();
		book.setTitle(name);
		book.setAuthor(author);
		book.setId(null);
		bookIds.add(bookId = libraryManager.addBook(book));
		assertNotNull(bookId);
		return book;
	}

	/**
	 * Helper function for fast adding clients
	 * @param name
	 * @param boorowedBooks
	 * @return
	 */
	private Person addClientHelper(String name, List<Book> boorowedBooks) {
		Long personId;
		Person person;
		person = new Person();
		person.setFirstName(name);
		person.setBooks(boorowedBooks);
		person.setId(null);
		clientIds.add(personId = libraryManager.addClient(person));
		assertNotNull(personId);
		return person;
	}


	@Before
	public void setup() {
		bookIds = new LinkedList<>();
		clientIds = new LinkedList<>();

		// first books
		addBookHelper("Using OpenCL","Janusz Kowalik, Tadeusz Puźniakowski");
		addBookHelper("W pustyni i w puszczy","Henryk Sienkiewicz");
		Book book = addBookHelper("Zbrodnia i kara","Fiodor Dostojewski");

		// then clients
		addClientHelper("Janusz",new LinkedList<Book>());
		ArrayList<Book> books = new ArrayList<Book>();
		books.add(book);
		addClientHelper("Grazyna",books);
	}

	@Test
	public void addBookTest() {
		assertTrue(bookIds.size() > 0);
	}

	@Test
	public void addClientTest() {
		assertTrue(clientIds.size() > 0);
	}

	@Test
	public void getAllBooksTest() {
		List <Long> foundIds = new LinkedList<>();
		/**
		 * are the found books the same as the added during setup phase?
		 */
		for (Book book: libraryManager.findAllBooks()) {
			if (bookIds.contains(book.getId())) foundIds.add(book.getId());
		}
		assertEquals(bookIds.size(), foundIds.size());
	}

	@Test
	public void getAllClientsTest() {
		List <Long> foundIds = new LinkedList<>();
		for (Person client: libraryManager.findAllClients()) {
			if (clientIds.contains(client.getId())) foundIds.add(client.getId());
		}
		assertEquals(clientIds.size(), foundIds.size());
	}


	@Test
	public void deleteBookNotBorrowedTest() {
		int prevSize = libraryManager.findAllBooks().size();
		Book book = libraryManager.findBookById(bookIds.get(0));
		assertNotNull(book);
		libraryManager.deleteBook(book);
		assertNull(libraryManager.findBookById(bookIds.get(0)));
		assertEquals(prevSize-1,libraryManager.findAllBooks().size());
	}

	@Test
	public void getClientWithBooksTest() {
		Person person = libraryManager.findClientById(clientIds.get(1));
		assertEquals(1, person.getBooks().size());
	}


	@Test
	public void findByBookAuthorTest() {
		List<Book> books = libraryManager.findBooksByAuthor("iodor");
		assertEquals("Fiodor Dostojewski", books.get(0).getAuthor());
	}
}
