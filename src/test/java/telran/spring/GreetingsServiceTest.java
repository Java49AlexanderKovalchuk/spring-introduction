package telran.spring;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;

import telran.exeptions.NotFoundExeption;
import telran.spring.service.GreetingsService;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //Defining order of the tests by @Order
class GreetingsServiceTest {
	@Autowired
	GreetingsService greetingsService;
	Person personNormal = new Person(123, "Vasya", "Rehovot", "vasya@gmail.com",
    		"054-1234567"); 
	Person personNormalUpdated = new Person(123, "Vasya", "Lod", "vasya@gmail.com",
    		"054-1234567"); 
	Person personNotFound = new Person(500, "Vasya", "Rehovot", "vasya@gmail.com",
    		"054-1234567");
//	@Value("${app.unknown.name:unknown guest}")
//	String unknownName;
	
	@BeforeAll 
	static void deleteFile() throws IOException {
		Files.deleteIfExists(Path.of("test.data"));
	}
	@Test
	@Order(1)
	void loadApplicationContextTest() {
		assertNotNull(greetingsService);
	}
	@Test
	@Order(2)
	void addPersonNormalTest() {
		  assertEquals(personNormal, greetingsService.addPerson(personNormal));
	}
	@Test
	@Order(3)
	void addPersonAlreadyExistsTest() {
		assertThrowsExactly(IllegalStateException.class, 
				() -> greetingsService.addPerson(personNormal));
	}
	@Test
	@Order(4)
	void updatePersonNormalTest() {
		assertEquals(personNormalUpdated, greetingsService.updatePerson(personNormalUpdated));
	}
	@Test
	@Order(5)
	void getPersonTest() {
		assertEquals(personNormalUpdated, greetingsService.getPerson(123));
	
	}
	@Test
	@Order(6)
	void getPersonNotFound() {
		assertNull(greetingsService.getPerson(500));
	}
	@Test
	@Order(7)
	void updateNotExistPersonTest() {
		assertThrowsExactly(NotFoundExeption.class, 
				() -> greetingsService.updatePerson(personNotFound));
	}
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	@Test
	@Order(8)
	void persistenceTest() {
		assertEquals(personNormalUpdated, greetingsService.getPerson(123));
	}
	@Test
	@Order(9)
	void getPersonsByCityTest() {
		List<Person> expected = List.of(personNormalUpdated);
		assertIterableEquals(expected, greetingsService.getPersonByCity("Lod"));
		assertTrue(greetingsService.getPersonByCity("Rehovot").isEmpty());
	}
	@Test
	@Order(10)
	void deleteTest() {
		assertEquals(personNormalUpdated, greetingsService.deletePerson(123));
		assertTrue(greetingsService.getPersonByCity("Lod").isEmpty());
		assertTrue(greetingsService.getPersonByCity("Rehovot").isEmpty());
		assertThrowsExactly(NotFoundExeption.class, 
				() -> greetingsService.deletePerson(123));
	}
	@Test
	void getGreetingsTest() {
		//assertEquals("Hello, Vasya", greetingsService.getGreetings(123));
		assertEquals("Hello, nemo", greetingsService.getGreetings(500));
	}



} 
