package cz.zebroid.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import cz.zebroid.client.ZonkyClient;
import cz.zebroid.model.Loan;
import cz.zebroid.processor.ConsolePrintProcessor;
import cz.zebroid.service.LoanMarketplaceService;
import cz.zebroid.service.MemLastProcessInfoService;

@Service
public class LoanMarketplaceServiceImpl implements LoanMarketplaceService {
	
	private final static Logger logger = LoggerFactory.getLogger(LoanMarketplaceServiceImpl.class);
	
	final static String LOANS_ORDER = "datePublished";
	
	final static String TOTAL_LOANS_COUNT = "X-Total";
	
	@Value("${zonky.batch-size}")
	private int responseBatchSize;
	
	private final ZonkyClient zonkyClient;
	
	private final ConsolePrintProcessor consolePrintProcessor;
	
	private final MemLastProcessInfoService memLastProcessInfoService;
	
	public LoanMarketplaceServiceImpl(ZonkyClient zonkyClient, ConsolePrintProcessor consolePrintProcessor,
			MemLastProcessInfoService memLastProcessInfoService) {
		this.zonkyClient = zonkyClient;
		this.consolePrintProcessor = consolePrintProcessor;
		this.memLastProcessInfoService = memLastProcessInfoService;
	}
	
	@Override
	public void downloadMarketpalceLoans() {
		ZonedDateTime lastCall = memLastProcessInfoService.getLastProcessTime();
		ZonedDateTime lastDatePublished = null;
		int totalRecordsNum;
		int processedRecords = 0;
		int page = 0;
		ResponseEntity<List<Loan>> responseEntity;
		do {
			if (Objects.nonNull(lastCall)) {
				logger.info("Download loans after {}", lastCall);
				responseEntity = zonkyClient
						.getMarketplaceLoansByDatePublishedGT(page, responseBatchSize, LOANS_ORDER, lastCall.toOffsetDateTime());
			} else {
				responseEntity = zonkyClient.getMarketplaceLoans(page, responseBatchSize, LOANS_ORDER);
			}
			if (responseEntity.getBody() != null && !responseEntity.getBody().isEmpty()) {
				consolePrintProcessor.run(responseEntity.getBody());
				processedRecords += responseEntity.getBody().size();
				lastDatePublished =
						responseEntity.getBody().get(responseEntity.getBody().size() - 1).getDatePublished();
			}
			page++;
			totalRecordsNum = getTotalRecordsCount(responseEntity);
			logger.info("Processed records [{}] of total [{}]", processedRecords, totalRecordsNum);
		} while (processedRecords < totalRecordsNum);
		if (Objects.nonNull(lastDatePublished)) {
			memLastProcessInfoService.setLastProcessTime(lastDatePublished);
		}
	}
	
	
	private int getTotalRecordsCount(@NotNull ResponseEntity responseEntity) {
		if (responseEntity.getHeaders().containsKey(TOTAL_LOANS_COUNT)) {
			return Integer.parseInt(Objects.requireNonNull(responseEntity.getHeaders().getFirst(TOTAL_LOANS_COUNT)));
		}
		return 0;
	}
}
