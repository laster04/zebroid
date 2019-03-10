package cz.zebroid.service.impl;

import static cz.zebroid.service.impl.LoanMarketplaceServiceImpl.LOANS_ORDER;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import cz.zebroid.client.ZonkyClient;
import cz.zebroid.model.Loan;
import cz.zebroid.processor.ConsolePrintProcessor;
import cz.zebroid.service.MemLastProcessInfoService;

@RunWith(MockitoJUnitRunner.class)
public class LoanMarketplaceServiceImplTest {
	
	@Value("${zonky.batch-size}")
	private int responseBatchSize;
	
	@Mock
	private ZonkyClient zonkyClient;
	
	@Mock
	private ConsolePrintProcessor consolePrintProcessor;
	
	@Mock
	private MemLastProcessInfoService memLastProcessInfoService;
	
	@Captor
	private ArgumentCaptor<List<Loan>> loansCaptor;
	
	private LoanMarketplaceServiceImpl loanMarketplaceServiceMock;
	
	@Before
	public void before(){
		loanMarketplaceServiceMock = new LoanMarketplaceServiceImpl(zonkyClient, consolePrintProcessor,
				memLastProcessInfoService);
	}
	
	@Test
	public void processMarketplaceLoans() {
		when(zonkyClient.getMarketplaceLoans(0, responseBatchSize, LOANS_ORDER))
				.thenReturn(createResponseEntity(3, createLoan(1L, 8)));
		
		when(zonkyClient.getMarketplaceLoans(1, responseBatchSize, LOANS_ORDER))
				.thenReturn(createResponseEntity(3, createLoan(2L, 16)));
		
		when(zonkyClient.getMarketplaceLoans(2, responseBatchSize, LOANS_ORDER))
				.thenReturn(createResponseEntity(3, createLoan(3L, 24)));
		
		doNothing().when(consolePrintProcessor).run(anyList());
		
		loanMarketplaceServiceMock.downloadMarketpalceLoans();
		
		verify(zonkyClient, times(1))
				.getMarketplaceLoans(0, responseBatchSize, LOANS_ORDER);
		verify(zonkyClient, times(1))
				.getMarketplaceLoans(1, responseBatchSize, LOANS_ORDER);
		verify(zonkyClient, times(1))
				.getMarketplaceLoans(2, responseBatchSize, LOANS_ORDER);
		
		verify(consolePrintProcessor, times(3)).run(loansCaptor.capture());
		
		
		verifyNoMoreInteractions(zonkyClient, consolePrintProcessor);
		
		List<List<Loan>> capturedLoans = loansCaptor.getAllValues();
		Assertions.assertThat(capturedLoans).isNotEmpty();
	}
	
	private ResponseEntity<List<Loan>> createResponseEntity(int count, Loan... loans){
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("X-Total", String.valueOf(count));
		return new ResponseEntity<>(Arrays.asList(loans), headers, HttpStatus.OK);
	}
	
	private Loan createLoan(Long id, int minusMinutesDatePublished){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -minusMinutesDatePublished);
		Loan loan = new Loan();
		loan.setId(id);
		loan.setDatePublished(cal.getTime());
		return loan;
	}
}