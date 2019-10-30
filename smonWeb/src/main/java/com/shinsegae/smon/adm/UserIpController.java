package com.shinsegae.smon.adm;

import java.net.InetAddress;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shinsegae.smon.adm.UserIpService;
import com.shinsegae.smon.model.UserVO;
import com.shinsegae.smon.util.ComUtil;
import com.shinsegae.smon.util.DataMap;
import com.shinsegae.smon.util.RequestMap;
import com.shinsegae.smon.util.NLogger;

@Controller
public class UserIpController {
	
	@Autowired
	UserIpService userIpService;
	
    // IP 리스트
	@RequestMapping("/listUserIp.do")
	public String systemCheck(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "/adm/listUserIp";
	}
	
	// 유저 IP 리스트 ajax
	@SuppressWarnings({ "unchecked", "rawtypes" })	
	@RequestMapping("/ajax/selectUserIp.do")
	public String selectSystemList(HttpServletRequest request, HttpServletResponse response, Model model) {		
		RequestMap param = RequestMap.getRequestMap(request, response);

		List<DataMap> retMap = userIpService.selectUserIp(param);
		
		/** 특수문자 html 코드로 변환 */
		if(retMap !=null && retMap.size() > 0){
			for(int i=0;i<retMap.size();i++){
				DataMap dmap = retMap.get(i);
				dmap.put("recentUser", ComUtil.validFormValueNC(dmap.getString("recentUser")));
				dmap.put("forWhat", ComUtil.validFormValueNC(dmap.getString("forWhat")));				
			}
		}
		
		model.addAttribute("data", retMap);
		model.addAttribute("resultCode", "SUCCESS");
		
		return "jsonView";
	}
	
	// 등록 가능한 IP 리스트
	@SuppressWarnings("rawtypes")	
	@RequestMapping("/ajax/selectAvailableIp.do")
	public String selectAvailableIp(HttpServletRequest request, HttpServletResponse response, Model model) {		
		RequestMap param = RequestMap.getRequestMap(request, response);

		List<DataMap> retMap = userIpService.selectAvailableIp(param);
		
		model.addAttribute("data", retMap);
		model.addAttribute("resultCode", "SUCCESS");
		
		return "jsonView";
	}
	
	// 신규 IP 발급
	@RequestMapping("/insertNewIp.do")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String insertNewIp(HttpServletRequest request, HttpServletResponse response, Model model) {	
		RequestMap param = RequestMap.getRequestMap(request, response);
		boolean processYn = false;
		try{
			
			String insertIp = param.getString("userIp");
			
			int timeOut = 3000; // 최소 3초 이상이 좋다고 한다. 물론, 상황에 따라..
			boolean status = InetAddress.getByName(insertIp).isReachable(timeOut);
			System.out.println(status);
			
			if(!status) {
				userIpService.insertNewIp(param);
	    		
	            /** 정상코드 전달 */
				model.addAttribute("resultCode", "SUCCESS");
				model.addAttribute("resultMsg", "신규 IP가 정상적으로 발급되었습니다.");
				processYn = true;
			}else {
				/** 에러코드 전달(사용중인 IP) */
				model.addAttribute("resultCode", "ERROR");
				model.addAttribute("resultMsg", "사용 추정되는 IP입니다.\n다른 IP로 등록요청해주세요.(미등록 IP)");
				processYn = false;
			}
			
    	}catch(Exception e) {
    		//e.printStackTrace(); // 임시 - 제거예정
    		
    		/** 에러코드 전달 */
    		model.addAttribute("resultCode", "ERROR");
    		model.addAttribute("resultMsg", "신규 IP 발급 중 오류가 발생했습니다.");
    	}
		
		if(processYn){
			try {
				/** 로그정보 저장 */
				HttpSession session = request.getSession(true);
				
				UserVO userVO = (UserVO) session.getAttribute("userVO"); 
				//String userId = (String)session.getAttribute("id"); // 최병문 추후 변경
				
				param.put("userId", userVO.getMgrId());
				param.put("acionCode", "INS");
				userIpService.insertIpHistory(param);
			} catch(Exception e) {
				NLogger.error("로그 저장 중 오류 발생");
			}
		}
		
		return "jsonView";
	}
	
	// IP 수정
	@RequestMapping("/updateIp.do")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String updateIp(HttpServletRequest request, HttpServletResponse response, Model model) {
		RequestMap param = RequestMap.getRequestMap(request, response);
		boolean processYn = false;
		
		try{
			
			String checkPasswdYn = "N";  // 비밀번호 체크

			checkPasswdYn = userIpService.confirmPasswdYn(param);
			
			if(checkPasswdYn.equals("Y")) {
				userIpService.updateIp(param);
	    		
	            /** 정상코드 전달 */
				model.addAttribute("resultCode", "SUCCESS");
				model.addAttribute("resultMsg", "정상적으로 수정되었습니다.");
				processYn = true;
			}else{
				 /** 에러코드 전달 */
				model.addAttribute("resultCode", "ERROR");
				model.addAttribute("resultMsg", "비밀번호가 일치하지 않습니다.");
			}
			
    	}catch(Exception e) {
    		//e.printStackTrace(); // 임시 - 제거예정
    		
    		/** 에러코드 전달 */
    		model.addAttribute("resultCode", "ERROR");
    		model.addAttribute("resultMsg", "수정 중 오류가 발생했습니다.");
    	}
		
		if(processYn){
			try {
				/** 로그정보 저장 */
				HttpSession session = request.getSession(true);
				UserVO userVO = (UserVO) session.getAttribute("userVO"); 
				//String userId = (String)session.getAttribute("id"); // 최병문 추후 변경
				
				param.put("userId", userVO.getMgrId());
				param.put("acionCode", "MOD");
				userIpService.insertIpHistory(param);
			} catch(Exception e) {
				NLogger.error("로그 저장 중 오류 발생");
			}
		}
		
		return "jsonView";
	}
	
	// IP 삭제
	@RequestMapping("/deleteIp.do")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String deleteIp(HttpServletRequest request, HttpServletResponse response, Model model) {
		RequestMap param = RequestMap.getRequestMap(request, response);
		boolean processYn = false;
		try{
			
			String checkPasswdYn = "N";  // 비밀번호 체크
			
			checkPasswdYn = userIpService.confirmPasswdYn(param);
			
			if(checkPasswdYn.equals("Y")) {
				userIpService.deleteIp(param);
	    		
	            /** 정상코드 전달 */
				model.addAttribute("resultCode", "SUCCESS");
				model.addAttribute("resultMsg", "IP가 정상적으로 삭제되었습니다.");
				processYn = true;
			}else{
				 /** 에러코드 전달 */
				model.addAttribute("resultCode", "ERROR");
				model.addAttribute("resultMsg", "비밀번호가 일치하지 않습니다.");
			}
			
    	}catch(Exception e) {
    		//e.printStackTrace(); // 임시 - 제거예정
    		
    		/** 에러코드 전달 */
    		model.addAttribute("resultCode", "ERROR");
    		model.addAttribute("resultMsg", "IP 삭제 중 오류가 발생했습니다.");
    	}
		
		if(processYn){
			try {
				/** 로그정보 저장 */
				HttpSession session = request.getSession(true);
				UserVO userVO = (UserVO) session.getAttribute("userVO"); 
				//String userId = (String)session.getAttribute("id"); // 최병문 추후 변경
				
				param.put("userId", userVO.getMgrId());
				param.put("acionCode", "DEL");
				userIpService.insertIpHistory(param);
			} catch(Exception e) {
				NLogger.error("로그 저장 중 오류 발생");
			}
		}
		
		return "jsonView";
	}
	
	// IP 리스트 ajax
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/ajax/confirmPassword.do")
	public String confirmPassword(HttpServletRequest request, HttpServletResponse response, Model model) {		
		RequestMap param = RequestMap.getRequestMap(request, response);

		List<DataMap> retMap = userIpService.selectUserIp(param);
		
		// 발급 이력 저장
		
		/** 특수문자 html 코드로 변환 */
		if(retMap !=null && retMap.size() > 0){
			for(int i=0;i<retMap.size();i++){
				DataMap dmap = retMap.get(i);
				dmap.put("recentUser", ComUtil.validFormValueNC(dmap.getString("recentUser")));
				dmap.put("forWhat", ComUtil.validFormValueNC(dmap.getString("forWhat")));				
			}
		}
		
		model.addAttribute("data", retMap);
		model.addAttribute("resultCode", "SUCCESS");
		
		return "jsonView";
	}
}