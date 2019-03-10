package cz.zebroid.service.impl;

import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

import javax.validation.constraints.NotNull;

import cz.zebroid.service.MemLastProcessInfoService;

@Component
public class MemLastProcessInfoServiceImpl implements MemLastProcessInfoService {
	
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
