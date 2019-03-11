package cz.zebroid.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.format.DateTimeFormatter;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class ZebroidRequestInterceptor implements RequestInterceptor {
	
	@Value("${zonky.user-agent}")
	private String userAgentValue;
	
	@Bean
	public FeignFormatterRegistrar localDateFeignFormatterRegistrar() {
		return formatterRegistry -> {
			DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
			registrar.setDateTimeFormatter(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
			registrar.registerFormatters(formatterRegistry);
		};
	}
	
	@Override
	public void apply(RequestTemplate requestTemplate) {
		setHeader(requestTemplate, HttpHeaders.USER_AGENT, userAgentValue);
		setHeader(requestTemplate, HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		setHeader(requestTemplate, HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
	}
	
	private void setHeader(RequestTemplate template, String headerName, String value){
		if (!template.headers().containsKey(headerName)){
			template.header(headerName, value);
		}
	}
}
