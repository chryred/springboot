package com.shinsegae.smon.adm;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae.smon.model.AccessLogActionMapVO;
import com.shinsegae.smon.model.PageVO;
import com.shinsegae.smon.model.UrlStatisticsDTO;
import com.shinsegae.smon.model.adm.DsBatchVO;
import com.shinsegae.smon.util.NLogger;

/*******************************
* 배치 컨트롤러
* @author 
* @since 2017.04.29
*******************************/
@RequestMapping("/adm")
@Controller
public class AdmController {
	
	@Autowired
	AdmService admservice;
	
	@Autowired
	LogService logService;
	/**********************************
	 * 운영정보 배치 리스트(표)
	 * @param 
	 * @return ModelAndView
	 *******************************/	
	@RequestMapping("/listUser.do")
	public ModelAndView listUser() {
		DsBatchVO searchCondition = new DsBatchVO();

		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/adm/listUser");
		mav.addObject("searchCondition", searchCondition);
		
		return mav;
	}
	
	
	/*******************************
	 * 사용자리스트listAuthGruopcheck
	 * @param DsBatchVO
	 * @return ModelAndView
	 * @throws Exception
	 *******************************/	
	@RequestMapping("/listUserJson.do")
	public @ResponseBody ModelAndView listUserJson(DsBatchVO paramCondition) {	
		List<DsBatchVO> rstList = null;
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("jsonView"); 
	
		try {
			/*int count = admservice.getCountlistUser(paramCondition); 
			
			PageVO pageVO = new PageVO(count, paramCondition.getCurPage(), paramCondition.getPageScale());
			
			int start = pageVO.getPageBegin(); // 시작번호
			int end = pageVO.getPageEnd(); // 끝번호
			
			paramCondition.setStart(start);
			paramCondition.setEnd(end);
			 
			rstList = admservice.getSimplelistUser(paramCondition);
			 
			mav.addObject("chkResult", "OK");
			mav.addObject("pageVO", pageVO);
			mav.addObject("count", count);*/
			
			int count = admservice.getCountlistUser(paramCondition); 
			   
		   PageVO pageVO = new PageVO(count, paramCondition.getCurPage(), paramCondition.getPageScale());

		   String mgrGrade = paramCondition.getMgrGrade();
		   int start = pageVO.getPageBegin(); // 시작번호
		   int end = pageVO.getPageEnd(); // 끝번호
		  
		   
		   
		   paramCondition.setStart(start);
		   paramCondition.setEnd(end);
		   paramCondition.setMgrGrade(mgrGrade);
		    
		   rstList = admservice.getSimplelistUser(paramCondition);

		   mav.addObject("chkResult", "OK");
		   mav.addObject("pageVO", pageVO);
		   mav.addObject("count", count);
		   mav.addObject("mgrGrade",mgrGrade);
		   
		   NLogger.info(mgrGrade);
		   
		} catch (Exception e) {
			mav.addObject("chkResult", "FAIL");
			mav.addObject("errorMSg", e.toString());
		}
		
		mav.addObject("paramCondition", paramCondition);
		mav.addObject("rstList", rstList);
		
		return mav;
	}
	
	/*******************************
	 *   
	 * @param 
	 * @return ModelAndView
	 *******************************/	
	@RequestMapping("/listMenu.do")
	public ModelAndView listMenu() {	
		DsBatchVO searchCondition = new DsBatchVO();
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/adm/listMenu");
		mav.addObject("searchCondition", searchCondition);
		
		return mav;
	}	
	
	/*******************************
	 * v
	 * @param DsBatchVO
	 * @return ModelAndView
	 * @throws Exception
	 *******************************/	
	@RequestMapping("/listMenuJson.do")
	public @ResponseBody ModelAndView listMenuJson(DsBatchVO paramCondition) {	
		List<DsBatchVO> rstList = null;
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("jsonView"); 
		 
		try {
			rstList = admservice.getDsMenuList(paramCondition);
			mav.addObject("chkResult", "OK");
		} catch (Exception e) {
			mav.addObject("chkResult", "FAIL");
			mav.addObject("errorMSg", e.toString());
		}
		 
		mav.addObject("paramCondition", paramCondition);
		mav.addObject("rstList", rstList);
		
		return mav;
	}
	
	@RequestMapping("/MenuDetailJson.do")
	public @ResponseBody ModelAndView MenuDetailJson(DsBatchVO paramCondition) {	
		HashMap<String, Object> returnMap = new  HashMap<String, Object>();
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("jsonView"); 
		
		try {
		//	mav.addObject("ret", admservice.getMenuDetail(paramCondition));
		    returnMap = admservice.getMenuDetail(paramCondition);
			mav.addObject("chkResult", "OK");
		} catch (Exception e) {
			mav.addObject("chkResult", "FAIL");
			mav.addObject("errorMSg", e.toString());
			e.printStackTrace();
		}		 	
		//mav.addObject("ID",returnMap.get("ID"));
		//logger.info(returnMap.get("pId"));
		mav.addObject("id", returnMap.get("id"));
		mav.addObject("pId1", returnMap.get("pId"));
		mav.addObject("name", returnMap.get("name"));
		mav.addObject("open", returnMap.get("open"));
		mav.addObject("lev", returnMap.get("lev"));	
		mav.addObject("menulink", returnMap.get("menulink"));	 
		mav.addObject("menuorder", returnMap.get("menuorder"));	 
		mav.addObject("useyn", returnMap.get("useyn"));	 
		mav.addObject("menuImg", returnMap.get("menuImg"));
		mav.addObject("paramCondition", paramCondition);
	 
		
		return mav;
	}	
	
	/*******************************
	 * 메뉴 츠기수정(amd Munu)
	 * @param 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/updateMenuJson.do")
	public @ResponseBody ModelAndView updateMenuJson(DsBatchVO paramCondition) {		
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("jsonView"); 		
	
		try {	 
			// Cron Job 수정
			admservice.updateMenuList(paramCondition);
			mav.addObject("chkResult", "OK");		 
		} catch (Exception e) {
			mav.addObject("chkResult", "FAIL");
			mav.addObject("errorMsg", e.toString());
			e.printStackTrace();
		}		
		
		return mav;
	}	
	
	/*******************************
	 * 권한그룹관리화면이동
	 * @param 
	 * @return ModelAndView
	 *******************************/	
	@RequestMapping("/listAuthGruop.do")
	public ModelAndView listAuthGruop() {	
		DsBatchVO searchCondition = new DsBatchVO();
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/adm/listAuthGroup");
		mav.addObject("searchCondition", searchCondition);
		
		return mav;
	}	
	/*******************************
	 * 권한그룹리스트
	 * @param 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/listAuthGruopJson.do")
	public @ResponseBody ModelAndView listAuthGruopJson(DsBatchVO paramCondition) {	
		List<DsBatchVO> rstList = null;
		
		ModelAndView mav = new ModelAndView();
		System.out.println("-------------------------------------------!!!!!!!!!!!!!!!!-----------------");
		mav.setViewName("jsonView"); 

		try {		 
			int count = admservice.getCountlistgroup(paramCondition); 
			NLogger.getString("----------------------------"+count+"--------------------------");
			PageVO pageVO = new PageVO(count, paramCondition.getCurPage(), paramCondition.getPageScale());
			
			int start = pageVO.getPageBegin(); // 시작번호
			int end = pageVO.getPageEnd(); // 끝번호
			
			paramCondition.setStart(start);
			paramCondition.setEnd(end);
			
			rstList = admservice.getlistAuthGruop(paramCondition);			 
			mav.addObject("chkResult", "OK");	
			mav.addObject("pageVO", pageVO);
			mav.addObject("count", count);
			
		} catch (Exception e) {		 
			
			mav.addObject("chkResult", "FAIL");
			mav.addObject("errorMSg", e.toString());
			e.printStackTrace();
		}
		 
		mav.addObject("paramCondition", paramCondition);
		mav.addObject("rstList", rstList); 
		
		return mav;
	}
	
	/*******************************
	 * 권한그룹 상세내역
	 * @param 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/selectauthGroup.do")
	public @ResponseBody ModelAndView selectauthGroup(DsBatchVO paramCondition) {	
		HashMap<String, Object> returnMap = new  HashMap<String, Object>();
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("jsonView"); 
		
		try {
		
		    returnMap = admservice.selectauthGroup(paramCondition);
			mav.addObject("chkResult", "OK");
		
		} catch (Exception e) {
			mav.addObject("chkResult", "FAIL");
			mav.addObject("errorMSg", e.toString());
			e.printStackTrace();
			
		}
		mav.addObject("authGroupid", returnMap.get("authGroupid"));
		mav.addObject("authGroupnm", returnMap.get("authGroupnm"));
		mav.addObject("authGroupremark", returnMap.get("authGroupremark"));
		mav.addObject("useyn", returnMap.get("useyn"));
		mav.addObject("roleGroup", returnMap.get("roleGroup"));	 
		mav.addObject("paramCondition", paramCondition);	 
		
		
		return mav;
	}	
	
	/*******************************
	 * 권한그룹 중복체크
	 * @param DsBatchVO
	 * @return ModelAndView
	 * @throws Exception
	 *******************************/	
	@RequestMapping("/listAuthGruopcheck.do")
	public @ResponseBody ModelAndView listAuthGruopcheck(DsBatchVO paramCondition) {			
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("jsonView"); 
	
		try {
			int count = admservice.getCountlistGruop(paramCondition); 	 
			 //
			mav.addObject("chkResult", "OK");		
			mav.addObject("count", count);
		} catch (Exception e) {
			mav.addObject("chkResult", "FAIL");
			mav.addObject("errorMSg", e.toString());
		}		
		mav.addObject("paramCondition", paramCondition);		 
		
		return mav;
	}
		
		/*******************************
		 * 메뉴번호 중복체크
		 * @param DsBatchVO
		 * @return ModelAndView
		 * @throws Exception
		 *******************************/	
		@RequestMapping("/listMenuIdcheck.do")
		public @ResponseBody ModelAndView listMenuIdcheck(DsBatchVO paramCondition) {			
			ModelAndView mav = new ModelAndView();
			
			mav.setViewName("jsonView"); 
		
			try {
				int count = admservice.listMenuIdcheck(paramCondition); 	 
				 //
				mav.addObject("chkResult", "OK");		
				mav.addObject("count", count);
			} catch (Exception e) {
				mav.addObject("chkResult", "FAIL");
				mav.addObject("errorMSg", e.toString());
			}		
			mav.addObject("paramCondition", paramCondition);		 
			
			return mav;
		}
	
	/*******************************
	 * 권한그룹 수정 및 입력
	 * @param 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/listAuthGruopSave.do")
	public @ResponseBody ModelAndView listAuthGruopSave(DsBatchVO paramCondition) {		
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("jsonView"); 		
		try {	 
			// Cron Job 수정
			admservice.listAuthGruopSave(paramCondition);
			mav.addObject("chkResult", "OK");		 
		} catch (Exception e) {
			mav.addObject("chkResult", "FAIL");
			mav.addObject("errorMsg", e.toString());
			e.printStackTrace();
		}		
		
		return mav;
	}	
	
	/*******************************
	 * 권한그룹 삭제
	 * @param 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/listAuthGruopDelete.do")
	public @ResponseBody ModelAndView listAuthGruopDelete(DsBatchVO paramCondition) {		
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("jsonView"); 		
		try {	 
			// Cron Job 수정
			admservice.listAuthGruopDelete(paramCondition);
			mav.addObject("chkResult", "OK");		 
		} catch (Exception e) {
			mav.addObject("chkResult", "FAIL");
			mav.addObject("errorMsg", e.toString());
			e.printStackTrace();
		}		
		
		return mav;
	}	
	
	/*******************************
	 * 권한그룹 삭제
	 * @param 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/deleteMenuJson.do")
	public @ResponseBody ModelAndView deleteMenuJson(DsBatchVO paramCondition) {		
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("jsonView"); 		
		try {	 
			// Cron Job 수정
			admservice.deleteMenuJson(paramCondition);
			mav.addObject("chkResult", "OK");		 
		} catch (Exception e) {
			mav.addObject("chkResult", "FAIL");
			mav.addObject("errorMsg", e.toString());
			e.printStackTrace();
		}		
		
		return mav;
	}	
	

	/*******************************
	 * 메뉴권한관리화면이동
	 * @param 
	 * @return ModelAndView
	 *******************************/	
	@RequestMapping("/listMenuGroup.do")
	public ModelAndView listMenuGroup() {	
		DsBatchVO searchCondition = new DsBatchVO();
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/adm/listMenuGroup");
		mav.addObject("searchCondition", searchCondition);
		
		return mav;
	}	 

	/*******************************
	 * 권한 셀렉트 박스 
	 * @param 
	 * @return ModelAndView
	 *******************************/	
	@RequestMapping("/selectGroupId.do")
	public @ResponseBody ModelAndView selectGroupId(DsBatchVO paramCondition) {	
		List<DsBatchVO> rstList = null;
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("jsonView"); 

		try {		 
			
			rstList = admservice.selectGroupId(paramCondition);			 
			mav.addObject("chkResult", "OK");	
			
		} catch (Exception e) {		 
			
			mav.addObject("chkResult", "FAIL");
			mav.addObject("errorMSg", e.toString());
			e.printStackTrace();
		}
		 
		mav.addObject("paramCondition", paramCondition);
		mav.addObject("rstList", rstList); 
		
		return mav;
	}
	
	@RequestMapping("/listMenuGruopJson.do")
	public @ResponseBody ModelAndView listMenuGruopJson(DsBatchVO paramCondition) {	
		List<DsBatchVO> rstList = null;
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("jsonView");  
		try {		 
			
			rstList = admservice.listMenuGruopJson(paramCondition);			 
			mav.addObject("chkResult", "OK");	
			
		} catch (Exception e) {		 
			
			mav.addObject("chkResult", "FAIL");
			mav.addObject("errorMSg", e.toString());
			e.printStackTrace();
		}
		 
		mav.addObject("paramCondition", paramCondition);
		mav.addObject("rstList", rstList); 
		
		return mav;
	}
	
	/*******************************
	 * 권한그룹 권한주기
	 * @param 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/updateMenugroup.do" )
	public @ResponseBody ModelAndView updateMenugroup(
			
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="data", required=true) DsBatchVO data
			) {		
		
		ModelAndView mav = new ModelAndView();
		
//		List<DsBatchVO> dsBatchVOs = new ArrayList<>();
//		DsBatchVO dsBatchVO = new DsBatchVO();
		
		//logger.info(data.toString());
		
		Enumeration<String> params = request.getParameterNames();
		//logger.info("----------------------------");
//		while (params.hasMoreElements()){
//		    String name = (String)params.nextElement();
//		    //logger.info(name + " : " +request.getParameter(name));
//		}
		//logger.info("----------------------------");


		
		mav.setViewName("jsonView"); 		
		try {	 
			//logger.info("===================");
			//logger.info(request.getParameter("postDatas[0].id"));
			//logger.info(paramCondition.toString());
			/*for (int i = 0; i < id.length; i++) {
				logger.info(id[i]);
			}
			for (int i = 0; i < authCd.length; i++) {
				logger.info(authCd[i]);
			}*/
//			logger.info(paramCondition.length);
//			for (int i = 0; i < paramCondition.length; i++) {
//				logger.info(paramCondition[i].getId().toString());
//				logger.info(paramCondition[i].getAuthCd().toString());
//			}
			
			//logger.info(request.toString());
			//logger.info("==================="); 
			//admservice.updateMenugroup(paramCondition);
			mav.addObject("chkResult", "OK");		 
		} catch (Exception e) {
			mav.addObject("chkResult", "FAIL");
			mav.addObject("errorMsg", e.toString());
			e.printStackTrace();
		}		
		
		return mav;
	}	
	
	/*******************************
	 * 상휘메뉴 출력
	 * @param 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/navigation.do")
	public @ResponseBody ModelAndView selectNavigation(DsBatchVO paramCondition) {	
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/mtr/common/navigationMenu"); 
		
		try {
		
		    mav.addObject("menuList", admservice.selectNavigation(paramCondition));
		
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return mav;
	}
	
	/*******************************
	 * 권한그룹 수정 및 입력
	 * @param 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/listMenuGruopSave.do")
	public @ResponseBody ModelAndView listMenuGruopSave(DsBatchVO paramCondition) {		
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("jsonView"); 		
		try {	 
			// Cron Job 수정
			admservice.listMenuGruopSave(paramCondition);
			mav.addObject("chkResult", "OK");		 
		} catch (Exception e) {
			mav.addObject("chkResult", "FAIL");
			mav.addObject("errorMsg", e.toString());
			e.printStackTrace();
		}		
		
		return mav;
	}
	
	/*******************************
	  * @param DsBatchVO
	  * @return ModelAndView
	  * @throws Exception
	  *******************************/ 
	 @RequestMapping("/listUserUpdateJson.do")
	 public @ResponseBody ModelAndView listUserUpdateJson(DsBatchVO paramCondition) { 
	  NLogger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@listUserUpdateJson_START!!");
	  
	  ModelAndView mav = new ModelAndView();
	  
	  mav.setViewName("jsonView"); 
	 
	  try {
	   
	   
	   String aa = paramCondition.getSUserList();
	   String[] bb  = aa.split(",");
	   
	   
	   for (int i = 0; i < bb.length; i++) {
	    NLogger.info("결과 확인 :: "+bb[i]);
	    DsBatchVO param  = new DsBatchVO();
	    
	    String[] cc = bb[i].split("@");
	    NLogger.info("11 :: "+cc[0]);
	    NLogger.info("22 :: "+cc[1]);
	    NLogger.info("33 :: "+cc[2]);
	    
	    param.setMgrId(cc[0]);
	    param.setMgrGrade(cc[1]);
	    param.setMgrStatecd(cc[2]);
	    admservice.getlistUserUpdate(param);
	    
	   }
	   
	   
	   mav.addObject("chkResult", "OK");
	  } catch (Exception e) {
	   mav.addObject("chkResult", "FAIL");
	   mav.addObject("errorMSg", e.toString());
	  }
	  
	  NLogger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@listUserUpdateJson_END!!");
	  return mav;
	 }
	 
	 
	 
	 @RequestMapping("/logActionMap.do")
	 public ModelAndView logActionMap() throws Exception {
		/**
		 *  user 권한 확인 필요
		 */
		ModelAndView mav = new ModelAndView();
		JSONArray jArray = new JSONArray();
		List list = logService.getLogURLDistinct();
		for (int i=0; i<list.size(); i++) {
			jArray.put(list.get(i));
		}
		
		mav.addObject("URLs", jArray);  
		mav.setViewName("/adm/logActionMap");
  
		return mav;
	 } 
	 
	 @RequestMapping("/getURLInfo.do")
	 public @ResponseBody ModelAndView getURLInfo(AccessLogActionMapVO accessLogActionMapVO) {
	  /**
	   *  user 권한 확인 필요
	   */
	  ModelAndView mav = new ModelAndView();
	  mav.setViewName("jsonView");
	  AccessLogActionMapVO accessLogActionMapVO2 = null;
	  
	  try {
	   accessLogActionMapVO2 = logService.getLogActionMap(accessLogActionMapVO);
	   if(accessLogActionMapVO2 == null) {
	    accessLogActionMapVO2 = new AccessLogActionMapVO();
	   }
	   mav.addObject("chkResult", "OK");
	  } catch (Exception e) {
	   mav.addObject("chkResult", "FAIL");
	   mav.addObject("errorMSg", e.toString());
	  }
	  
	  mav.addObject("data", accessLogActionMapVO2);
	  
	  return mav;
	 }
	 
	 @RequestMapping("/setURLInfo.do")
	 public @ResponseBody ModelAndView setURLInfo(AccessLogActionMapVO accessLogActionMapVO) {
	  /**
	   *  user 권한 확인 필요
	   */
	  
	  ModelAndView mav = new ModelAndView();
	  
	  mav.setViewName("jsonView");
	  try {
	   logService.setLogActionMap(accessLogActionMapVO);
	   mav.addObject("chkResult", "OK");
	  } catch (Exception e) {
	   mav.addObject("chkResult", "FAIL");
	   mav.addObject("errorMSg", e.toString());
	  }
	  
	  return mav;
	 }
	 
	 @RequestMapping("/getURLStatistics.do")
	 public @ResponseBody ModelAndView getURLStatistics(UrlStatisticsDTO paramCondition) {
	  /**
	   *  user 권한 확인 필요
	   */

	  ModelAndView mav = new ModelAndView();
	  mav.setViewName("jsonView");
	  
	  List<UrlStatisticsDTO> ret = new ArrayList<>();
	  try {
	   int count = logService.getCountUrlStatisticsList(paramCondition); 
	   
	   PageVO pageVO = new PageVO(count, paramCondition.getCurPage(), paramCondition.getPageScale());
	   
	   int start = pageVO.getPageBegin(); // 시작번호
	   int end = pageVO.getPageEnd(); // 끝번호
	   
	   
	   paramCondition.setStart(start);
	   paramCondition.setEnd(end);
	   

	   ret = logService.getUrlStatistics(paramCondition);
	   
	   mav.addObject("pageVO", pageVO);
	   mav.addObject("count", count);
	   mav.addObject("paramCondition", paramCondition);
	   mav.addObject("data", ret);
	   
	   mav.addObject("chkResult", "OK");
	  } catch (Exception e) {
	   e.printStackTrace();
	   
	   mav.addObject("chkResult", "FAIL");
	   mav.addObject("errorMSg", e.toString());
	  }
	  
	  return mav;
	 }
	
} 