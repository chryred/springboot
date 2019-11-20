package com.shinsegae.smon.security.code;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface JenkinsInfoMapper {
	
	List<HashMap<String, Object>> selectTotalJobNm(Map<String, String> param) throws Exception;

	List<HashMap<String, Object>> selectFixedCntByMonth(Map<String, String> param) throws Exception;

	List<HashMap<String, Object>> selectJenkinsTotalInfo(Map<String, String> param) throws Exception;

	List<HashMap<String, Object>> selectJenkinsJobInfo(Map<String, String> param) throws Exception;

	List<HashMap<String, Object>> selectJobNmPivotColumns(Map<String, Object> param) throws Exception;

	List<HashMap<String, Object>> selectJenkinsRuleSetInfoList(Map<String, Object> param) throws Exception;
}
