package com.shinsegae.smon.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.shinsegae.smon.model.AlertVO;

@Mapper
public interface AlertMapper {
	int insertAlertHistoryTable(AlertVO alertVO) throws Exception;
	
	String selectAlertMessage(AlertVO alertVO) throws Exception;
	
	List<HashMap<String, Object>> selectAlertLog(String strSystemCode);
	
	List<HashMap<String, Object>> selectHitRatio(Map<String,String> map);
}
