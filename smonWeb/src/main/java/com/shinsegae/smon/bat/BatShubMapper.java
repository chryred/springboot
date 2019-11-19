package com.shinsegae.smon.bat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.shinsegae.smon.model.bat.DsBatchVO;

@Mapper
public interface BatShubMapper {
	List<DsBatchVO> getShubBatchFolderList(DsBatchVO paramCondition) throws Exception;
	List<DsBatchVO> getShubBatchList(DsBatchVO paramCondition) throws Exception;
}
