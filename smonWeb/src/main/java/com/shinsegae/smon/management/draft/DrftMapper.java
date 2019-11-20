package com.shinsegae.smon.management.draft;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.shinsegae.smon.model.DrftVO;


@Mapper
public interface DrftMapper {

	// 기안서 기준 리스트 가져오기(해당 조건)
	List<HashMap<String, Object>> selectList(HashMap<String, Object> params);
	
	
	// 기안서 기준 리스트 가져오기(해당 조건)
	List<HashMap<String, Object>> selectList();
	
	// 기안서 콤보 가져오기
	List<HashMap<String, Object>> selectBox();
	
	// 기안서 기준 저장
	void insertData(DrftVO drftVO);
	
	// 기안서 삭제
	void deleteData(DrftVO drftVO);
	
	// 기안서 기준 업데이트
	void updateData(DrftVO drftVO);
}
