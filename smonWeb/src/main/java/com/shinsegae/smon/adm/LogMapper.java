package com.shinsegae.smon.adm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.shinsegae.smon.model.AccessLogActionMapVO;
import com.shinsegae.smon.model.UrlStatisticsDTO;
import com.shinsegae.smon.model.UserAccessLogVO;

@Mapper
public interface LogMapper {
	
	
	int writeUserLog(UserAccessLogVO userAccessLogVO) throws Exception;

	List<Map> getLogURLDistinct() throws Exception;

	AccessLogActionMapVO getLogActionMap(AccessLogActionMapVO accessLogActionMapVO) throws Exception;

	
	int setLogActionMap(AccessLogActionMapVO accessLogActionMapVO) throws Exception;

	List<UrlStatisticsDTO> getUrlStatistics(UrlStatisticsDTO paramCondition) throws Exception;

	int getCountUrlStatisticsList(UrlStatisticsDTO paramCondition) throws Exception;
}
