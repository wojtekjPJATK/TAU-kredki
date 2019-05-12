package pl.tau.lab9wj.service;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.tau.lab9wj.domain.Crayon;
import pl.tau.lab9wj.domain.MyDate;
import pl.tau.lab9wj.domain.Person;

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
	public List<Crayon> findAllCrayons() {
		return sessionFactory.getCurrentSession().getNamedQuery("crayons.all").list();
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
	public void deleteCrayon(Crayon crayon) {
        if (crayon.getCreator() != null) {
            crayon.getCreator().getCrayons().remove(crayon);
            sessionFactory.getCurrentSession().update(crayon.getCreator());
        }
	    sessionFactory.getCurrentSession().delete(crayon);
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
	public boolean transferCrayonToAnotherCreator(Crayon crayon, Person newCreator) throws IllegalArgumentException {
		if(newCreator == null) throw new IllegalArgumentException("Can't transfer to null");
		if(newCreator.equals(crayon.getCreator())) throw new IllegalArgumentException("Cant transfer to the same person");
		crayon.setCreator(newCreator);
		sessionFactory.getCurrentSession().save(crayon);
		sessionFactory.getCurrentSession().save(newCreator);
		return true;
	}


}
