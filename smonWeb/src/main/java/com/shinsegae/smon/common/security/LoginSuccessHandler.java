package com.shinsegae.smon.common.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.shinsegae.smon.common.user.UserService;
import com.shinsegae.smon.model.UserVO;

/*******************************
 * 1. 프로젝트명 : monitoringWeb
 * 2. 패키지명   : com.sec
 * 3. 클래스명   : LoginSuccessHandler.java
 * 4. 작성일     : 2018.01.18
 * 5. 작성자     : 김성일
 * 6. 설명       : Spring Security AuthenticationSuccessHandler Implement Class 스프링 시큐리티 로그인 인증 정상처리시 핸들러이다.
 ********************************/

public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
		
		String returnFlag = "USER_LOGIN_OK";

		PrintWriter out = null;
		UserVO userVO = null;

		try {
			if (auth.getDetails() == null) {
				returnFlag = "USER_NOT_FOUND";
			} else {
				// 세션ID 설정
				HttpSession session = request.getSession(true);
				
				session.setMaxInactiveInterval(30*60); // 초단위
				
				String userId = (String) auth.getPrincipal(); // 사용자 ID
				
				HashMap<String, Object> paramVO = new HashMap<String, Object>();
				
				paramVO.put("mgrId", userId);
				paramVO.put("mgrStateCd", "Y"); // 사용중인 계정만 확인
				
				userVO = userService.getUser(paramVO); // 사용자 정보 
				
				userVO.setSid(session.getId()); // 세션 아이디
				userVO.setUserIp(request.getRemoteAddr()); // 사용자 IP
				userVO.setUserHostName(InetAddress.getLocalHost().getHostName()); // 호스트명

				session.setAttribute(session.getId(), userVO);
				session.setAttribute("userVO", userVO);
				
				userVO.setLogType("1"); // 로그타입(1:로그인, 2:로그아웃)
				userVO.setLoginIp(request.getRemoteAddr()); // 로그인아이피
				userVO.setLoginSsid(session.getId()); // 로그인 session id
				
				userService.insertMgrLoginHis(userVO); // 로그인 이력 남기기
				
				paramVO.put("successYn", "Y");
				paramVO.put("sessionCreDtm", String.valueOf(session.getCreationTime()));
				paramVO.put("loginSsid", session.getId());
				
				userService.updateUser(paramVO);
				
				logger.info("로그인 성공");
			}

			response.setCharacterEncoding("UTF-8");
			//response.sendRedirect("/index.do");
			
			out = response.getWriter();
			out.print(returnFlag);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("로그인 실패 = " + e.toString());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
