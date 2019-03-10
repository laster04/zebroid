package cz.zebroid.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import cz.zebroid.model.Loan;

@Component
public class ConsolePrintProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(ConsolePrintProcessor.class);
	
	public void run(List<Loan> loans){
		logger.info("Process {} loans", loans.size());
		loans.forEach(System.out::println);
	}
}
