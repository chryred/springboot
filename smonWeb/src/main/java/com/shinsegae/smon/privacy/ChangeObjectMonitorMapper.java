package com.shinsegae.smon.privacy;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChangeObjectMonitorMapper {
	
	List<HashMap<String, Object>> selectList(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectColList(HashMap<String, Object> params); 
	
	void updateAuthedData(HashMap<String, Object> params); 
	
}
