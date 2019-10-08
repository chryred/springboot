package com.shinsegae.smon.adm;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.shinsegae.smon.model.AccessLogActionMapVO;
import com.shinsegae.smon.model.UrlStatisticsDTO;
import com.shinsegae.smon.model.UserAccessLogVO;

@Service
public class LogService {

	@Autowired
	@Qualifier("sqlSessionTemplatePrimary")
	SqlSessionTemplate sqlSessionTemplatePrimary;
	
	private String getcurrentUser() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		
		String userID = (String)session.getAttribute("id");
		
		return userID;
	}
	
	public int setUserLog(UserAccessLogVO userAccessLogVO) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(LogMapper.class).writeUserLog(userAccessLogVO);
	}

	public List<Map> getLogURLDistinct() throws Exception {
		return sqlSessionTemplatePrimary.getMapper(LogMapper.class).getLogURLDistinct();
	}

	public AccessLogActionMapVO getLogActionMap(AccessLogActionMapVO accessLogActionMapVO) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(LogMapper.class).getLogActionMap(accessLogActionMapVO);
	}

	public int setLogActionMap(AccessLogActionMapVO accessLogActionMapVO) throws Exception {
		accessLogActionMapVO.setRegId(getcurrentUser());
		accessLogActionMapVO.setModId(getcurrentUser());
		
		return sqlSessionTemplatePrimary.getMapper(LogMapper.class).setLogActionMap(accessLogActionMapVO);
	}
	
	

	public List<UrlStatisticsDTO> getUrlStatistics(UrlStatisticsDTO paramCondition) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(LogMapper.class).getUrlStatistics(paramCondition);
	}


	public int getCountUrlStatisticsList(UrlStatisticsDTO paramCondition) throws Exception {
		return sqlSessionTemplatePrimary.getMapper(LogMapper.class).getCountUrlStatisticsList(paramCondition);
	}
	
}
