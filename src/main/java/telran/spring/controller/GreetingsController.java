package telran.spring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.spring.Person;
import telran.spring.service.GreetingsService;
import telran.spring.service.IdName;

@RestController
@RequestMapping("greetings")
@RequiredArgsConstructor 
@Slf4j
public class GreetingsController {
	final GreetingsService greetingsService;
	
	@GetMapping("{id}")
	String getGreetings(@PathVariable long id) {
		log.debug("method getGreetengs,	recieved id {}", id);
		
		return greetingsService.getGreetings(id);
	}
	
	@PostMapping
	Person addPerson(@RequestBody @Valid Person person) {
		log.debug("method: addPerson, IN: received {}", person);
		return greetingsService.addPerson(person);
	}
	
	@PutMapping
	Person updatePerson(@RequestBody @Valid Person person) {
		log.debug("method: updatePerson, received {}", person);
		return greetingsService.updatePerson(person);
	}
	
	@DeleteMapping("{id}")
	Person deletePerson(@PathVariable long id) {
		log.debug("method: deletePerson, received id {}", id);
		return greetingsService.deletePerson(id);
	}
	
	@GetMapping("city/{city}")
	List<Person> getPersonByCity(@PathVariable String city) {
		log.debug("method getPersonByCity, received city {}", city);
		List<Person> result = greetingsService.getPersonByCity(city);
		if(result.isEmpty()) {
			log.warn("received empy list for city: {}", city);
		} else {
			log.trace("result is {}", result);
		}
		return result;
	}
	 
	@GetMapping("id/{id}")
	Person getPerson(@PathVariable long id) {
		log.debug("method: getPerson, received id: {}", id);
		return greetingsService.getPerson(id);
	}
} 
