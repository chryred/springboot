package com.shinsegae.smon.privacy;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonalInfoDestroySearchMapper {
	
	List<HashMap<String, Object>> selectDestoryTableList2(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectDestoryColList(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectComboDBName(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectComboOwner(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectComboTableName(HashMap<String, Object> params);
	
}
