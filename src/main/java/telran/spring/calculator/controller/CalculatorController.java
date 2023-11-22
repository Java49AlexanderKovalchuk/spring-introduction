package telran.spring.calculator.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.exeptions.NotFoundExeption;
import telran.spring.calculator.dto.OperationData;
import telran.spring.calculator.service.CalculatorService;

@RestController
@RequestMapping("calculator")
@RequiredArgsConstructor
@Slf4j
	
public class CalculatorController {
	final List<CalculatorService> calculatorServices;
	Map<String, CalculatorService> servicesMap;
	@PostConstruct 
	void createServicesMap() {
		servicesMap = calculatorServices.stream() 
		.collect(Collectors.toMap(service -> service.getCalculationType(), 
		service -> service));
		log.debug("sevices: {}", servicesMap.keySet());
	}
	
	@PostMapping
	String calculate(@RequestBody @Valid OperationData operationData) {
		String type = operationData.type();
		CalculatorService calculatorService = servicesMap.get(type);
		if(calculatorService == null) {
			throw new NotFoundExeption(String.format("type %s not found", type));
		}
		return calculatorService.calculate(operationData);
	}
	
}
