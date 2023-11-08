package telran.spring.service;

import java.util.List;

import telran.spring.Person;

public interface GreetingsService {
	
	Person getPerson(long id);
	List<Person> getPersonByCity(String city);
	Person addPerson(Person person);
	Person deletePerson(long id);
	Person updatePerson(Person person);
	
}
	