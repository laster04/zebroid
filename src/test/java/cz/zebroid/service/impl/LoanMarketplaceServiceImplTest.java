package cz.zebroid.service.impl;

import static cz.zebroid.service.impl.LoanMarketplaceServiceImpl.LOANS_ORDER;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cz.zebroid.client.ZonkyClient;
import cz.zebroid.model.Loan;
import cz.zebroid.processor.LoanConsolePrinter;

@RunWith(MockitoJUnitRunner.class)
public class LoanMarketplaceServiceImplTest {
	
	@Mock
	private ZonkyClient zonkyClient;
	
	@Mock
	private LoanConsolePrinter loanConsolePrinter;
	
	private LoanMarketplaceServiceImpl loanMarketplaceServiceMock;
	
	private TestObjectFactory testObjectFactory;
	
	@Before
	public void before() {
		loanMarketplaceServiceMock = new LoanMarketplaceServiceImpl(zonkyClient);
		ReflectionTestUtils.setField(loanMarketplaceServiceMock, "initMaxBatchSize", 100); //
		testObjectFactory = new TestObjectFactory();
	}
	
	@Test(expected = IllegalStateException.class)
	public void processMarketplaceLoansExceptionTest() {
		when(zonkyClient.getMarketplaceLoans(0, 0, LOANS_ORDER))
				.thenReturn(createBadResponseEntity());
		
		loanMarketplaceServiceMock.downloadMarketplaceLoans(null);
	}
	
	@Test
	public void processMarketplaceLoansTest() {
		int responseBatchSize = 0;
		when(zonkyClient.getMarketplaceLoans(0, responseBatchSize, LOANS_ORDER))
				.thenReturn(createResponseEntity(3, testObjectFactory.createLoan(1L, 8)));
		when(zonkyClient.getMarketplaceLoans(1, responseBatchSize, LOANS_ORDER))
				.thenReturn(createResponseEntity(3, testObjectFactory.createLoan(2L, 16)));
		when(zonkyClient.getMarketplaceLoans(2, responseBatchSize, LOANS_ORDER))
				.thenReturn(createResponseEntity(3, testObjectFactory.createLoan(3L, 24)));
		
		loanMarketplaceServiceMock.downloadMarketplaceLoans(null);
		
		verify(zonkyClient, times(1))
				.getMarketplaceLoans(0, responseBatchSize, LOANS_ORDER);
		verify(zonkyClient, times(1))
				.getMarketplaceLoans(1, responseBatchSize, LOANS_ORDER);
		verify(zonkyClient, times(1))
				.getMarketplaceLoans(2, responseBatchSize, LOANS_ORDER);
		
		verifyNoMoreInteractions(zonkyClient, loanConsolePrinter);
	}
	
	private ResponseEntity<List<Loan>> createResponseEntity(int count, Loan... loans) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("X-Total", String.valueOf(count));
		return new ResponseEntity<>(Arrays.asList(loans), headers, HttpStatus.OK);
	}
	
	private ResponseEntity<List<Loan>> createBadResponseEntity() {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("X-Total", String.valueOf(10));
		return new ResponseEntity<>(Collections.emptyList(), headers, HttpStatus.BAD_REQUEST);
	}
}