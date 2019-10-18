package com.shinsegae.smon.privacy;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ChangeObjectMonitorService {
	
	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
    private SqlSessionTemplate sqlSessionTemplatePrimary;
	
	public List<HashMap<String, Object>> selectList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(ChangeObjectMonitorMapper.class).selectList(params);
	}
	
	public List<HashMap<String, Object>> selectColumnList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(ChangeObjectMonitorMapper.class).selectColList(params);
	}
	
	public void updateAuthedData(List<HashMap<String, Object>> paramsList, String empNo) {
		Iterator<HashMap<String, Object>> iter = paramsList.iterator();
		while(iter.hasNext()) {
			HashMap<String, Object> params = iter.next();
			params.put("empNo", empNo);
			sqlSessionTemplatePrimary.getMapper(ChangeObjectMonitorMapper.class).updateAuthedData(params);
		}
	}

}
