package com.shinsegae.smon.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinsegae.smon.model.AlertVO;

@Service
public class AlertService {

	@Autowired
	SqlSessionTemplate sqlSessionTemplatePrimary;
	
	public int insertAlertHistoryTable(AlertVO alertVO) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(AlertMapper.class).insertAlertHistoryTable(alertVO); 
	}
	
	public String selectAlertMessage(AlertVO alertVO) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(AlertMapper.class).selectAlertMessage(alertVO);
	}
	
	public List<HashMap<String, Object>> selectAlertLog(String strSystemCode) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(AlertMapper.class).selectAlertLog(strSystemCode);
	}
	
	
	public List<HashMap<String, Object>> selectHitRatio(Map<String,String> map) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(AlertMapper.class).selectHitRatio(map);
	}
	
	
}
