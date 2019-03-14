package cz.zebroid.service;

import java.time.ZonedDateTime;
import java.util.List;

import cz.zebroid.model.Loan;

public interface LoanMarketplaceService {
	
	/**
	 * Method call marketplace API and download loans
	 *
	 * @param lastDatePublishLoan time of last published loans in Zonky marketplace API
	 * @return List of loans
	 */
	List<Loan> downloadMarketplaceLoans(ZonedDateTime lastDatePublishLoan);
}
