package com.shinsegae.smon.database.tablespace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TableSpaceDbMonitoringMapper {
	List<HashMap<String, Object>> selectUndoTableSpaceState(Map<String, Object> map);
	
	int insertUndoTablespaceHistoryTable(Map<String, Object> map);
	
	List<HashMap<String, Object>> selectTablespaceState(Map<String, String> map);
	
	List<HashMap<String, Object>> selectTablespaceSizeTck(Map<String, Object> map);
	
	int selectTablespaceSizeTckTotal(Map<String, Object> map);
}
