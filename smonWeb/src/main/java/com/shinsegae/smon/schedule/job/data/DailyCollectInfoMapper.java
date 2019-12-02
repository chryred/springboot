package com.shinsegae.smon.schedule.job.data;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface DailyCollectInfoMapper {
	
	List<Map<String, Object>> searchDailyCollectInfoByThread(Map<String, Object> param);
	
	List<Map<String, Object>> searchDailyCollectInfo();
	
	List<Map<String, Object>> searchTckLoqCollectInfo(Map<String, Object> param);
	
	List<Map<String, Object>> searchDailyTckLoqCollectInfo(Map<String, Object> param);	
	
	@SelectProvider(type = DynamicQuery.class, method= "excuteQry")
	Map<String, Object>searchDynamicMaxValue(Map<String, Object> param);
	
	@SelectProvider(type = DynamicQuery.class, method= "excuteQry")
	int searchDynamicCountInfo(Map<String, Object> param);
	
	@SelectProvider(type = DynamicQuery.class, method= "excuteQry")
	List<Map<String, Object>> searchDynamicCollectInfo(Map<String, Object> param);
	
	@InsertProvider(type = DynamicQuery.class, method= "excuteQry")
	int insertIgnoreDynamicBaseInfo(Map<String, Object> param);
	
	@DeleteProvider(type = DynamicQuery.class, method= "excuteQryString")
	void deleteDynamicBaseInfo(String strQry);
}
