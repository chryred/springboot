package com.shinsegae.smon.login;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.shinsegae.smon.util.NLogger;

@Service
public class LoginService {
	
	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
	SqlSessionTemplate sqlSessionTemplatePrimary;

	
	public Map<String, Object> searchUser(Map<String, Object> map) {
		return sqlSessionTemplatePrimary.getMapper(LoginMapper.class).searchUser(map);
	}
	
	public HashMap<String, Object> oneUser(HashMap<String, Object> params) throws Exception{
		NLogger.info("----- call oneNotice in NoticeDAO -----");
		
		return sqlSessionTemplatePrimary.getMapper(LoginMapper.class).searchUser2(params);
	}
	
	public int newFaqReplyCount(String userId){
		NLogger.info("----- call newFaqReplyCount in FaqDAO -----");
		
		return sqlSessionTemplatePrimary.getMapper(LoginMapper.class).newFaqReplyCount(userId);
	}
}
