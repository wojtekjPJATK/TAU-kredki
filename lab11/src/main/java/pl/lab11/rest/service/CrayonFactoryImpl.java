package pl.lab11.rest.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.lab11.rest.domain.Crayon;
import pl.lab11.rest.domain.Person;
import pl.lab11.rest.domain.MyDate;

@Component
@Transactional
public class CrayonFactoryImpl implements CrayonFactory {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public List<Crayon> findAllCrayons(int index, int results) {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("crayons.all");
		query.setFirstResult(index);
		query.setMaxResults(results);
		return (List<Crayon>) query.list();
	}

	@Override
	public List<Crayon> findAllCrayons() {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("crayons.all");
		return (List<Crayon>) query.list();
	}

	@Override
	public List<Crayon> findCrayonsByColor (String color) {
		return (List<Crayon>) sessionFactory.getCurrentSession().getNamedQuery("crayons.findbyColor").setString("givenColor", color).list();
	}

	@Override
    public void updateCrayon(Crayon crayon) {
        sessionFactory.getCurrentSession().update(crayon);
    }

	@Override
	public Long addWorker(Person person) {
		return (Long) sessionFactory.getCurrentSession().save(person);
	}

	@Override
	public boolean deleteCrayon(Crayon crayon) {
        if (crayon.getCreator() != null) {
            crayon.getCreator().getCrayons().remove(crayon);
            sessionFactory.getCurrentSession().update(crayon.getCreator());
        }
		sessionFactory.getCurrentSession().delete(crayon);
		return true;
	}

	@Override
	public Person findWorker(String name) {
		List<Person> result = (List<Person>) sessionFactory.getCurrentSession().getNamedQuery("person.byName").setString("givenName", "%" + name + "%").list();
		return result.get(0);

	}

	@Override
	public Long addCrayon(Crayon crayon) {
		MyDate myDate = new MyDate();
		crayon.setCreationDate(new Date(myDate.getCurrentTime()));
		return (Long) sessionFactory.getCurrentSession().save(crayon);
	}

	@Override
	public List<Crayon> findCrayonsByCreator(Person creator) {
		return (List<Crayon>) sessionFactory.getCurrentSession().getNamedQuery("crayons.findByCreator").setParameter("creator", creator).list();
	}

	@Override
	public Crayon transferCrayonToAnotherCreator(Crayon crayon, Person newCreator) throws IllegalArgumentException {
		if(newCreator == null) throw new IllegalArgumentException("Can't transfer to null");
		if(newCreator.equals(crayon.getCreator())) throw new IllegalArgumentException("Cant transfer to the same person");
		crayon.setCreator(newCreator);
		sessionFactory.getCurrentSession().save(crayon);
		sessionFactory.getCurrentSession().save(newCreator);
		return crayon;
	}
}
