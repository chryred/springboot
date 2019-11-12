package com.shinsegae.smon.management.draft;

import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shinsegae.smon.model.DrftVO;
import com.shinsegae.smon.model.UserVO;
import com.shinsegae.smon.util.NLogger;

/*******************************
 * 기안서 기준 컨트롤러
 * 
 * @author 175582
 * @since 2018.07.04
 *******************************/
@RequestMapping("/drftStnd")
@Controller
public class DrftController {

	@Autowired
	DrftService drftService;
	protected static Logger logger = Logger.getLogger(DrftController.class.getName());

	/*******************************
	 * 기안서 기준 리스트 화면
	 * 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/drftPaper.do")
	public ModelAndView drftPaper() {
		logger.info("-----drftPaper.do------");

		ModelAndView mav = new ModelAndView();
		mav.addObject("list", drftService.selectBox());
		mav.setViewName("drftStnd/drftPaper");
		return mav;
	}

	/*******************************
	 * 기안서 신규 기준 저장
	 * 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/drftSave.do")
	public ModelAndView drftSave(DrftVO drftVO) {
		logger.info("-----DrftContoller.drftSave.do------");
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		HttpSession session = req.getSession();

		UserVO userVO = (UserVO) session.getAttribute("userVO");
		drftVO.setMODID(userVO.getMgrId());
		drftService.insertData(drftVO);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		return mav;
	}

	/*******************************
	 * 기안서 삭제
	 * 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/drftDelete.do")
	public ModelAndView drftDelete(DrftVO drftVO) {
		logger.info("-----DrftContoller.drftDelete.do------");

		drftService.deleteData(drftVO);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		return mav;
	}

	/*******************************
	 * 기안서 신규 기준 업데이트
	 * 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/drftUpdate.do")
	public ModelAndView drftUpdate(DrftVO drftVO) {
		logger.info("-----DrftContoller.drftUpdate.do------");

		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		HttpSession session = req.getSession();
		UserVO userVO = (UserVO) session.getAttribute("userVO");
		drftVO.setMODID(userVO.getMgrId());
		drftService.updateData(drftVO);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		return mav;
	}

	/*******************************
	 * 기안서 리스트 조회
	 * 
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/drftPaperSearch.do")
	public ModelAndView drftPaperSearch() {
		logger.info("-----drftPaperSearch.do------");
		
	
		
		NLogger.debug(drftService.selectList().get(0).toString());
		NLogger.debug("===========================================================");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("jsonView");
		mav.addObject("data", drftService.selectList());

		return mav;
	}
}
