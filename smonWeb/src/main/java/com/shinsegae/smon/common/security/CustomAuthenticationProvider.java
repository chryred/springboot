package com.shinsegae.smon.common.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.shinsegae.smon.model.UserVO;
import com.shinsegae.smon.util.NLogger;

/********************************************
 * 1. 프로젝트명 : monitoringWeb
 * 2. 패키지명   : com.sec
 * 3. 클래스명   : CustomAuthenticationProvider.java
 * 4. 작성일     : 2018.01.16 
 * 5. 작성자     : 김성일
 * 6. 설명       : Spring Security AuthenticationProvider Implement Class
 *                  UserDetailsService구현체에서 받아온 사용자 정보와 로그인 비빌번호를 체크하여 사용자정보와 권한을 UsernamePasswordAuthenticationToken에 설정한다.
 *********************************************/

@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired(required = false)
    private HttpServletRequest request;
	
	@Autowired
	private CustomUserDetailsService customUserDetailService;	
	
	@Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		LoginUserDetails loginUserDetails; /** 로그인 사용자 정보 */
		UsernamePasswordAuthenticationToken result = null; /** 토큰 정보 */
		List<GrantedAuthority> authorities =  null; /** 권한 리스트 */
		NLogger.debug("authenticate : ", "===========================================");
		
		authorities = new ArrayList<GrantedAuthority>();  // 리스트 생성
        //authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		
		String userId = (String)authentication.getPrincipal(); // 사용자 아이디
		String userPwd = (String)authentication.getCredentials(); // 사용자 비밀번호
		
		RequestAttributes attribs = RequestContextHolder.getRequestAttributes();

		if (RequestContextHolder.getRequestAttributes() != null) {
		    HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
		}
		
		try {
			loginUserDetails = (LoginUserDetails) customUserDetailService.loadUserByUsernamePw(userId, userPwd); // 인증 확인
			
			// 사용자 존재 여부
			if (loginUserDetails != null) {
				/*******************************
				 * 체크로직
				 * 1. 5회이상 비밀번호 틀렸을 경우
				 * 2. 미사용 사용자인 경우
				*******************************/ 
				{
					authorities.add(new SimpleGrantedAuthority(loginUserDetails.getRoleGroup())); // 권한 유저
				}
				
				UserVO userVO = new UserVO();
				
				userVO.setMgrId(loginUserDetails.getMgrId()); // 관리자 id
				
				customUserDetailService.sendCertNumPush(userVO); // push 보내기
			} else {
				throw new BadCredentialsException("사용자 인증처리중 오류가 발생하였습니다.");
			}
			
			result = new UsernamePasswordAuthenticationToken(userId, userPwd, authorities);
		} catch (Exception e) {
			throw new BadCredentialsException("사용자 인증처리중 오류가 발생하였습니다.", e);
		} finally {
			NLogger.debug("로그인 인증 종료");
		}
		
		return result;
	}
}
