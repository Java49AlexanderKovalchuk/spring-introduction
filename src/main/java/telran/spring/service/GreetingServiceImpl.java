package telran.spring.service;

import java.util.*;


import org.springframework.stereotype.Service;

import telran.spring.Person;
@Service
public class GreetingServiceImpl implements GreetingsService {
	  	
	Map<Long, Person> personsMap = 
			new HashMap<>(Map.of(123l, new Person(123l, "David", "Jerusalem"), 
								124l, new Person(124l, "Sara" , "Tel Aviv"), 
								125l, new Person(125l, "Rivka", "Lod"),
								126l, new Person(126l, "Yosef", "Lod")));
	
	@Override
	public Person getPerson(long id) {
		Person res = personsMap.get(id);
		if(res == null) {
			throw new IllegalStateException("ID " + id + " is not found");
		}
		return res;
	}
	
	@Override
	public List<Person> getPersonByCity(String city) {
		
		return personsMap.values()
				.stream().filter(p -> p.city().equalsIgnoreCase(city)).toList();
	}
	
	@Override
	public Person addPerson(Person person) {

		Person res = personsMap.putIfAbsent(person.id(), person);
		if(res != null) {
			throw new IllegalStateException("Person with ID " + person.id() + " is already exists");
		}
		return person;
	}
	
	@Override
	public Person deletePerson(long id) {

		Person res = personsMap.remove(id);
		if(res == null) {
			throw new IllegalStateException("person with ID " + id + "is not found");
		}
		return res;
	}
	
	@Override
	public Person updatePerson(Person person) {
		if(!personsMap.containsKey(person.id())) {
			throw new IllegalStateException("person with ID " + person.id() + "is not found");
		}
		personsMap.put(person.id(), person);
		return person;
	}
	
}
