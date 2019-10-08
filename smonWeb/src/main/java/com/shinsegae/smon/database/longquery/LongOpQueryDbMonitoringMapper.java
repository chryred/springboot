package com.shinsegae.smon.database.longquery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LongOpQueryDbMonitoringMapper {


    List<HashMap<String, Object>> selectLongOpQueryList() throws Exception;

    int insertLongOpQueryHistoryTable(Map<String, Object> longOpQueryMap) throws Exception;
        
    List<HashMap<String, Object>> selectSqlTextList(Map<String, Object> longOpQueryMap) throws Exception;
    

    int selectLongOpQueryListTckTotal() throws Exception;

    List<HashMap<String, Object>> selectLongOpQueryListTck(Map<String, Object> paramMap) throws Exception;

	List<HashMap<String, Object>> selectLongQuery(Map<String, String> map) throws Exception;

	List<HashMap<String, Object>> selectLongQueryDetailList(Map<String, String> map) throws Exception;

	List<HashMap<String, Object>> selectLongQueryDetailMonList(Map<String, String> map) throws Exception;
	
	List<HashMap<String, Object>> selectLongQueryDetailListScnd(Map<String, String> map) throws Exception;

	List<HashMap<String, Object>> selectLongQueryDetailListScndParam(Map<String, Object> map) throws Exception;
}
