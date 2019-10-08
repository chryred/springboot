package com.shinsegae.smon.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
	SqlSessionTemplate sqlSessionTemplatePrimary;
	
	public List<HashMap<String, Object>> searchSystemCombo(Map<String,String> map) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(CommonMapper.class).searchSystemCombo(map);
	}
}
