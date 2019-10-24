package com.shinsegae.smon.privacy;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MtrCommonMapper {
	
	List<HashMap<String, Object>> selectTeamComboList(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectSystemComboList(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectOwnerComboList(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectSaveTypeComboList(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectObjectTypeComboList(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectPersonalInfoTypeComboList(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectDataTypeComboList(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectActionTypeComboList(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectWorkComboList(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectEffectComboList(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectEffectRangeComboList(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectTargetSystemComboList(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectStatusComboList(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectDefectGradeComboList(HashMap<String, Object> params);
	
}
