package com.shinsegae.smon.database.objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class InvalidObjectsDbMonitoringService {

	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
	SqlSessionTemplate sqlSessionTemplatePrimary;
	
	public List<HashMap<String, Object>> selectDbDictionaryInfo(Map<String, Object> params) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(InvalidObjectsDbMonitoringMapper.class).selectInvalidObjectsList(params);
	}
	
	
	public List<HashMap<String, Object>> selectInvalidObjectsState(String strSystemCode) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(InvalidObjectsDbMonitoringMapper.class).selectInvalidObjectsState(strSystemCode);
	}
}
