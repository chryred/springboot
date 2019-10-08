package com.shinsegae.smon.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
	List<HashMap<String, Object>> searchUser(Map<String, Object> param);
}
