package com.shinsegae.smon.privacy;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonalInfoDestroyManageMapper {
	
	List<HashMap<String, Object>> selectDestroyTableList2(HashMap<String, Object> params);

	/*
	 * public List<HashMap<String, Object>> list2(HashMap<String, Object> params) {
	 * return
	 * session.selectList("dbDestroyPersonalInfoManage.selectDestroyTableList",
	 * params); }
	 */
	
	List<HashMap<String, Object>> selectComboDBName(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectComboOwner(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectComboTbname(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> insertRowAddedInfo(HashMap<String, Object> params);
	
	int updateRowAddedInfo(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> deleteRowAddedInfo(HashMap<String, Object> params);

}
