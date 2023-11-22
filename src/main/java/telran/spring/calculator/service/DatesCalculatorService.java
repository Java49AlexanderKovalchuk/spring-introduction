package telran.spring.calculator.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import telran.spring.calculator.dto.OperationData;
@Service
public class DatesCalculatorService implements CalculatorService {

	@Override
	public String calculate(OperationData operationData) {
		String operation = operationData.operation();
		return switch(operation) {
			case "between" -> operationBetween(operationData);
			case "before" -> String.format("it was %s",
					LocalDate.now().minusDays(getDays(operationData)).toString());
			case "after" -> String.format("it will be %s", 
					LocalDate.now().plusDays(getDays(operationData)).toString());
			default -> throw new IllegalArgumentException(operation + " unsupported operation");
		};
	}

	private long getDays(OperationData operationData) {
		try {
			long days = Long.parseLong(operationData.operand1());
			return days;
		} catch (Exception e) {
			throw new IllegalArgumentException("operand must be a number");
		}
	}
	
	private String operationBetween(OperationData operationData) {
		LocalDate[] operands = getDatesOperands(operationData);
		long between = ChronoUnit.DAYS.between(operands[0], operands[1]);
		return between + " days";
	}

	private LocalDate[] getDatesOperands(OperationData operationData) {
		try {
			LocalDate op1 = LocalDate.parse(operationData.operand1());
			LocalDate op2 = LocalDate.parse(operationData.operand2());
			return new LocalDate[] {op1, op2};
		} catch (Exception e) {
			throw new IllegalArgumentException("both operand must be in format yyyy-mm-dd");
		}
	}
	
	@Override 
	public String getCalculationType() {
		
		return "dates";
	}

}
