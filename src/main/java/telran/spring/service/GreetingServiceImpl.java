package telran.spring.service;

import java.util.*;


import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import telran.exeptions.NotFoundExeption;
import telran.spring.Person;
import telran.spring.controller.GreetingsController;
@Service
@Slf4j
public class GreetingServiceImpl implements GreetingsService {
	  	
	Map<Long, Person> greetingsMap = new HashMap<>();
	
	@Override
	public String getGreetings(long id) {
		Person person = greetingsMap.get(id);
		String name = person == null ? "Unknown guest" : person.name();
		if(person == null) {
	 		log.warn("Service. For recived id {} Person is null", id);
		} else {
			log.debug("Service. Method getGreeting, IN: received id {}", id);
			log.debug("Servise. Method getGreeting, OUT: Person's name is {}", name);
		}
		return "Hello, " + name;
	}
	
	@Override
	public Person getPerson(long id) {
		log.debug("Service. Method getPerson IN: received id is {}", id);
		Person person = greetingsMap.get(id);
		if(person == null) {
			log.warn("Service. Method getPerson OUT: for received id person is {}", person);
		} else {
			log.trace("Service. Method getPerson OUT: for received id person is {}", person);
		}
		return person;
	}
	
	@Override
	public List<Person> getPersonByCity(String city) {
		log.debug("Service. Method getPersonByCity, received city is {}", city);
		return greetingsMap.values().stream()
				.filter(p -> p.city().equals(city))
				.toList();
	}
	
	@Override
	public Person addPerson(Person person) {
		long id = person.id();
		log.debug("Service. Method addPerson, IN: received Person {}", person);
		if(greetingsMap.containsKey(id)) {
			throw new IllegalStateException(String.format("person with id %d already exists", id));
		}
		greetingsMap.put(id, person);		
		return person;
	}
	
	@Override
	public Person deletePerson(long id) {
		if(!greetingsMap.containsKey(id)) {
			throw new NotFoundExeption(String.format("person with id %d doesn't exist", id));
		}
		Person deletedPerson = greetingsMap.remove(id);
		log.debug("Service. Method deletePerson, OUT: deleted person is {}", 
				deletedPerson != null ? deletedPerson : null);
		return  deletedPerson; 
	}
	
	@Override
	public Person updatePerson(Person person) {
		long id = person.id();
		log.debug("Service. Method updatePerson, IN: received person {}", person);
		if(!greetingsMap.containsKey(id)) {
			throw new NotFoundExeption(String.format("person with id %d doesn't exist", id));
		}
		greetingsMap.put(id, person);
		return person;
	}
	
} 
