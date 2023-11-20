package telran.spring.service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import telran.exeptions.NotFoundExeption;
import telran.spring.Person;

@Service
@Slf4j
public class GreetingServiceImpl implements GreetingsService {
	  	
	Map<Long, Person> greetingsMap = new HashMap<>();
	@Value("${app.greeting.message:Hello}")
	String greetingMessage;
	@Value("${app.unknown.name:unknown guest}")
	String unknownName;
	@Value("${app.file.name:persons.data}")
	String fileName;
	@Override   
	public String getGreetings(long id) {
		Person person = greetingsMap.get(id);
		//String name = person == null ? "Unknown guest" : person.name();
		String name = "";
		if(person == null) {
	 		name = unknownName;
			log.warn("person with id {} not found", id);
		
		} else {
			name = person.name();
			log.debug("person name is {}", name);
		}
		return String.format("%s, %s", greetingMessage, name);
	}
	
	@Override
	public Person getPerson(long id) {
		Person person = greetingsMap.get(id);
		if(person == null) {
			log.warn("person with id {} not found", id);
		
		} else {
			log.debug("person with id {} exists", id);
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
		if(greetingsMap.containsKey(id)) {
			throw new IllegalStateException(String.format("person with id %d already exists", id));
		}
		greetingsMap.put(id, person);	
		log.debug("person with id {} has been saved", id);
		return person;
	}
	
	@Override
	public Person deletePerson(long id) {
		if(!greetingsMap.containsKey(id)) {
			throw new NotFoundExeption(String.format("person with id %d doesn't exist", id));
		}
		Person person = greetingsMap.remove(id);
		log.debug("person with id {} has been removed", id);
		return  person; 
	}
	
	@Override
	public Person updatePerson(Person person) {
		long id = person.id();
		if(!greetingsMap.containsKey(id)) {
			throw new NotFoundExeption(String.format("person with id %d doesn't exist", id));
		}
		greetingsMap.put(id, person);
		log.debug("person with id {} has been updated", person.id());
		return person;
	}
	
	@Override
	public void save(String fileName) {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))) {
			output.writeObject(new ArrayList<Person>(greetingsMap.values()));
			log.info("persons data have been saved");
		} catch (Exception e) {
			log.error("{}", e);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void restore(String fileName) {
		try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName))) {
			List<Person> restoredPersons = (List<Person>) input.readObject();
			restoredPersons.forEach(this::addPerson);
			log.info("restored fronm file");
			} catch (FileNotFoundException e) {
				log.warn("No file with data found");
			}
			catch (Exception e) {
				log.error("{}", e);
			}
	
	}
	@PostConstruct
	void restoreFromFile() {
		restore(fileName);
		
	}
	@PreDestroy
	void saveFile() {	
		save(fileName);
	}
	
	
	
} 
