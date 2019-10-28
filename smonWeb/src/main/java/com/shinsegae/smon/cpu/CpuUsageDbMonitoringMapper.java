package com.shinsegae.smon.cpu;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CpuUsageDbMonitoringMapper {

    List<HashMap<String, Object>> selectCpuUsageList() throws Exception;
    
    List<HashMap<String, Object>> selectCpuUsageList(HashMap<String, String> map) throws Exception;

	List<HashMap<String, Object>> selectStatusOfInterlocking()  throws Exception;
	
}
