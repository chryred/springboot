package com.shinsegae.smon.issue;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScheduleMapper {

	public List<HashMap<String, Object>> searchComboData(HashMap<String, String> map) throws Exception;

	public List<HashMap<String, Object>> searchProjectGridData(HashMap<String, String> param) throws Exception;

	public List<HashMap<String, Object>> searchAllData(HashMap<String, String> param) throws Exception;

	public void updateRow(HashMap<String, String> param) throws Exception;

}