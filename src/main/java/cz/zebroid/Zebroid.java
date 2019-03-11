package cz.zebroid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import cz.zebroid.client.ZonkyClient;

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
}
