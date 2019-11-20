package com.shinsegae.smon.adm;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.shinsegae.smon.util.DataMap;
import com.shinsegae.smon.util.RequestMap;

@Mapper
public interface UserIpMapper {
	/**
	 * IP 리스트 조회
	 */
	@SuppressWarnings("rawtypes")
	List<DataMap> selectUserIp(RequestMap param);
	
	
	/**
	 * 가능한 IP 조회
	 */
	@SuppressWarnings("rawtypes")
	List<DataMap> selectAvailableIp(RequestMap param);
	
	/**
	 * 신규 IP 발급
	 */
	@SuppressWarnings("rawtypes")
	int insertNewIp(RequestMap param) throws Exception;
	
	/**
	 * IP 정보 수정
	 */
	@SuppressWarnings("rawtypes")
	int updateIp(RequestMap param) throws Exception;
	
	/**
	 * IP 정보 삭제
	 */
	@SuppressWarnings("rawtypes")
	int deleteIp(RequestMap param) throws Exception;
	
	/**
	 * IP 비밀번호 체크
	 */
	@SuppressWarnings("rawtypes")
	String confirmPasswdYn(RequestMap param) throws Exception;

	/**
	 * IP 이력 저장
	 */
	@SuppressWarnings("rawtypes")
	int insertIpHistory(RequestMap param) throws Exception;

}
