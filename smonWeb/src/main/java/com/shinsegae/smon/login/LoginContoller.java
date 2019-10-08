package com.shinsegae.smon.login;

import java.util.HashMap;
import java.util.Locale;

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
        loginService.searchUser();
        
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
}
