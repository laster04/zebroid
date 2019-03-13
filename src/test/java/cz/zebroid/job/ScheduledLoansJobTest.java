package cz.zebroid.job;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.Assert;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cz.zebroid.model.Loan;
import cz.zebroid.processor.LoanConsolePrinter;
import cz.zebroid.service.LoanMarketplaceService;
import cz.zebroid.service.impl.TestObjectFactory;

@RunWith(MockitoJUnitRunner.class)
public class ScheduledLoansJobTest {
	
	@Mock
	private LoanMarketplaceService loanMarketplaceService;
	
	@Mock
	private LoanConsolePrinter loanConsolePrinter;
	
	private ScheduledLoansJob scheduledLoansJob;
	
	private TestObjectFactory testObjectFactory;
	
	@Captor
	private ArgumentCaptor<List<Loan>> loansCaptor;
	
	@Before
	public void before() {
		testObjectFactory = new TestObjectFactory();
		scheduledLoansJob = new ScheduledLoansJob(loanMarketplaceService, loanConsolePrinter);
	}
	
	@Test
	public void checkAndReportLoansSchedulerTest__firstCall() {
		List<Loan> firstCall = Arrays
				.asList(testObjectFactory.createLoan(1L, 8),
						testObjectFactory.createLoan(2L, 16),
						testObjectFactory.createLoan(3L, 18));
		when(loanMarketplaceService.downloadMarketplaceLoans(null))
				.thenReturn(firstCall);
		
		scheduledLoansJob.checkAndReportNewLoans();
		
		verify(loanMarketplaceService, times(1)).downloadMarketplaceLoans(null);
		
		verify(loanConsolePrinter, times(1)).print(loansCaptor.capture());
		
		List<List<Loan>> captureLoans = loansCaptor.getAllValues();
		Assert.notEmpty(captureLoans, "Empty Loans list");
	}
	
	@Test
	public void checkAndReportLoansSchedulerTest__dateTimeOffset() {
		ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
		ReflectionTestUtils.setField(scheduledLoansJob, "lastCall", now);
		
		when(loanMarketplaceService.downloadMarketplaceLoans(any((ZonedDateTime.class))))
				.thenReturn(Collections.emptyList());
		
		scheduledLoansJob.checkAndReportNewLoans();
		
		verify(loanMarketplaceService, times(1))
				.downloadMarketplaceLoans(now);
		verifyNoMoreInteractions(loanMarketplaceService, loanConsolePrinter);
	}
	
}