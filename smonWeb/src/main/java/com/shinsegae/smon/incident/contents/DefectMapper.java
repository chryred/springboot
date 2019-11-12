package com.shinsegae.smon.incident.contents;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DefectMapper {

	// 장애 증적 관리 리스트 가져오기(해당 조건)
	List<HashMap<String, Object>> selectList(HashMap<String, Object> params);
	
	// 장애 증적 관리 리스트에 데이터 삽입(해당 조건)
	void insertData(HashMap<String, Object> params);

	// 장애 증적 관리 리스트에 데이터 삽입(해당 조건)
	void updateStatus(HashMap<String, Object> params);
}
