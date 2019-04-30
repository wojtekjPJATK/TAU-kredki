package pl.puzniakowski.shdemo.domain;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

@Entity(name = "Person")
@Table(name = "person")
@NamedQueries({ 
	@NamedQuery(name = "person.all", query = "Select p from Person p"),
})
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String firstName;

	@Temporal(TemporalType.DATE)
	private Date registrationDate;

	/**
	 * the books borrowed by the person.
	 */
	@OneToMany(cascade = CascadeType.PERSIST,
			fetch = FetchType.EAGER,
			orphanRemoval=false,
			mappedBy = "person"
	)
	private List<Book> books = new LinkedList<>();

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public List<Book> getBooks() {
		return books;
	}

	//
	//public void addBook(Book book) {
	//	books.add(book);
	//	books.setBook(this);
	//}
	//public void removeBook(PostBook book) {
	//	books.remove(book);
	//	book.setPost(null);
	//}


	public void setBooks(List<Book> books) {
		this.books = books;
	}
}
