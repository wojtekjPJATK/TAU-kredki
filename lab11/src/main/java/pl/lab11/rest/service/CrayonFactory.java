package pl.lab11.rest.service;

import java.util.List;

import pl.lab11.rest.domain.*;

public interface CrayonFactory {
	
	Long addWorker(Person person);
	Long addCrayon(Crayon crayon);
	List<Crayon> findAllCrayons(int index, int results);
	List<Crayon> findAllCrayons();
	List<Crayon> findCrayonsByColor(String color);
	boolean deleteCrayon(Crayon crayon);
	void updateCrayon(Crayon crayon);

	Person findWorker(String name);

	List<Crayon> findCrayonsByCreator(Person creator);
	boolean transferCrayonToAnotherCreator(Crayon crayon, Person newCreator);
}
