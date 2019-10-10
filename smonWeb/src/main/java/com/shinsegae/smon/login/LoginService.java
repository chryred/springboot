package com.shinsegae.smon.login;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	
	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
	SqlSessionTemplate sqlSessionTemplatePrimary;

	
	public Map<String, Object> searchUser(Map<String, Object> map) {
		return sqlSessionTemplatePrimary.getMapper(LoginMapper.class).searchUser(map);
	}
}
