package com.shinsegae.smon.privacy;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PersonalInfoMonitoringService {
	
	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
    private SqlSessionTemplate sqlSessionTemplatePrimary;
	
	public List<HashMap<String, Object>> selectPersonalInfoList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoMonitoringMapper.class).selectPersonalInfoList(params);
	}

	public List<HashMap<String, Object>> selectComboDBName(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoMonitoringMapper.class).selectComboDBName(params);
	}
	
	public List<HashMap<String, Object>> selectComboOwner(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoMonitoringMapper.class).selectComboOwner(params);
	}

	public void updatePersonalInfoException(List<HashMap<String, Object>> paramsList) {
		Iterator<HashMap<String, Object>> iter = paramsList.iterator();
		while(iter.hasNext()) {
			HashMap<String, Object> params = iter.next();
			sqlSessionTemplatePrimary.getMapper(PersonalInfoMonitoringMapper.class).updatePersonalInfoException(params);
		}
	}
	
	public List<HashMap<String, Object>> selectPersonalInfoProgressGraphList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoMonitoringMapper.class).selectPersonalInfoProgressGraphList(params);
	}

	public List<HashMap<String, Object>> selectPersonalInfoProgressGraphList2(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoMonitoringMapper.class).selectPersonalInfoProgressGraphList2(params);
	}
	
	public List<HashMap<String, Object>> selectPersonalInfoDetailList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoMonitoringMapper.class).selectPersonalInfoDetailList(params);
	}
}
