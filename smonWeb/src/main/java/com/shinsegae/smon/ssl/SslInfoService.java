package com.shinsegae.smon.ssl;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SslInfoService {
	
	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
	SqlSessionTemplate sqlSessionTemplatePrimary;
    
	
	public List<HashMap<String, Object>> sslInfoDataTables() throws Exception{
		// TODO Auto-generated method stub
		return sqlSessionTemplatePrimary.getMapper(SslInfoMapper.class).sslInfoDataTables();
	}

}
