package pl.puzniakowski.shdemo.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.puzniakowski.shdemo.domain.Book;
import pl.puzniakowski.shdemo.domain.Person;

@Component
@Transactional
public class LibraryMangerHibernateImpl implements LibraryManager {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public Long addClient(Person person) {
		// Pierwsza wersja dodawania do bazy danych - korzystamy z persist - to jest dostepne w JPA
		if (person.getId() != null) throw new IllegalArgumentException("the person ID should be null if added to database");
		sessionFactory.getCurrentSession().persist(person);
		for (Book book : person.getBooks()) {
			book.setPerson(person);
			sessionFactory.getCurrentSession().update(book);
		}
		sessionFactory.getCurrentSession().flush();
		return person.getId();
	}

	@Override
	public List<Person> findAllClients() {
		return sessionFactory.getCurrentSession().getNamedQuery("person.all")
				.list();
	}

	@Override
	public Person findClientById(Long id) {
		return (Person) sessionFactory.getCurrentSession().get(Person.class, id);
	}

	@Override
    public void updateClient(Person person) {
        sessionFactory.getCurrentSession().update(person);
    }

	@Override
	public Long addBook(Book book) {
		// Inna wersja dodawania do bazy danych - korzystamy z save - to nie dostepne w JPA
		return (Long) sessionFactory.getCurrentSession().save(book);
	}

	@Override
	public void updateBook(Book book) {

	}

	@Override
	public Book findBookById(Long id) {
		return (Book) sessionFactory.getCurrentSession().get(Book.class, id);
	}

	@Override
	public void deleteBook(Book book) {
        if (book.getPerson() != null) {
            book.getPerson().getBooks().remove(book);
            sessionFactory.getCurrentSession().update(book.getPerson());
        }
	    sessionFactory.getCurrentSession().delete(book);
	}

	@Override
	public List<Book> findAllBooks() {
		return sessionFactory.getCurrentSession().getNamedQuery("book.all")
				.list();
	}

	@Override
	public List<Book> findAvailableBooks() {
		return null;
	}

	@Override
	public List<Book> findBooksByAuthor(String authorNameFragment) {
		return (List<Book>) sessionFactory.getCurrentSession()
				.getNamedQuery("book.findAvailableBooksByAuthor")
				.setString("authorNameFragment", "%"+authorNameFragment+"%")
				.list();
	}

	@Override
	public void deleteClient(Person person) {
		person = (Person) sessionFactory.getCurrentSession().get(Person.class,
				person.getId());
		for (Book book : person.getBooks()) {
			book.setPerson(null);
			sessionFactory.getCurrentSession().update(book);
		}
		sessionFactory.getCurrentSession().delete(person);
	}

}
