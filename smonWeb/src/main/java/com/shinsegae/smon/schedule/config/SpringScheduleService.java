package com.shinsegae.smon.schedule.config;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SpringScheduleService {

	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
	SqlSessionTemplate sqlSessionTemplatePrimary;
	
	public List<Map<String, Object>> searchScheduleTrigger() throws Exception {
		return sqlSessionTemplatePrimary.getMapper(SpringScheduleMapper.class).searchScheduleTrigger();
	}
}
