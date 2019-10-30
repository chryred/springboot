package com.shinsegae.smon.privacy;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PersonalInfoDestroyManageService {

	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
    private SqlSessionTemplate sqlSessionTemplatePrimary;
	
	public List<HashMap<String, Object>> selectDestroyTableList(HashMap<String, Object> params){
		System.out.println("PersonalInfoDestroyManageService - selectDestroyTableList2 : params - " + params);
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoDestroyManageMapper.class).selectDestroyTableList(params);
	}

	/*
	 * public List<HashMap<String, Object>> list2(HashMap<String, Object> params) {
	 * return
	 * session.selectList("dbDestroyPersonalInfoManage.selectDestroyTableList",
	 * params); }
	 */
	
	public List<HashMap<String, Object>> selectComboDBName(HashMap<String, Object> params){
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoDestroyManageMapper.class).selectComboDBName(params);
	}
	
	public List<HashMap<String, Object>> selectComboOwner(HashMap<String, Object> params){
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoDestroyManageMapper.class).selectComboOwner(params);
	}
	
	public List<HashMap<String, Object>> selectComboTbname(HashMap<String, Object> params){
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoDestroyManageMapper.class).selectComboTbname(params);
	}
	
	public List<HashMap<String, Object>> insertRowAddedInfo(HashMap<String, Object> params){
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoDestroyManageMapper.class).insertRowAddedInfo(params);
	}
	
	public int updateRowAddedInfo(HashMap<String, Object> params) {
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoDestroyManageMapper.class).updateRowAddedInfo(params);
	}
	
	public List<HashMap<String, Object>> deleteRowAddedInfo(HashMap<String, Object> params){
		return sqlSessionTemplatePrimary.getMapper(PersonalInfoDestroyManageMapper.class).deleteRowAddedInfo(params);
	}

}
