package telran.spring;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.spring.calculator.controller.CalculatorController;
import telran.spring.calculator.dto.OperationData;
import telran.spring.calculator.service.ArithmeticCalculatorService;
import telran.spring.calculator.service.CalculatorService;
import telran.spring.calculator.service.DatesCalculatorService;

@WebMvcTest
public class CalculatorControllerTest {
	@Autowired
	CalculatorController calculatorController;
	@MockBean
//	@Autowired - ???
//	DatesCalculatorService datesService;
//	ArithmeticCalculatorService arithmeticService;
	List<CalculatorService> calculatorServices;
	
//	CalculatorService[] services = new CalculatorService[] {arithmeticService, datesService};
//	 
	@Autowired
	MockMvc mockMvc;
	
	OperationData operationDataArithNormal = 
			new OperationData("arithmetic", "add", "10.5", "0.5");
	OperationData operationDataDatesNormal = 
			new OperationData("dates", "between", "2023-11-20", "2023-12-25");
	@Autowired
	ObjectMapper objectMapper;

//	
	@Test
	void applicationContextTest() {
		assertNotNull(calculatorController);
//		assertNotNull(arithmeticService);
//		assertNotNull(datesService);
//		assertNotNull(services);
		assertNotNull(calculatorServices);
		assertNotNull(mockMvc);
		assertNotNull(objectMapper);
	}
	@Test
	void normalCalculateArithmAAdd() throws Exception {
		mockMvc.perform(post("http://localhost:8080/calculator")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(operationDataArithNormal)))
		.andDo(print()).andExpect(status().isOk());
	}
}
