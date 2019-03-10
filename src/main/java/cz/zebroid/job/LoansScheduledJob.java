package cz.zebroid.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cz.zebroid.service.LoanMarketplaceService;

@Component
public class LoansScheduledJob {
	
	private static final Logger logger = LoggerFactory.getLogger(LoansScheduledJob.class);
	
	private final LoanMarketplaceService loanMarketplaceService;
	
	public LoansScheduledJob(LoanMarketplaceService loanMarketplaceService) {
		this.loanMarketplaceService = loanMarketplaceService;
	}
	
	@Scheduled(fixedRate = 5 * 60 * 1000)
	public void downloadLoans(){
		logger.info("Start download new loans {}");
		loanMarketplaceService.downloadMarketpalceLoans();
		logger.info("End download new loans");
	}
}
