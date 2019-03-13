package cz.zebroid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.format.DateTimeFormatter;

import cz.zebroid.client.ZonkyClient;
import cz.zebroid.conf.ZebroidFeignRequestInterceptor;

@SpringBootApplication
@EnableFeignClients(basePackageClasses = ZonkyClient.class)
@EnableScheduling
public class Zebroid {
	
	private static Logger logger = LoggerFactory.getLogger(Zebroid.class);
	
	public static void main(String[] args) {
		try {
			SpringApplication.run(Zebroid.class, args);
		} catch (Exception e) {
			logger.error("Can't start Zebroid test loan API", e);
		}
	}
	
	@Bean
	public ZebroidFeignRequestInterceptor zebroidRequestInterceptor() {
		return new ZebroidFeignRequestInterceptor();
	}
	
	/**
	 * DateTime format - 2011-12-03T10:15:30+01:00
	 *
	 * @return FeignFormatterRegistrar
	 */
	@Bean
	public FeignFormatterRegistrar localDateFeignFormatterRegistrar() {
		return formatterRegistry -> {
			DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
			registrar.setDateTimeFormatter(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
			registrar.registerFormatters(formatterRegistry);
		};
	}
}
