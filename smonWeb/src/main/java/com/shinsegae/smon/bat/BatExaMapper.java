package com.shinsegae.smon.bat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.shinsegae.smon.model.bat.DsBatchVO;

@Mapper
public interface BatExaMapper {
	List<DsBatchVO> getSossBatchList(DsBatchVO paramCondition) throws Exception;
	List<DsBatchVO> getDsFolderList(DsBatchVO paramCondition) throws Exception;
	List<DsBatchVO> getDsBatchList(DsBatchVO paramCondition) throws Exception;
	List<DsBatchVO> getSimpleSossBatchList(DsBatchVO paramCondition) throws Exception;
	int getCountSimpleSossBatchList(DsBatchVO paramCondition) throws Exception;
}
