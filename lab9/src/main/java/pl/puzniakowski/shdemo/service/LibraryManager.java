package pl.puzniakowski.shdemo.service;

import java.util.List;

import pl.puzniakowski.shdemo.domain.Book;
import pl.puzniakowski.shdemo.domain.Person;

public interface LibraryManager {
	
	Long addClient(Person person);
	List<Person> findAllClients();
	Person findClientById(Long id);
	void deleteClient(Person person);
	void updateClient(Person person);

	Long addBook(Book book);
	void updateBook(Book book);
	Book findBookById(Long id);
	void deleteBook(Book book);
	List<Book>  findAllBooks();
	List<Book> findAvailableBooks();
	List<Book> findBooksByAuthor(String authorNameFragment);
}
