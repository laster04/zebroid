package cz.zebroid.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import cz.zebroid.model.Loan;

/**
 * Print loans to console screen
 */
@Component
public class LoanConsolePrinter {
	
	private static final Logger logger = LoggerFactory.getLogger(LoanConsolePrinter.class);
	
	public void print(List<Loan> loans) {
		logger.info("Process {} loans", loans.size());
		loans.forEach(System.out::println);
	}
}
