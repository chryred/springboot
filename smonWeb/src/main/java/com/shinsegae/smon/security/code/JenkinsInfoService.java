package com.shinsegae.smon.security.code;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class JenkinsInfoService {

	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
	SqlSessionTemplate sqlSessionTemplate;


	public List<HashMap<String, Object>> selectTotalJobNm(Map<String, String> param) throws Exception {
		return sqlSessionTemplate.getMapper(JenkinsInfoMapper.class).selectTotalJobNm(param);
	}

	public List<HashMap<String, Object>> selectFixedCntByMonth(Map<String, String> param) throws Exception {
		return sqlSessionTemplate.getMapper(JenkinsInfoMapper.class).selectFixedCntByMonth(param);
	}

	public List<HashMap<String, Object>> selectJenkinsTotalInfo(Map<String, String> param) throws Exception {
		return sqlSessionTemplate.getMapper(JenkinsInfoMapper.class).selectJenkinsTotalInfo(param);
	}

	public List<HashMap<String, Object>> selectJenkinsJobInfo(Map<String, String> param) throws Exception {
		return sqlSessionTemplate.getMapper(JenkinsInfoMapper.class).selectJenkinsJobInfo(param);
	}

	public List<HashMap<String, Object>> selectJenkinsRegisteredSystem(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.getMapper(JenkinsInfoMapper.class).selectJobNmPivotColumns(param);
	}

	public List<HashMap<String, Object>> selectJenkinsRuleSetInfoList(Map<String, Object> param)  throws Exception {
		param.put("systemList", sqlSessionTemplate.getMapper(JenkinsInfoMapper.class).selectJobNmPivotColumns(param));
		return sqlSessionTemplate.getMapper(JenkinsInfoMapper.class).selectJenkinsRuleSetInfoList(param);
	}
}
