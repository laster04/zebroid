package cz.zebroid.service;

import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;

public interface MemoryLastProcessInfoService {
	
	void setLastProcessTime(@NotNull ZonedDateTime recordTime);
	
	ZonedDateTime getLastProcessTime();
}
