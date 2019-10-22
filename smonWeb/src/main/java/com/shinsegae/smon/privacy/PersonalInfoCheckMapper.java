package com.shinsegae.smon.privacy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Mapper
public interface PersonalInfoCheckMapper {

	List<HashMap<String, Object>> selectPersonalInfoList(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectComboDBName(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectComboOwner(HashMap<String, Object> params);
	
	int insertPersonalInfoException(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectPersonalInfoProgressGraphList(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectPersonalInfoProgressGraphList2(HashMap<String, Object> params);
	
	List<HashMap<String, Object>> selectPersonalInfoDetailList(HashMap<String, Object> params);
	
}

