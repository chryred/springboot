package com.shinsegae.smon.schedule.config;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SpringScheduleMapper {
	List<Map<String, Object>> searchScheduleTrigger() throws Exception;
}	
