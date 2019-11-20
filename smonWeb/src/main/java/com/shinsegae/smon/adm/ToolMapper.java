package com.shinsegae.smon.adm;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.shinsegae.smon.model.tool.SystemCodeVO;

@Mapper
public interface ToolMapper {
	List<SystemCodeVO> getSystemCodes(SystemCodeVO paramVO) throws Exception;
}
