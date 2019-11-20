package com.shinsegae.smon.privacy;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonalInfoDictionaryMapper {
	public List<HashMap<String, Object>> selectDictionaryTableList(HashMap<String, Object> params);
	
	public List<HashMap<String, Object>> selectDictionaryColList(HashMap<String, Object> params);
	
	public List<HashMap<String, Object>> selectComboDBName(HashMap<String, Object> params);
	
	public List<HashMap<String, Object>> selectComboOwner(HashMap<String, Object> params);
	
	public void updateColumnAddedInfo(HashMap<String, Object> params);

	public void insertPersonalInfoException(HashMap<String, Object> params);
	
	public List<HashMap<String, Object>> selectPersonalInfoProgressGraphList(HashMap<String, Object> params);

	public List<HashMap<String, Object>> selectPersonalInfoProgressGraphList2(HashMap<String, Object> params);
	
	public List<HashMap<String, Object>> selectPersonalInfoDetailList(HashMap<String, Object> params);
}
