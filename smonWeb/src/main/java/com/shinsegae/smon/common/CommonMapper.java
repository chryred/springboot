package com.shinsegae.smon.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {
	List<HashMap<String, Object>> searchSystemCombo(Map<String,String> map);
}
