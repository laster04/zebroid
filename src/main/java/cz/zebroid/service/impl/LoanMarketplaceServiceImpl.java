package cz.zebroid.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import cz.zebroid.client.ZonkyClient;
import cz.zebroid.model.Loan;
import cz.zebroid.service.LoanMarketplaceService;

/**
 * Service interact with Zonky marketplace API
 * in first-call download first 1000 newest loans (param should be configured in application.yml)
 * in next-call download only latest loans after the last one from previously call
 */
@Service
public class LoanMarketplaceServiceImpl implements LoanMarketplaceService {
	
	private final static Logger logger = LoggerFactory.getLogger(LoanMarketplaceServiceImpl.class);
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy - HH:mm:ss ");
	
	final static String LOANS_ORDER = "-datePublished";
	
	final static String TOTAL_LOANS_COUNT = "X-Total";
	
	@Value("${zonky.batch-size}")
	private int responseBatchSize;
	
	@Value("${zonky.init-max-batch-size}")
	public int initMaxBatchSize;
	
	private final ZonkyClient zonkyClient;
	
	public LoanMarketplaceServiceImpl(ZonkyClient zonkyClient) {
		this.zonkyClient = zonkyClient;
	}
	
	@Override
	public List<Loan> downloadMarketplaceLoans(ZonedDateTime lastDatePublishLoan) {
		int totalRecordsNum;
		int processedRecords = 0;
		int page = 0;
		List<Loan> returnLoans = new ArrayList<>();
		ResponseEntity<List<Loan>> responseEntity;
		do {
			if (Objects.nonNull(lastDatePublishLoan)) {
				logger.info("Download loans after {}", lastDatePublishLoan.format(formatter));
				responseEntity = zonkyClient
						.getMarketplaceLoansByDatePublishedGT(page, responseBatchSize, LOANS_ORDER,
								lastDatePublishLoan.toOffsetDateTime());
			} else {
				responseEntity = zonkyClient.getMarketplaceLoans(page, responseBatchSize, LOANS_ORDER);
			}
			if (responseEntity.getStatusCode() != HttpStatus.OK) {
				throw new IllegalStateException(String.format("Bad Http status (%s) during interacting with Zonky API",
						responseEntity.getStatusCode()));
			}
			if (responseEntity.getBody() != null && !responseEntity.getBody().isEmpty()) {
				returnLoans.addAll(responseEntity.getBody());
				processedRecords += responseEntity.getBody().size();
			}
			page++;
			totalRecordsNum = getTotalRecordsCount(responseEntity);
			logger.info("Processed records [{}] of total [{}]", processedRecords, totalRecordsNum);
			if (Objects.isNull(lastDatePublishLoan) && processedRecords >= initMaxBatchSize) {
				break;
			}
		} while (processedRecords < totalRecordsNum);
		return returnLoans;
	}
	
	
	private int getTotalRecordsCount(@NotNull ResponseEntity responseEntity) {
		if (responseEntity.getHeaders().containsKey(TOTAL_LOANS_COUNT)) {
			return Integer.parseInt(Objects.requireNonNull(responseEntity.getHeaders().getFirst(TOTAL_LOANS_COUNT)));
		}
		return 0;
	}
}
