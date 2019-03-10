package cz.zebroid.service.impl;

import org.springframework.stereotype.Component;

import java.util.Date;

import javax.validation.constraints.NotNull;

import cz.zebroid.service.MemLastProcessInfoService;

@Component
public class MemLastProcessInfoServiceImpl implements MemLastProcessInfoService {
	
	private Date lastProcessedTime;
	
	@Override
	public void setLastProcessTime(@NotNull Date recordTime) {
		lastProcessedTime = recordTime;
	}
	
	@Override
	public Date getLastProcessTime() {
		return lastProcessedTime;
	}
}
