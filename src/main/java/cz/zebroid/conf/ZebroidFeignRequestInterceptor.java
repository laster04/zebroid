package cz.zebroid.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Configuration for feign requests
 */
public class ZebroidFeignRequestInterceptor implements RequestInterceptor {
	
	@Value("${zonky.user-agent}")
	private String userAgentValue;
	
	@Override
	public void apply(RequestTemplate requestTemplate) {
		setHeader(requestTemplate, HttpHeaders.USER_AGENT, userAgentValue);
		setHeader(requestTemplate, HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		setHeader(requestTemplate, HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
	}
	
	/**
	 * Set header if not present
	 *
	 * @param template   requestTemplate
	 * @param headerName header field
	 * @param value      header value
	 */
	private void setHeader(RequestTemplate template, String headerName, String value){
		if (!template.headers().containsKey(headerName)){
			template.header(headerName, value);
		}
	}
}
