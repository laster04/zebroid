package cz.zebroid.service;

import java.util.Date;

import javax.validation.constraints.NotNull;

public interface MemLastProcessInfoService {
	
	void setLastProcessTime(@NotNull Date recordTime);
	
	Date getLastProcessTime();
}
