package cz.zebroid.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.Test;

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
		Calendar now = Calendar.getInstance();
		Calendar cal = (Calendar) now.clone();
		
		Assertions.assertThat(memLastProcessInfoService.getLastProcessTime()).isNull();
		
		memLastProcessInfoService.setLastProcessTime(cal.getTime());
		
		Assertions.assertThat(memLastProcessInfoService.getLastProcessTime()).isEqualTo(now.getTime());
		
		cal.add(Calendar.MINUTE, 5);
		memLastProcessInfoService.setLastProcessTime(cal.getTime());
		
		Assertions.assertThat(memLastProcessInfoService.getLastProcessTime()).isAfter(now.getTime());
	}
}