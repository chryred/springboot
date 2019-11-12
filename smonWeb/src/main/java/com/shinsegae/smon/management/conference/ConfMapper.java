package com.shinsegae.smon.management.conference;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.shinsegae.smon.model.ConfVO;

@Mapper
public interface ConfMapper {

	// 기안서 기준 리스트 가져오기(해당 조건)
	List<HashMap<String, Object>> selectList(HashMap<String, Object> params);
	
	// 기안서 기준 리스트 가져오기(해당 조건)
	List<HashMap<String, Object>> selectList();
	
	// 진행자 정보 가져오기
	List<HashMap<String, Object>> searchHost(ConfVO confVO);
	
	// 진행자 정보 가져오기
	String confHostYN(ConfVO confVO);

	// 회의 시퀀스 가져오기
	String selectConfSeq();
	
	// 참석자 차트 데이터 가져오기
	List<HashMap<String, Object>> searchAttndList(ConfVO confVO);
	
	// 엑셀 다운로드 자료 가져오기
	List<HashMap<String, Object>> searchAttndResult(ConfVO confVO);
	
	// 진행자 차트 데이터 가져오기
	List<HashMap<String, Object>> searchHostResult(ConfVO confVO);
	
	// 회의정보 가져오기
	List<HashMap<String, Object>> selConfName(ConfVO confVO);
	
	// 기안서 기준 리스트 가져오기(해당 조건)
	List<HashMap<String, Object>> selectAttndList(ConfVO confVO);
	
	// 기안서 콤보 가져오기
	public List<HashMap<String, Object>> selectBox();
	
	//기안서 기준 저장
	void insertConf(ConfVO confVO);
	
	//참석자 리스트 저장
	void insertAttnd(ConfVO confVO);
	
	// 기안서 기준 저장
	void attndChng(ConfVO confVO);
	
	// 공유회 삭제
	void deleteConf(ConfVO confVO);
	
	// 참석자 리스트 삭제
	void deleteAttndList(ConfVO confVO);

	// 기안서 삭제
	void deleteData(ConfVO confVO);
	
	// 기안서 기준 업데이트
	void updateData(ConfVO confVO);
}
