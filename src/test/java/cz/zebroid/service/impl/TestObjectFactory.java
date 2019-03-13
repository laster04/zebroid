package cz.zebroid.service.impl;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static java.time.ZonedDateTime.now;

import com.github.tomakehurst.wiremock.client.MappingBuilder;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import cz.zebroid.model.Loan;

@Component
public class TestObjectFactory {
	
	public void prepareMockLoans(int page, int batchSize, String file, ZonedDateTime dateTime){
		MappingBuilder mappingBuilder = get(urlPathMatching("/loans/marketplace"))
				.withHeader(HttpHeaders.ACCEPT, equalTo(MediaType.APPLICATION_JSON_VALUE))
				.withHeader(HttpHeaders.CONTENT_TYPE, equalTo(MediaType.APPLICATION_JSON_VALUE))
				.withHeader("X-Page", equalTo(String.valueOf(page)))
				.withHeader("X-Size", equalTo(String.valueOf(batchSize)));
		if(Objects.nonNull(dateTime)){
			mappingBuilder.withQueryParam("datePublished__gt", equalTo(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(dateTime)));
		}
		stubFor(mappingBuilder.willReturn(aResponse()
				.withStatus(HttpStatus.OK.value())
				.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.withBodyFile(file)
		));
	}
	
	public void prepareMockLoans(int page, int batchSize, String file) {
		this.prepareMockLoans(page, batchSize, file, null);
	}
	
	public Loan createLoan(Long id, int minusMinutesDatePublished) {
		ZonedDateTime zonedDateTime = now(ZoneOffset.UTC);
		zonedDateTime.minusMinutes(minusMinutesDatePublished);
		Loan loan = new Loan();
		loan.setId(id);
		loan.setDatePublished(zonedDateTime);
		return loan;
	}
}
