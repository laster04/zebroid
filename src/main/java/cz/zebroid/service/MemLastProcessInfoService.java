package cz.zebroid.service;

import java.time.ZonedDateTime;
import java.util.Date;

import javax.validation.constraints.NotNull;

public interface MemLastProcessInfoService {
	
	void setLastProcessTime(@NotNull ZonedDateTime recordTime);
	
	ZonedDateTime getLastProcessTime();
}
