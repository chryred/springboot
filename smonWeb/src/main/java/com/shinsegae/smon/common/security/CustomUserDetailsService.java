package com.shinsegae.smon.common.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shinsegae.smon.common.user.UserService;
import com.shinsegae.smon.model.CubeoneVO;
import com.shinsegae.smon.model.UserVO;
import com.shinsegae.smon.support.exception.CubeoneException;
import com.shinsegae.smon.util.ActionBlossomPush;
import com.shinsegae.smon.util.CubeoneAPIHandler;
import com.shinsegae.smon.util.GenerateRandomNum;
import com.shinsegae.smon.util.NLogger;

/********************************************
 * 1. 프로젝트명 : monitoringWeb
 * 2. 패키지명   : com.sec
 * 3. 클래스명   : CustomAuthenticationProvider.java
 * 4. 작성일     : 2018.01.18 
 *  
 * 5. 작성자     : 김성일
 * 6. 설명       : Spring Security UserDetailsService Implement Class 로그인ID로 DB에서 사용자를 조회하여 사용자 기본정보를 UserDetails구현체에 설정한다.
 *********************************************/

@Service
public class CustomUserDetailsService implements UserDetailsService {
	/** Common Log */
    @Autowired
	private UserService userService;

    @Override
	public UserDetails loadUserByUsername(String userName)	throws UsernameNotFoundException {
    	LoginUserDetails loginUserDetails = null;
    	UserVO userVO;
    	
		HashMap<String, Object> paramVO = new HashMap<String, Object>();
		
		paramVO.put("mgrId", userName); // 사용자 아이디

		try {
			userVO = userService.getUser(paramVO); // 사용자 조회
		} catch (Exception e) {
			throw new UsernameNotFoundException("사용자정보 조회시 오류가 발생하였습다.", e);
		}

		if (userVO != null) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();    
	        authorities.add(new SimpleGrantedAuthority(userVO.getRoleGroup()));			
	
	        loginUserDetails = new LoginUserDetails ((String) userVO.getMgrId(), (String) userVO.getMgrPwd(), (String) userVO.getMgrName(), authorities);
	        
	        loginUserDetails.setMgrId(userVO.getMgrId());
	        loginUserDetails.setMgrPwd(userVO.getMgrPwd());
	        loginUserDetails.setMgrName(userVO.getMgrName());
		}
		
		return loginUserDetails;
	}
    
    public UserDetails loadUserByUsernamePw(String userName,String userPw)	throws UsernameNotFoundException, CubeoneException {
		LoginUserDetails loginUserDetails = null;
		UserVO userVO = null;

		HashMap<String, Object> paramVO = new HashMap<String, Object>();
		
		paramVO.put("mgrId", userName); // 사용자 아이디
		paramVO.put("userPwd", CubeoneAPIHandler.getInstance().callCubeoneAPI("coencbyte", CubeoneVO.builder().msg(userPw).crudLog(10).itemCd("PWD").tableName("MGR").columnName("MGR_PWD").build())); // 사용자 비밀번호

		try {
			userVO = userService.getUser(paramVO); // 사용자 조회
			NLogger.debug("userVO : ", userVO.toString());
		} catch (Exception e) {
			throw new UsernameNotFoundException("사용자정보 조회시 오류가 발생하였습다.", e);
		}

		if (userVO != null) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();    
	        authorities.add(new SimpleGrantedAuthority(userVO.getRoleGroup()));			
	
	        loginUserDetails = new LoginUserDetails ((String) userVO.getMgrId(), (String) userVO.getMgrPwd(), (String) userVO.getMgrName(), authorities);
	        
	        loginUserDetails.setMgrId(userVO.getMgrId());
	        loginUserDetails.setMgrPwd(userVO.getMgrPwd());
	        loginUserDetails.setMgrName(userVO.getMgrName());
	        
	        // 추후 사용자 정보 추가설정

			// 카운터 수정 (정상로그인시 오류횟수 0 으로 수정)
			// 로그인 성공 여부
			paramVO.put("successYn", "Y");
			// loginService.updateUserLoginTry(map);
		} else {
			/***************************************
			 * 아이디, 패스워드5회 오류시 미사용처리 방안 
			 * 1) 사용자관리에 오류횟수 카운터 LOGINTRY_CNT(number) default 0 필드추가(화면에 표시) 
			 * 2) 오류시 현재카운터가 3 이하면 카운터 +1 업데이트 
			 * 3) 오류시 현재카운터가 4 이상이면 카운터 +1 업데이트후 미사용 처리 
			 * 4) 정상로그인시 카운터가 1 이상이면 카운터 0으로 초기화 
			 * 5) 사용자관리에서 미사용을 사용처리시 카운터 0으로 초기화
			 ***************************************/
			// 현재카운터 조회
			// int logintryCnt = loginService.selectUserLoginTryCnt(map);
			/*
			 * if(logintryCnt > 3){ // 미사용처리 map.put("useYn", "N"); }else{ map.put("useYn",
			 * "Y"); }
			 */

			// 로그인 성공 여부
			paramVO.put("successYn", "N");

			// 카운터 수정
			// loginService.updateUserLoginTry(map);
		}

		return loginUserDetails;
	}
    
	/*******************************
	 * 인증번호 보내기
	 * @param String
	 * @return String
	 *******************************/
	public String sendCertNumPush(UserVO userVO) {
		String result = "false";
		
		try {
			//난수 발생
			String randomNum = String.valueOf(GenerateRandomNum.generateNumber(6));
			
			HashMap<String, Object> paramVO = new HashMap<String, Object>();
			
			paramVO.put("mgrId", userVO.getMgrId()); // 관리자ID
			paramVO.put("certNum", randomNum); // 인증번호
			
			String message = "본인 확인을 위해 인증번호["+randomNum+"]를 입력해 주세요!";
			ActionBlossomPush.actionBlossomPush(userVO.getMgrId(), message, randomNum);
			
			userService.updateCertNum(paramVO);
			
			result = "true";
		} catch (Exception e) {
			NLogger.error(e.toString());
		}
		
		return result;
	}	
}
