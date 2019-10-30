package com.shinsegae.smon.adm;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shinsegae.smon.util.DataMap;
import com.shinsegae.smon.util.RequestMap;

@Service
@Transactional
public class UserIpService {
	
	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
	SqlSessionTemplate sqlSessionTemplatePrimary;

	/**
	 * 유저 IP 조회
	 */
	@SuppressWarnings({"rawtypes"})
	public List<DataMap> selectUserIp(RequestMap param) {
		return sqlSessionTemplatePrimary.getMapper(UserIpMapper.class).selectUserIp(param);		
	}
	
	/**
	 * 가능한 IP 조회
	 */
	@SuppressWarnings({"rawtypes"})
	public List<DataMap> selectAvailableIp(RequestMap param) {
		return sqlSessionTemplatePrimary.getMapper(UserIpMapper.class).selectAvailableIp(param);	
	}
	
	/**
	 * 신규 IP 발급
	 */
	@SuppressWarnings({"rawtypes"})
	public int insertNewIp(RequestMap param) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(UserIpMapper.class).insertNewIp(param);	
	}
	
	/**
	 * IP 정보 수정
	 */
	@SuppressWarnings({"rawtypes"})
	public int updateIp(RequestMap param) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(UserIpMapper.class).updateIp(param);	
	}
	
	/**
	 * IP 정보 삭제
	 */
	@SuppressWarnings({"rawtypes"})
	public int deleteIp(RequestMap param) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(UserIpMapper.class).deleteIp(param);	
	}
	
	/**
	 * IP 비밀번호 체크
	 */
	@SuppressWarnings({"rawtypes"})
	public String confirmPasswdYn(RequestMap param) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(UserIpMapper.class).confirmPasswdYn(param);	
	}
	
	/**
	 * IP 변경이력
	 */
	@SuppressWarnings({"rawtypes"})
	public int insertIpHistory(RequestMap param) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(UserIpMapper.class).insertIpHistory(param);	
	}
}
