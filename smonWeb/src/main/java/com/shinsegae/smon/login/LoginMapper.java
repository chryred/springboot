package com.shinsegae.smon.login;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
	Map<String, Object> searchUser(Map<String, Object> param);
}
