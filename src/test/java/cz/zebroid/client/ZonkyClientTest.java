package cz.zebroid.client;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;

import cz.zebroid.model.Loan;
import cz.zebroid.service.impl.TestObjectFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("offline")
public class ZonkyClientTest {
	
	@Value("${zonky.batch-size}")
	private int responseBatchSize;
	@Autowired
	private ZonkyClient zonkyClient;
	
	@Autowired
	private TestObjectFactory testObjectFactory;
	
	@ClassRule
	public static WireMockClassRule wireMockClassRule = new WireMockClassRule(wireMockConfig().port(9091));
	
	@Test
	public void callEmptyLoansTest() {
		testObjectFactory.prepareMockLoans(10, responseBatchSize, "empty.json");
		
		ResponseEntity<List<Loan>> responseEntity =
				zonkyClient.getMarketplaceLoans(10, responseBatchSize, "datePublished");
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assert.assertEquals(0, Objects.requireNonNull(responseEntity.getBody()).size());
	}
	
	@Test
	public void callSingleLoanTest() {
		testObjectFactory.prepareMockLoans(7, responseBatchSize, "singleLoan.json");
		ResponseEntity<List<Loan>> responseEntity =
				zonkyClient.getMarketplaceLoans(7, responseBatchSize, "datePublished");
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Loan loan = Objects.requireNonNull(responseEntity.getBody()).get(0);
		Assertions.assertThat(loan.getId()).isEqualTo(418933);
		Assertions.assertThat(loan.getName()).isEqualTo("Výměna vozového parku");
		Assertions.assertThat(loan.getPurpose()).isEqualTo("1");
		Assertions.assertThat(loan.getPhotos()).isNotEmpty();
		Assertions.assertThat(loan.getRating()).isEqualTo("AAA");
		Assertions.assertThat(loan.getTopped()).isFalse();
		Assertions.assertThat(loan.getPublished()).isTrue();
		Assertions.assertThat(loan.getMainIncomeType()).isEqualTo("EMPLOYMENT");
	}
}