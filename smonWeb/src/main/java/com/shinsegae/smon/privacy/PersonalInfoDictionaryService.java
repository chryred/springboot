package com.shinsegae.smon.privacy;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PersonalInfoDictionaryService {

	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
    SqlSessionTemplate sqlSessionTemplatePrimary;
	
	public List<HashMap<String, Object>> selectDictionaryTableList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoDictionaryMapper.class).selectDictionaryTableList(params);
	}
	
	public List<HashMap<String, Object>> selectDictionaryColList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoDictionaryMapper.class).selectDictionaryColList(params);
	}

	public List<HashMap<String, Object>> selectComboDBName(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoDictionaryMapper.class).selectComboDBName(params);
	}
	
	public List<HashMap<String, Object>> selectComboOwner(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoDictionaryMapper.class).selectComboOwner(params);
	}
	
	public void updateColumnAddedInfo(List<HashMap<String, Object>> paramsList) {
		Iterator<HashMap<String, Object>> iter = paramsList.iterator();
		while(iter.hasNext()) {
			HashMap<String, Object> params = iter.next();
			sqlSessionTemplatePrimary.getMapper(PersonalInfoDictionaryMapper.class).updateColumnAddedInfo(params);
		}
	}

	public void insertPersonalInfoException(List<HashMap<String, Object>> paramsList) {
		Iterator<HashMap<String, Object>> iter = paramsList.iterator();
		while(iter.hasNext()) {
			HashMap<String, Object> params = iter.next();
			sqlSessionTemplatePrimary.getMapper(PersonalInfoDictionaryMapper.class).insertPersonalInfoException(params);
		}
	}
	
	public List<HashMap<String, Object>> selectPersonalInfoProgressGraphList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoDictionaryMapper.class).selectPersonalInfoProgressGraphList(params);
	}

	public List<HashMap<String, Object>> selectPersonalInfoProgressGraphList2(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoDictionaryMapper.class).selectPersonalInfoProgressGraphList2(params);
	}
	
	public List<HashMap<String, Object>> selectPersonalInfoDetailList(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoDictionaryMapper.class).selectPersonalInfoDetailList(params);
	}

}
