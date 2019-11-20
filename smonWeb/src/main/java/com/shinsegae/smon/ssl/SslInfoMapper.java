package com.shinsegae.smon.ssl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SslInfoMapper {

	List<HashMap<String, Object>> sslInfoDataTables() throws Exception;
	

}
