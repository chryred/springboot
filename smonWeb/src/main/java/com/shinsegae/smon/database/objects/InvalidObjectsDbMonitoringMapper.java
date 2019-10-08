package com.shinsegae.smon.database.objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface InvalidObjectsDbMonitoringMapper {
	List<HashMap<String, Object>> selectInvalidObjectsList(Map<String, Object> params) throws Exception;
	
	List<HashMap<String, Object>> selectInvalidObjectsState(String strSystemCode) throws Exception;
	
}
