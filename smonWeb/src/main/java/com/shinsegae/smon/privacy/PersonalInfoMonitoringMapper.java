package com.shinsegae.smon.privacy;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonalInfoMonitoringMapper {

	public List<HashMap<String, Object>> selectPersonalInfoList(HashMap<String, Object> params); 
	
	public List<HashMap<String, Object>> selectComboDBName(HashMap<String, Object> params);
	
	public List<HashMap<String, Object>> selectComboOwner(HashMap<String, Object> params);

	public void updatePersonalInfoException(HashMap<String, Object> params); 
	
	public List<HashMap<String, Object>> selectPersonalInfoProgressGraphList(HashMap<String, Object> params);

	public List<HashMap<String, Object>> selectPersonalInfoProgressGraphList2(HashMap<String, Object> params); 
	
	public List<HashMap<String, Object>> selectPersonalInfoDetailList(HashMap<String, Object> params); 
}
