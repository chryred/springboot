package com.shinsegae.smon.management.conference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae.smon.model.ConfVO;
import com.shinsegae.smon.management.conference.ConfService;
import com.shinsegae.smon.model.UserVO;

/*******************************
 * 기안서 기준 컨트롤러
 * 
 * @author 175582
 * @since 2018.07.04
 *******************************/
@RequestMapping("/confAdm")
@Controller
public class ConfController {
	
	@Autowired
	ConfService confService;
	
	
	protected static Logger logger = Logger.getLogger(ConfController.class.getName());

	
	/*******************************
	 * 참석자 통계 관리 화면
	 * 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/attndStatics.do")
	public ModelAndView attndStatics() {
		logger.info("-----attndStatics.do------");

		ModelAndView mav = new ModelAndView();
		//mav.addObject("list", drftService.selectBox());
		mav.setViewName("drftStnd/attndStatics");
		return mav;
	}
	
	/*******************************
	 * 진행자 통계 관리 화면
	 * 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/hostStatics.do")
	public ModelAndView hostStatics() {
		logger.info("-----hostStatics.do------");

		ModelAndView mav = new ModelAndView();
		mav.setViewName("drftStnd/hostStatics");
		return mav;
	}
	
	@RequestMapping("/confList.do")
	public ModelAndView conferenceList() {
		logger.info("-----confList.do------");

		ModelAndView mav = new ModelAndView();
		mav.setViewName("drftStnd/confList");
		return mav;
	}
	
	@RequestMapping("/attndList.do")
	public ModelAndView attndList(ConfVO confVO) {
		logger.info("-----attndList.do------");
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = req.getSession();
		UserVO userVO = (UserVO) session.getAttribute("userVO");
		ModelAndView mav = new ModelAndView();
		confVO.setLOGINID(userVO.getMgrId());
		// 공유회 진행자 및 품질담당자에 한해서만 화면을 열도록 분기
		logger.info("confHostYN--" + confService.confHostYN(confVO));
		if(confService.confHostYN(confVO).equals("0")) {
			mav.setViewName("drftStnd/confList");
		} else {
			mav.addObject("SEQ", confVO.getSEQ());
			logger.info("re controller attndList");
			mav.setViewName("drftStnd/attndList");
		}
		return mav;
	}
	
	@RequestMapping("/searchAttndList.do")
	public ModelAndView searchAttndList(ConfVO confVO) {
		logger.info("-----searchAttndList.do------");

		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		mav.addObject("data", confService.selectAttndList(confVO));
		return mav;
	}
	
	
	/*******************************
	 * 회의 리스트 관리 화면
	 * 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/confSearch.do")
	public ModelAndView confSearch() {
		logger.info("-----confSearch.do------");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		mav.addObject("data", confService.selectList());
		return mav;
	}
	
	/*******************************
	 * 엑셀 다운로드
	 * 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/excelDown.do")
	public ModelAndView excelDown(ConfVO confVO) {
		logger.info("-----excelDown.do------");
		System.out.println(confVO);
		ModelAndView mav = new ModelAndView();
		//mav.setViewName("jsonView");
		System.out.println(confService.searchAttndList(confVO));
		mav.addObject("list", confService.searchAttndList(confVO));
		
		mav.setViewName("drftStnd/excel");
		return mav;
	}
	
	/*******************************
	 * 진행자 사번 이름 찾기
	 * 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/searchHost.do")
	public ModelAndView searchHost(ConfVO confVO) {
		logger.info("-----searchHost.do------");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		mav.addObject("data", confService.searchHost(confVO));
		return mav;
	}
	
	/*******************************
	 * 회의 정보 가져오기
	 * 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/selConfName.do")
	public ModelAndView selConfName(ConfVO confVO) {
		logger.info("-----selConfName.do------");
		logger.info(confVO.getSEQ());
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		mav.addObject("data", confService.selConfName(confVO));
		return mav;
	}
	
	/*******************************
	 * 회의 리스트 관리 화면
	 * 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/confSave.do")
	public ModelAndView confSave(ConfVO confVO) {
		logger.info("-----confSave.do------");
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = req.getSession();
		UserVO userVO = (UserVO) session.getAttribute("userVO");
		
		confVO.setLOGINID(userVO.getMgrId());
		
		confService.insertData(confVO);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		//mav.addObject("list", drftService.selectBox());
		return mav;
	}

	@RequestMapping("/attndChng.do")
	public ModelAndView attndChng(ConfVO confVO) {
		logger.info("-----attndChng.do------");

		
		confService.attndChng(confVO);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		mav.addObject("SEQ", confVO.getSEQ());
		return mav;
	}
	
	@RequestMapping("/deleteConf.do")
	public ModelAndView deleteConf(ConfVO confVO) {
		logger.info("-----deleteConf.do------");

		
		confService.deleteConf(confVO);
		confService.deleteAttndList(confVO);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		return mav;
	}
	
	@RequestMapping("/searchAttndResult.do")
	public ModelAndView searchAttndResult(ConfVO confVO) {
		logger.info("-----searchAttndResult.do------");

		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		mav.addObject("data", confService.searchAttndResult(confVO));
		return mav;
	}
	
	@RequestMapping("/searchHostResult.do")
	public ModelAndView searchHostResult(ConfVO confVO) {
		logger.info("-----searchHostResult.do------");

		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		mav.addObject("data", confService.searchHostResult(confVO));
		return mav;
	}
}
