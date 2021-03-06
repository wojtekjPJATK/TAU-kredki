package pl.lab11.rest.domain;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

@Entity(name = "Person")
@Table(name = "person")
@NamedQueries({ 
	@NamedQuery(name = "person.byName", query = "Select p from Person p where p.name like :givenName"),
})
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	@OneToMany(cascade = CascadeType.PERSIST,
			fetch = FetchType.EAGER,
			orphanRemoval=false,
			mappedBy = "creator"
	)
	private List<Crayon> crayons = new LinkedList<>();

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<Crayon> getCrayons() {
		return crayons;
	}

	public void setCrayons(List<Crayon> crayons) {
		this.crayons = crayons;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Person))
            return false;
        Person p = (Person) o;
        return this.getName().equals(p.getName());
    }
}
