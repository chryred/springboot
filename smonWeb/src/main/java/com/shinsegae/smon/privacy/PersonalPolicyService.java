package com.shinsegae.smon.privacy;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PersonalPolicyService {

	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
    private SqlSessionTemplate sqlSessionTemplatePrimary;
	
	public List<HashMap<String, Object>> selectPolicyTableList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalPolicyMapper.class).selectPolicyTableList(params);
	}
	
	public List<HashMap<String, Object>> selectComboDBName(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalPolicyMapper.class).selectComboDBName(params);
	}
	
	public List<HashMap<String, Object>> selectComboOwner(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalPolicyMapper.class).selectComboOwner(params);
	}
}
