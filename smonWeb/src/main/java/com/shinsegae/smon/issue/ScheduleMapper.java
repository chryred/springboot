package com.shinsegae.smon.issue;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.SqlSessionTemplate;

@Mapper
public interface ScheduleMapper {

	public List<HashMap<String, Object>> searchComboData(SqlSessionTemplate session, HashMap<String, String> map) throws Exception;

	public List<HashMap<String, Object>> searchProjectGridData(SqlSessionTemplate session, HashMap<String, String> param) throws Exception;

	public List<HashMap<String, Object>> searchAllData(SqlSessionTemplate session, HashMap<String, String> param) throws Exception;

	public void updateRow(SqlSessionTemplate session, HashMap<String, String> param) throws Exception;

}