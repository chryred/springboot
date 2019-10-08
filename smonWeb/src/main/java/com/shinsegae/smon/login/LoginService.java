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

	
	public void searchUser() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mgr_id", "173917");
		
		NLogger.debug(sqlSessionTemplatePrimary.getMapper(LoginMapper.class).searchUser(map));
	}
}
