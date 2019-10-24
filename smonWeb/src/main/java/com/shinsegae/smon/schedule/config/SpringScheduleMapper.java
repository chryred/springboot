package com.shinsegae.smon.schedule.config;

import java.util.List;
import java.util.Map;

public interface SpringScheduleMapper {
	List<Map<String, Object>> searchScheduleTrigger() throws Exception;
}	
