package com.shinsegae.smon.support;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.shinsegae.smon.common.user.UserService;
import com.shinsegae.smon.model.UserVO;
import com.shinsegae.smon.util.NLogger;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	UserService userService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if(handler instanceof ResourceHttpRequestHandler) {
			return true;
		} 
		HttpSession session = request.getSession(false);
		boolean result = true;
		
		if(session != null) {
			// 중복로그인 체크
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			HashMap<String, Object> returnMap = new HashMap<String, Object>();
	
			Object details = null;
			
			details = session.getAttribute(session.getId());
			
			if (details instanceof UserVO) {
				String varMgrId = ((UserVO) details).getMgrId();
	
				if (varMgrId != null) {
					paramMap.put("mgrId", ((UserVO) details).getMgrId()); // 관리자ID
					paramMap.put("ssId", session.getId());
					paramMap.put("sessionCreDtm", String.valueOf(session.getCreationTime()));
					try {
						returnMap = userService.selectCurrentUserStat(paramMap);
	
						String varDupYn = (String) returnMap.get("DUP_LOGINYN"); // 중복
	
						System.out.println("DUP_LOGINYN : " + (String) returnMap.get("DUP_LOGINYN"));
						if (varDupYn.equals("Y")) {
							response.sendRedirect("/duplicate_login.do");
							result = false;
						}
					} catch (Exception e) {
						NLogger.error("----- 중복로그인 체크중 오류발생 -----" + e.toString());
					}
				}
				
				String varConfirmCertNum = (String) session.getAttribute("confirmCertNum");
	
				if (varConfirmCertNum == null) {
					result = false;
				} else {
					if (!"Y".equals(varConfirmCertNum)) {
						result = false;
					}
				}
				if (!result) {
					session.invalidate();
					response.sendRedirect("/index.do");
				}
			}
			
			return result;
		} else {
			return super.preHandle(request, response, handler);
		}
	}
	
}
