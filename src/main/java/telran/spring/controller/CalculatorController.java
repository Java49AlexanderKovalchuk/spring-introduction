package telran.spring.controller;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import telran.spring.service.Calculator;

@RestController
@RequestMapping("calculator")
@RequiredArgsConstructor

public class CalculatorController {
	final Calculator calculator;
	@GetMapping("multiply/{op1}/{op2}")
	double multiply(@PathVariable double op1, @PathVariable double op2) {
		return calculator.multiply(op1, op2);
	}
	@GetMapping("sum/{op1}/{op2}")
	double sum(@PathVariable double op1, @PathVariable double op2) {
		return calculator.sum(op1, op2);
	}
	@GetMapping("subtract/{op1}/{op2}")
	double subtract(@PathVariable double op1, @PathVariable double op2) {
		return calculator.subtract(op1, op2);
	}
	@GetMapping("divide/{op1}/{op2}")
	double divide(@PathVariable double op1, @PathVariable double op2) {
		return calculator.divide(op1, op2);
	}
	
}
