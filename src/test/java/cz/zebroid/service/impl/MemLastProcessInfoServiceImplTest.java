package cz.zebroid.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;

import cz.zebroid.service.MemLastProcessInfoService;

public class MemLastProcessInfoServiceImplTest {
	
	private MemLastProcessInfoService memLastProcessInfoService = new MemLastProcessInfoServiceImpl();
	
	@Test
	public void emptyLastInfotime() {
		Assertions.assertThat(memLastProcessInfoService.getLastProcessTime()).isNull();
	}
	
	@Test
	public void processInfoTimeTest() {
		ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
		
		Assertions.assertThat(memLastProcessInfoService.getLastProcessTime()).isNull();
		
		memLastProcessInfoService.setLastProcessTime(now);
		
		Assertions.assertThat(memLastProcessInfoService.getLastProcessTime()).isEqualTo(now);
		
		memLastProcessInfoService.setLastProcessTime(now.plusMinutes(10L));
		
		Assertions.assertThat(memLastProcessInfoService.getLastProcessTime()).isAfter(now);
	}
}