package cz.zebroid.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.OffsetDateTime;
import java.util.List;

import cz.zebroid.model.Loan;

@FeignClient(value = "loans", url = "${zonky.host}")
public interface ZonkyClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "${zonky.path-loans}")
	ResponseEntity<List<Loan>> getMarketplaceLoans(
			@RequestHeader("X-Page") int page,
			@RequestHeader("X-Size") int pageSize,
			@RequestHeader("X-Order") String order);
	
	@RequestMapping(method = RequestMethod.GET, value = "${zonky.path-loans}")
	ResponseEntity<List<Loan>> getMarketplaceLoansByDatePublishedGT(
			@RequestHeader("X-Page") int page,
			@RequestHeader("X-Size") int pageSize,
			@RequestHeader("X-Order") String order,
			@RequestParam("datePublished__gt") OffsetDateTime datePublish);
	
}
