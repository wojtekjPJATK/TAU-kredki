package pl.tau.lab9wj.service;

import java.util.List;

import pl.tau.lab9wj.domain.Crayon;
import pl.tau.lab9wj.domain.Person;

public interface CrayonFactory {
	
	Long addWorker(Person person);
	Long addCrayon(Crayon crayon);
	List<Crayon> findAllCrayons();
	List<Crayon> findCrayonsByColor(String color);
	void deleteCrayon(Crayon crayon);
	void updateCrayon(Crayon crayon);

	Person findWorker(String name);
}
