package cz.zebroid.service.impl;

import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;

import cz.zebroid.service.MemoryLastProcessInfoService;

@Component
public class MemoryLastProcessInfoServiceImpl implements MemoryLastProcessInfoService {
	
	private ZonedDateTime lastProcessedTime;
	
	@Override
	public void setLastProcessTime(@NotNull ZonedDateTime recordTime) {
		lastProcessedTime = recordTime;
	}
	
	@Override
	public ZonedDateTime getLastProcessTime() {
		return lastProcessedTime;
	}
}
