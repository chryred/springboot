package com.shinsegae.smon.login;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae.smon.common.user.UserService;
import com.shinsegae.smon.model.UserVO;
import com.shinsegae.smon.util.ActionBlossomPush;
import com.shinsegae.smon.util.GenerateRandomNum;
import com.shinsegae.smon.util.NLogger;


@Controller
public class LoginContoller {

	@Autowired
	LoginService loginService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcomeUrl(Locale locale, Model model) {
		return "redirect:/index.do";
	}	
	
	/*******************************
	 * 인덱스 화면
	 * @return ModelAndView
	 *******************************/
	@RequestMapping(value="/index.do")
    public ModelAndView securityLoginView(Locale locale, Model model, @RequestParam(value="type", required=false) String type) {
        ModelAndView mav = new ModelAndView("login/securityLogin");
        
        if (type != null) {
        	mav.addObject("type", type);
        }
        
        return mav;
    }
	
	/*******************************
	 * 접근 불가
	 * @return ModelAndView
	 *******************************/
	@RequestMapping(value="/access_denied.do")
	public ModelAndView accessDenied(ModelAndView mav)throws Exception {
		mav.setViewName("login/accessDenied");
		return mav;
	}
	
	/*******************************
	 * 세션 타임아웃
	 * @return ModelAndView
	 *******************************/
	@RequestMapping(value="/session_timeout.do")
	public String sessionTimeout(ModelAndView mav)throws Exception {
		return "login/sessionTimeout";
	} 	
	
	/*******************************
	 * 로그아웃
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 *******************************/
	@RequestMapping(value="/do_logout.do")
    public ModelAndView doLogout(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView("login/logout");
        
        // 로그인 세션 정보 설정
		HttpSession session = request.getSession(false);
		
		if (session == null) {
			NLogger.info("Logout Session is NULL!!!");
		} else {
			UserVO userVO = (UserVO) session.getAttribute("userVO"); 

			if (userVO != null) {
				try {
					userVO.setLogType("2"); // 로그타입(1:로그인, 2:로그아웃)
					userVO.setLoginIp(request.getRemoteAddr()); // 로그인아이피
					userVO.setLoginSsid(session.getId()); // 로그인 session id
					
					userService.insertMgrLoginHis(userVO); // 로그인 이력 남기기
				} catch (Exception e)  {
					NLogger.error(e.getMessage());
				}
			}
				
			session.invalidate();
		}
		
        return view;
    }
	
	/*******************************
	 * 중복 로그인
	 * @return ModelAndView
	 *******************************/
	@RequestMapping(value="/duplicate_login.do")
	public String duplicateLogin(ModelAndView mav)throws Exception {
		return "login/duplicateLogin";
	}	
	
	/*******************************
	 * 중복 로그인
	 * @return ModelAndView
	 *******************************/
	@RequestMapping(value="/error.do")
	public String error(ModelAndView mav)throws Exception {
		return "login/errorPage";
	}
	
	/*******************************
	 * 시큐리티 로그인
	 * @return ModelAndView
	 *******************************/
    @RequestMapping(value="/confirmCertNum.do")
    @ResponseBody
    public ModelAndView confirmCertNum(HttpServletRequest request, UserVO paramUserVO) {
    	// 로그인 세션 정보 설정
    	HttpSession session = request.getSession(false);
    	
    	ModelAndView mav = new ModelAndView();
		
		mav.setViewName("jsonView"); 
    	
    	try {
    		HashMap<String, Object> paramVO = new HashMap<String, Object>();
    		
    		String varMgrId = paramUserVO.getMgrId(); // 아이디
    		String varMgrPwd = paramUserVO.getMgrPwd(); // 비밀번호
    		String varCertNum = paramUserVO.getCertNum(); // 인증번호
    		
    		if (varMgrId == null || varMgrId.equals("")) {
    			throw new Exception("아이디는 필수 값 입니다.");
    		}
    		
    		if (varMgrPwd == null || varMgrPwd.equals("")) {
    			throw new Exception("비밀번호는 필수 값 입니다.");
    		}
    		
    		if (varCertNum == null || varCertNum.equals("")) {
    			throw new Exception("인증번호는 필수 값 입니다.");
    		}
    		
    		paramVO.put("mgrId", varMgrId);
    		paramVO.put("mgrPwd", varMgrPwd);
    		paramVO.put("certNum", varCertNum);
    		
    		UserVO userVO = userService.getUser(paramVO);
    		
    		if (userVO == null) {
    			throw new Exception("인증번호가 일치하지 않습니다.");
    		}
    		
    		session.setAttribute("confirmCertNum", "Y"); // 인증완료시 체크
    		
    		mav.addObject("chkResult", "OK");
		} catch (Exception e) {
			SecurityContextHolder.getContext().setAuthentication(null);								
			mav.addObject("chkResult", "FAIL");
			mav.addObject("errorMsg", e.toString());
		}
    	
    	return mav;
    }
    
    /*******************************
	 * 메인 페이지로 이동
	 * @return ModelAndView
	 *******************************/
    @RequestMapping(value="/main.do")
    public String main(HttpServletRequest request) {
    	return "index";
    }
    
	/**
	 * 비밀번호 변경 전 정합성 체크
	 * @param userVO
	 * @return
	 */
	@RequestMapping(value = "/confirmEmpPwd.do")
	public @ResponseBody Map<String, Object> confirmEmpPwd(UserVO userVO) {

		NLogger.debug("confirmEmpPwd 비밀번호 체크로직 시작");

		Map<String, Object> jsonResult = new HashMap<String, Object>();

		int userPwdHistCnt = 0;
		String checkOldPwd = "N"; 

		try {
			
			/**
			 * =============================================== 
			 * // 기존 비밀번호 체크
			 * ================================================
			 **/
			
			checkOldPwd = userService.checkUserPassword(userVO);

			/**
			 * =============================================== 
			 * // 비밀번호 작성규칙 체크(김성일 담당님 소스)
			 * ================================================
			 **/
			
			HashMap<String, Object> paramVO = new HashMap<String, Object>();
			
			String varMgrId = userVO.getMgrId(); // 아이디
    		String varMgrPwd = userVO.getMgrPwd(); // 패스워드
    		String varMgrPwd2 = userVO.getMgrPwd2(); // 패스워드 확인
    		
    		paramVO.put("mgrId", varMgrId);
    		
    		HashMap<String, Object> paramMap = new HashMap<String, Object>();
			
			/***************************
    		 * 1. 패스워드 동일여부 파라미터 세팅
    		 **************************/
    		HashMap<String, Object> paramMapSub1 = new HashMap<String, Object>();
    		
    		paramMapSub1.put("password", varMgrPwd); // 비밀번호
    		paramMapSub1.put("password2", varMgrPwd2); // 비밀번호 확인
    		
    		paramMap.put("PASSWORD_DIFFERENCE", paramMapSub1);
    		
			/***************************
    		 * 2. 비밀번호 생성규칙(정규식)
    		 * - 3가지 조합 8자리 이상
    		 * - 연속된 숫자, 문자
    		 **************************/
    		HashMap<String, Object> paramMapSub2 = new HashMap<String, Object>();
    		
    		paramMapSub2.put("password", varMgrPwd);
    		
    		paramMap.put("PASSWORD_COMBINATION", paramMapSub2);
    		
    		/***************************
    		 * 3. 비밀번호 생성규칙
    		 *    - 사용자 연관된 정보(생일, 전화번호, 아이디, 사번, 이메일ID 등 연관된 비밀번호)
    		 **************************/
    		List<HashMap<String, Object>> wordMaps = userService.getAssociatedWordMap(paramVO); // 연관된 단어
    		HashMap<String, Object> paramMapSub3 = new HashMap<String, Object>();
    		
    		paramMapSub3.put("password", varMgrPwd);
    		paramMapSub3.put("wordMaps", wordMaps);
    		
    		paramMap.put("PASSWORD_ASSOCIATED", paramMapSub3);
    		
    		HashMap<String, Object> returnMap = userService.confirmPwd(paramMap); // 패스워드 확인
    		
    		String varResultCode = (String) returnMap.get("resultCode");
    		String varResultMsg = (String) returnMap.get("resultMsg");

			/**
			 * =============================================== 
			 * // 비밀번호 이력 체크
			 * ================================================
			 **/
			userPwdHistCnt = userService.checkUserPasswordHist(userVO);
            
			if(checkOldPwd.equals("N")) {
				/** 에러코드 전달 */
				jsonResult.put("chkResultCode", "ERROR");
				jsonResult.put("errorMsg", "기존 비밀번호를 확인해주세요.");
			} else if (!varResultCode.equals("0000")) { /** 비밀번호 정합성 문제 시 */
				/** 에러코드 전달 */
				jsonResult.put("chkResultCode", "ERROR");
				jsonResult.put("errorMsg", varResultMsg);
			} else if (userPwdHistCnt > 0) { /** 기존 사용된 패스워드의 경우 */
				/** 에러코드 전달 */
				jsonResult.put("chkResultCode", "ERROR");
				jsonResult.put("errorMsg", "최근 3회 사용된 비밀번호입니다.\n다른 비밀번호를 입력해주세요.");
			} else {
				/** 정상코드 전달 */
				jsonResult.put("chkResultCode", "OK");
				jsonResult.put("errorMsg", "비밀번호 정합성 체크 완료");
			}

		} catch (Exception e) {
			NLogger.error("비밀번호 체크로직 오류 : " + e);
			/** 에러코드 전달 */
			jsonResult.put("chkResultCode", "ERROR");
			jsonResult.put("errorMsg", "비밀번호 정합성 체크 중 오류가 발생했습니다.");
		} finally {
			NLogger.debug("confirmEmpPwd 비밀번호 체크로직 종료");
		}

		return jsonResult;
	}

	/**
	 * 비밀번호 변경
	 * @param userVO
	 * @return
	 */
	@RequestMapping(value = "/updateEmpPwd.do")
	public @ResponseBody Map<String, Object> updateEmpPwd(UserVO userVO) {

		NLogger.debug("updateEmpPwd 비밀번호 변경 시작");

		Map<String, Object> jsonResult = new HashMap<String, Object>();

		int userPwdHistCnt = 0;

		/**
		 * =============================================== 
		 * // 비밀번호 변경 시작 & 이력 저장
		 * ================================================
		 **/
		try {
			userService.updateUserPassword(userVO);                         // 비밀번호 변경
			userService.modifyUserPasswordHist(userVO, 0, "I");             // 비밀번호 이력 추가
			userVO.setMgrPwd("");
			userPwdHistCnt = userService.checkUserPasswordHist(userVO);     // 이력횟수 조회
			userService.modifyUserPasswordHist(userVO, userPwdHistCnt, "D");// 비밀번호 이력 삭제

			/** 정상코드 전달 */
			jsonResult.put("chkResultCode", "OK");
			jsonResult.put("errorMsg", "비밀번호 변경이 완료되었습니다.");

		} catch (Exception e) {
			NLogger.error("비밀번호 변경 오류 : " + e);
			/** 에러코드 전달 */
			jsonResult.put("chkResultCode", "ERROR");
			jsonResult.put("errorMsg", "비밀번호 변경 중 오류가 발생했습니다.");
		} finally {
			NLogger.debug("updateEmpPwd 비밀번호 변경 종료");
		}

		return jsonResult;
	}

	/**
	 * 임시 비밀번호 발급
	 * @param userVO
	 * @return
	 */
	@RequestMapping(value = "/sendUserTmpPassword.do")
	public @ResponseBody Map<String, Object> sendUserTmpPassword(UserVO userVO) {

		NLogger.debug("임시 비밀번호 발급 시작");

		Map<String, Object> jsonResult = new HashMap<String, Object>();

		/**
		 * =============================================== 
		 * // 사용자 확인 & 임시비밀번호 발급
		 * ================================================
		 **/
		try {
			// 사용자 정보 조회
			HashMap<String, Object> mapUserInfo = userService.selectUserPasswordInitInfo(userVO);

			if (mapUserInfo != null) {
				// 비밀번호 초기값 추가
				String initPasswd = (String) mapUserInfo.get("initPasswd");

				// 임시 비밀번호 수정
				// 변경일을 90일 이후로 수정하여, 로그인 이후 수정팝업 뜨도록 진행
				mapUserInfo.put("initPasswd", initPasswd);
				userService.updateUserPasswordInitInfo(mapUserInfo);

				// 블라썸톡 임시 비밀번호 발송
				String sendMsg = "\n임시 비밀번호는 [ " + initPasswd + " ] 입니다.\n로그인을 진행해주세요.";
				ActionBlossomPush.actionBlossomPush((String) mapUserInfo.get("mgrId"), sendMsg, null);
				
				/** 정상코드 전달 */
				jsonResult.put("chkResultCode", "OK");
				jsonResult.put("errorMsg", "임시 비밀번호 발급이 완료되었습니다.");
			} else {
				NLogger.debug("사용자 정보 미존재");
				/** 에러코드 전달 */
				jsonResult.put("chkResultCode", "ERROR");
				jsonResult.put("errorMsg", "임시 비밀번호 발급 중 오류가 발생했습니다.");
			}

		} catch (Exception e) {
			NLogger.error("임시 비밀번호 발급 오류 : " + e);
			/** 에러코드 전달 */
			jsonResult.put("chkResultCode", "ERROR");
			jsonResult.put("errorMsg", "임시 비밀번호 발급 중 오류가 발생했습니다.");
		} finally {
			NLogger.debug("임시 비밀번호 발급 종료");
		}

		return jsonResult;
	}
	
	/**
	 * 사용자 확인(비밀번호 변경 시, 유효한 사용자인지 확인)
	 * @param userVO
	 * @return
	 */
	@RequestMapping(value = "/checkValidUserInfo.do")
	public @ResponseBody Map<String, Object> checkValidUserInfo(UserVO userVO) {

		NLogger.debug("사용자 정보 확인");

		Map<String, Object> jsonResult = new HashMap<String, Object>();

		/**
		 * =============================================== 
		 * // 사용자 정보 확인
		 * ================================================
		 **/
		try {
			// 사용자 정보 조회
			HashMap<String, Object> mapUserInfo = userService.selectUserPasswordInitInfo(userVO);

			if (mapUserInfo != null) {
				/** 정상코드 전달 */
				jsonResult.put("chkResultCode", "OK");
				jsonResult.put("errorMsg", "정상확인\n비밀번호 변경 창으로 이동합니다.");
			} else {
				NLogger.debug("사용자 정보 미존재");
				/** 에러코드 전달 */
				jsonResult.put("chkResultCode", "ERROR");
				jsonResult.put("errorMsg", "사용자 정보가 없습니다. 관리자에게 문의 바랍니다.");
			}

		} catch (Exception e) {
			NLogger.error("비밀번호 찾기 오류 : " + e);
			/** 에러코드 전달 */
			jsonResult.put("chkResultCode", "ERROR");
			jsonResult.put("errorMsg", "비밀번호 찾기 중 오류가 발생했습니다.");
		} finally {
			NLogger.debug("비밀번호 찾기 종료");
		}

		return jsonResult;
	}
	
	/*******************************
	 * 비밀번호 찾기 화면
	 * @return ModelAndView
	 *******************************/
	@RequestMapping(value="/findPassword.do")
    public ModelAndView findPassword(Locale locale, Model model
    		                         ,@RequestParam(value = "findPwdId") String userId
    		                         ,@RequestParam(value = "findUserName") String userName) {
        ModelAndView mav = new ModelAndView("login/findPassword");
        
        mav.addObject("userId", userId);
        mav.addObject("userName", userName);
        
        return mav;
    }
	
	/**
	 * 비밀번호 변경을 위한 OTP 번호 전송
	 * @param userVO
	 * @return
	 */
	@RequestMapping(value = "/sendPwdOtpNumber.do")
	public @ResponseBody Map<String, Object> sendPwdOtpNumber(UserVO userVO) {

		NLogger.debug("사용자 정보 확인");

		Map<String, Object> jsonResult = new HashMap<String, Object>();

		/**
		 * =============================================== 
		 * // 사용자 정보 확인 & OTP 전송
		 * ================================================
		 **/
		try {
			// 사용자 정보 조회
			HashMap<String, Object> mapUserInfo = userService.selectUserPasswordInitInfo(userVO);

			if (mapUserInfo != null) {
				
				//난수 발생
				String randomNum = String.valueOf(GenerateRandomNum.generateNumber(6));
				
				HashMap<String, Object> paramVO = new HashMap<String, Object>();
				
				paramVO.put("mgrId", userVO.getMgrId()); // 관리자ID
				paramVO.put("certNum", randomNum); // 인증번호
				
				String message = "[SMON] 인증번호는 ["+randomNum+"]입니다. 인증번호란에 3분 이내에 입력해주세요!";
				ActionBlossomPush.actionBlossomPush(userVO.getMgrId(), message, randomNum);
				
				userService.updatePwdCertNum(paramVO);
								
				/** 정상코드 전달 */
				jsonResult.put("chkResultCode", "OK");
				jsonResult.put("errorMsg", "인증번호를 입력해주세요.");
			} else {
				NLogger.debug("사용자 정보 미존재");
				/** 에러코드 전달 */
				jsonResult.put("chkResultCode", "ERROR");
				jsonResult.put("errorMsg", "사용자 정보가 없습니다. 관리자에게 문의 바랍니다.");
			}

		} catch (Exception e) {
			NLogger.error("비밀번호 인증번호 발송 오류 : " + e);
			/** 에러코드 전달 */
			jsonResult.put("chkResultCode", "ERROR");
			jsonResult.put("errorMsg", "인증번호 발송 중 오류가 발생했습니다.");
		} finally {
			NLogger.debug("비밀번호 인증번호 발송 종료");
		}

		return jsonResult;
	}
	
	/**
	 * OTP 확인 및 비밀번호 변경
	 * @param userVO
	 * @return
	 */
	@RequestMapping(value = "/checkOtpAndUpdatePwd.do")
	public @ResponseBody Map<String, Object> checkOtpAndUpdatePwd(UserVO userVO) {

		NLogger.debug("updateEmpPwd 비밀번호 변경 시작");

		Map<String, Object> jsonResult = new HashMap<String, Object>();

		int userPwdHistCnt = 0;

		/**
		 * =============================================== 
		 * // 비밀번호 변경 시작 & 이력 저장
		 * ================================================
		 **/
		try {
			
			/**
			 * =============================================== 
			 * // OTP 번호 확인
			 * ================================================
			 **/
			
			HashMap<String, Object> paramVO = new HashMap<String, Object>();
			
			paramVO.put("mgrId", userVO.getMgrId());
			paramVO.put("certNum", userVO.getCertNum());
			
			HashMap<String, Object> otpMap = userService.checkPwdOtpInfo(paramVO);
			
			if (otpMap != null && otpMap.get("OTP_YN").equals("Y")) { // OTP 정보 일치 할 경우
				/**
				 * =============================================== 
				 * // 비밀번호 작성규칙 체크(김성일 담당님 소스)
				 * ================================================
				 **/
				
				String varMgrId = userVO.getMgrId(); // 아이디
	    		String varMgrPwd = userVO.getMgrPwd(); // 패스워드
	    		String varMgrPwd2 = userVO.getMgrPwd2(); // 패스워드 확인
	    		
	    		paramVO.put("mgrId", varMgrId);
	    		
	    		HashMap<String, Object> paramMap = new HashMap<String, Object>();
				
				/***************************
	    		 * 1. 패스워드 동일여부 파라미터 세팅
	    		 **************************/
	    		HashMap<String, Object> paramMapSub1 = new HashMap<String, Object>();
	    		
	    		paramMapSub1.put("password", varMgrPwd); // 비밀번호
	    		paramMapSub1.put("password2", varMgrPwd2); // 비밀번호 확인
	    		
	    		paramMap.put("PASSWORD_DIFFERENCE", paramMapSub1);
	    		
				/***************************
	    		 * 2. 비밀번호 생성규칙(정규식)
	    		 * - 3가지 조합 8자리 이상
	    		 * - 연속된 숫자, 문자
	    		 **************************/
	    		HashMap<String, Object> paramMapSub2 = new HashMap<String, Object>();
	    		
	    		paramMapSub2.put("password", varMgrPwd);
	    		
	    		paramMap.put("PASSWORD_COMBINATION", paramMapSub2);
	    		
	    		/***************************
	    		 * 3. 비밀번호 생성규칙
	    		 *    - 사용자 연관된 정보(생일, 전화번호, 아이디, 사번, 이메일ID 등 연관된 비밀번호)
	    		 **************************/
	    		List<HashMap<String, Object>> wordMaps = userService.getAssociatedWordMap(paramVO); // 연관된 단어
	    		HashMap<String, Object> paramMapSub3 = new HashMap<String, Object>();
	    		
	    		paramMapSub3.put("password", varMgrPwd);
	    		paramMapSub3.put("wordMaps", wordMaps);
	    		
	    		paramMap.put("PASSWORD_ASSOCIATED", paramMapSub3);
	    		
	    		HashMap<String, Object> returnMap = userService.confirmPwd(paramMap); // 패스워드 확인
	    		
	    		String varResultCode = (String) returnMap.get("resultCode");
	    		String varResultMsg = (String) returnMap.get("resultMsg");
	
				/**
				 * =============================================== 
				 * // 비밀번호 이력 체크
				 * ================================================
				 **/
				userPwdHistCnt = userService.checkUserPasswordHist(userVO);
				
				if (!varResultCode.equals("0000")) { /** 비밀번호 정합성 문제 시 */
					/** 에러코드 전달 */
					jsonResult.put("chkResultCode", "ERROR");
					jsonResult.put("errorMsg", varResultMsg);
				} else if (userPwdHistCnt > 0) { /** 기존 사용된 패스워드의 경우 */
					/** 에러코드 전달 */
					jsonResult.put("chkResultCode", "ERROR");
					jsonResult.put("errorMsg", "최근 3회 사용된 비밀번호입니다.\n다른 비밀번호를 입력해주세요.");
				} else {
					userService.updateUserPassword(userVO); // 비밀번호 변경
					userService.modifyUserPasswordHist(userVO, 0, "I"); // 비밀번호 이력 추가
					userVO.setMgrPwd("");
					userPwdHistCnt = userService.checkUserPasswordHist(userVO); // 이력횟수 조회
					userService.modifyUserPasswordHist(userVO, userPwdHistCnt, "D"); // 비밀번호 이력 삭제
		
					/** 정상코드 전달 */
					jsonResult.put("chkResultCode", "OK");
					jsonResult.put("errorMsg", "비밀번호 변경이 완료되었습니다.");
				}				
				
			}else {
				/** 에러코드 전달 */
				jsonResult.put("chkResultCode", "ERROR");
				jsonResult.put("errorMsg", "비밀번호 변경 중 오류가 발생했습니다.\nOTP 번호 등 확인해주시기 바랍니다.");
			}

		} catch (Exception e) {
			NLogger.error("비밀번호 변경 오류 : " + e);
			/** 에러코드 전달 */
			jsonResult.put("chkResultCode", "ERROR");
			jsonResult.put("errorMsg", "비밀번호 변경 중 오류가 발생했습니다.");
		} finally {
			NLogger.debug("updateEmpPwd 비밀번호 변경 종료");
		}

		return jsonResult;
	}
}
