package com.shinsegae.smon.privacy;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PersonalInfoDestroySearchService {
	
	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
    private SqlSessionTemplate sqlSessionTemplatePrimary;
	
	public List<HashMap<String, Object>> selectDestoryTableList2(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoDestroySearchMapper.class).selectDestoryTableList2(params);
	}
	public List<HashMap<String, Object>> selectDestoryColList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoDestroySearchMapper.class).selectDestoryColList(params);
	}
	
	public List<HashMap<String, Object>> selectComboDBName(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoDestroySearchMapper.class).selectComboDBName(params);
	}	
	public List<HashMap<String, Object>> selectComboOwner(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoDestroySearchMapper.class).selectComboOwner(params);
	}
	public List<HashMap<String, Object>> selectComboTableName(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoDestroySearchMapper.class).selectComboTableName(params);
	}
	
}
