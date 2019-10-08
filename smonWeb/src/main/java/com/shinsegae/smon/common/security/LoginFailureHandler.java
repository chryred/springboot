package com.shinsegae.smon.common.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.shinsegae.smon.common.user.UserService;
import com.shinsegae.smon.model.UserVO;
import com.shinsegae.smon.util.NLogger;

/*******************************
 * 1. 프로젝트명 : monitoringWeb
 * 2. 패키지명   : com.sec
 * 3. 클래스명   : LoginFailureHandler.java
 * 4. 작성일     : 2018.01.30
 * 5. 작성자     : 김성일
 * 6. 설명       : Spring Security AuthenticationFailureHandler Implement Class 스프링 시큐리티 로그인 인증 실패 시 핸들러이다.
 ********************************/

public class LoginFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	private UserService userService;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		PrintWriter out   = null;
        String returnFlag = "USER_NOT_MATCHED";
        
        
        //String varMgrId = (String) exception.getAuthentication().getPrincipal();
        String varMgrId = request.getParameter("userId");
        
        HashMap<String, Object> paramMap = new HashMap<String, Object>();

		try {
			NLogger.info("로그인 아이디 : ", varMgrId);
			NLogger.info("로그인 실패 = ", exception.getMessage());
			
			paramMap.put("mgrId", varMgrId); // 사용자 아이디
			
			UserVO userVO = userService.getUser(paramMap); // 사용자 가져오기
			
			Integer varLoginFailCnt = userVO.getLoginFailCnt(); // 로그인 실패 횟수
			
			if (varLoginFailCnt >= 5) {
				paramMap.put("mgrStateCd", "N"); // 계정 미사용 처리
			}
			
			paramMap.put("successYn", "N"); // 로그인 실패
			userService.updateUser(paramMap);

			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			out.print(returnFlag);
			out.flush();
			out.close();
			
		} catch (IOException e) {
			//e.printStackTrace();
			NLogger.error("로그인 실패 = " + e.getMessage());
		} catch (Exception e) {
			//e.printStackTrace();
			NLogger.error("로그인 실패 = " + e.getMessage());
		} finally {
			if (out != null) out.close();
		}
	}
}
