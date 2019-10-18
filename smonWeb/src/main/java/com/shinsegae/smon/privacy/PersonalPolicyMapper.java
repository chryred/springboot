package com.shinsegae.smon.privacy;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonalPolicyMapper {

	List<HashMap<String, Object>> selectPolicyTableList(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectComboDBName(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectComboOwner(HashMap<String, Object> params);
	
}
