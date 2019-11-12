package com.shinsegae.smon.login;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
	Map<String, Object> searchUser(Map<String, Object> param);
	HashMap<String, Object> searchUser2(HashMap<String, Object> param);
	int newFaqReplyCount(String userId);
}
