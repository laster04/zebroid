package cz.zebroid.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import cz.zebroid.model.Loan;
import cz.zebroid.processor.LoanConsolePrinter;
import cz.zebroid.service.LoanMarketplaceService;

/**
 * Job repetitively checking new loans
 * Job holds publishDate of last loan after app start and after first scheduled job
 * LastCall parameter is only in memory during app run
 */
@Component
public class ScheduledLoansJob {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduledLoansJob.class);
	
	private final LoanMarketplaceService loanMarketplaceService;
	
	private final LoanConsolePrinter loanConsolePrinter;
	
	private ZonedDateTime lastCall;
	
	public ScheduledLoansJob(LoanMarketplaceService loanMarketplaceService,
			LoanConsolePrinter loanConsolePrinter) {
		this.loanMarketplaceService = loanMarketplaceService;
		this.loanConsolePrinter = loanConsolePrinter;
	}
	
	@Scheduled(cron = "${zonky.loans-scheduler}")
	public void checkAndReportNewLoans() {
		logger.info("Start download new loans");
		
		List<Loan> loanList = loanMarketplaceService.downloadMarketplaceLoans(lastCall);
		if (!CollectionUtils.isEmpty(loanList)) {
			Collections.reverse(loanList);
			loanConsolePrinter.print(loanList);
			lastCall = loanList.get(loanList.size() - 1).getDatePublished();
		}
		logger.info("End download new loans");
	}
}
