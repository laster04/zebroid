package cz.zebroid.service;

import java.time.ZonedDateTime;
import java.util.List;

import cz.zebroid.model.Loan;

public interface LoanMarketplaceService {
	
	List<Loan> downloadMarketplaceLoans(ZonedDateTime lastCall);
}
