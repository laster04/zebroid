package cz.zebroid.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import cz.zebroid.service.MemoryLastProcessInfoService;

public class MemoryLastProcessInfoServiceImplTest {
	
	private MemoryLastProcessInfoService memoryLastProcessInfoService = new MemoryLastProcessInfoServiceImpl();
	
	@Test
	public void emptyLastInfoTimeTest() {
		Assertions.assertThat(memoryLastProcessInfoService.getLastProcessTime()).isNull();
	}
	
	@Test
	public void processInfoTimeTest() {
		ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
		
		Assertions.assertThat(memoryLastProcessInfoService.getLastProcessTime()).isNull();
		
		memoryLastProcessInfoService.setLastProcessTime(now);
		
		Assertions.assertThat(memoryLastProcessInfoService.getLastProcessTime()).isEqualTo(now);
		
		memoryLastProcessInfoService.setLastProcessTime(now.plusMinutes(10L));
		
		Assertions.assertThat(memoryLastProcessInfoService.getLastProcessTime()).isAfter(now);
	}
}