package com.shinsegae.smon.schedule.job.data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface BaseInfoMapper {
	List<Map<String, Object>> searchBaseInfo();
	
	@SelectProvider(type = DynamicQuery.class, method= "excuteQry")
	List<LinkedHashMap<String, Object>> searchDynamicBaseInfo(Map<String, Object> param);
	
	@InsertProvider(type = DynamicQuery.class, method= "excuteQry")
	int mergeDynamicBaseInfo(Map<String, Object> param);
	
	@DeleteProvider(type = DynamicQuery.class, method= "excuteQryString")
	void deleteDynamicBaseInfo(String strQry);
}
