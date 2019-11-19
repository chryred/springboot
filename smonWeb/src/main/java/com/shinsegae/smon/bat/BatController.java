package com.shinsegae.smon.bat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae.smon.model.bat.DsBatchVO;
import com.shinsegae.smon.model.bat.PageVO;


/*******************************
 * 배치 컨트롤러
 * @author 153712 김성일
 * @since 2017.04.29
 *******************************/
@RequestMapping("/batch")
@Controller
public class BatController {
	@Autowired
	BatService batchService;
	
	/*******************************
	 * 운영정보 배치 리스트
	 * @param 
	 * @return ModelAndView
	 *******************************/	
	@RequestMapping("/listSossBatch.do")
	public ModelAndView listSossBatch() {	
		DsBatchVO searchCondition = new DsBatchVO();
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/bat/listSossBatch");
		mav.addObject("searchCondition", searchCondition);
		
		return mav;
	}	
	
	/*******************************
	 * 운영정보 배치 리스트 조회
	 * @param DsBatchVO
	 * @return ModelAndView
	 * @throws Exception
	 *******************************/	
	@RequestMapping("/listSossBatchJson.do")
	public @ResponseBody ModelAndView listSossBatchJson(DsBatchVO paramCondition) {	
		List<DsBatchVO> rstList = null;
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("jsonView"); 
		
		try {
			rstList = batchService.getSossBatchList(paramCondition);
			mav.addObject("chkResult", "OK");
		} catch (Exception e) {
			mav.addObject("chkResult", "FAIL");
			mav.addObject("errorMSg", e.toString());
		}
		
		mav.addObject("paramCondition", paramCondition);
		mav.addObject("rstList", rstList);
		
		return mav;
	}	
	
	/*******************************
	 * DataStage-폴더구조 조회
	 * @param DsBatchVO
	 * @return ModelAndView
	 * @throws Exception
	 *******************************/	
	@RequestMapping("/listFolderJson.do")
	public @ResponseBody ModelAndView listFolderJson(DsBatchVO paramCondition) {	
		List<DsBatchVO> rstList = null;
		
		String projectName = paramCondition.getProjectName(); // 프로젝트명
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("jsonView"); 
		
		try {
			if (projectName.equals("SHUB_BAT")) {
				rstList = batchService.getShubBatchFolderList(paramCondition);
			} else {
				rstList = batchService.getDsFolderList(paramCondition);
			}
			
			mav.addObject("chkResult", "OK");
		} catch (Exception e) {
			mav.addObject("chkResult", "FAIL");
			mav.addObject("errorMSg", e.toString());
		}
		
		mav.addObject("paramCondition", paramCondition);
		mav.addObject("rstList", rstList);
		
		return mav;
	}	
	
	/*******************************
	 * DataStage-배치 실행 조회
	 * @param DsBatchVO
	 * @return ModelAndView
	 * @throws Exception
	 *******************************/	
	@RequestMapping("/listDsBatchJson.do")
	public @ResponseBody ModelAndView listDsBatchJson(DsBatchVO paramCondition) {	
		List<DsBatchVO> rstList = null;
		
		String projectName = paramCondition.getProjectName(); // 프로젝트명
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("jsonView"); 
		
		try {
			if (projectName.equals("SHUB_BAT")) {
				rstList = batchService.getShubBatchList(paramCondition);
			} else {
				rstList = batchService.getDsBatchList(paramCondition);
			}
			
			mav.addObject("chkResult", "OK");
		} catch (Exception e) {
			mav.addObject("chkResult", "FAIL");
			mav.addObject("errorMSg", e.toString());
		}
		
		mav.addObject("paramCondition", paramCondition);
		mav.addObject("rstList", rstList);
		
		return mav;
	}	
	
	
	/*******************************
	 * 운영정보 배치 리스트(표)
	 * @param 
	 * @return ModelAndView
	 *******************************/	
	@RequestMapping("/listSimpleSossBatch.do")
	public ModelAndView listSimpleSossBatch() {	
		DsBatchVO searchCondition = new DsBatchVO();
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/bat/listSimpleSossBatch");
		mav.addObject("searchCondition", searchCondition);
		
		return mav;
	}	
	
	/*******************************
	 * 운영정보 배치 리스트(표) 조회
	 * @param DsBatchVO
	 * @return ModelAndView
	 * @throws Exception
	 *******************************/	
	@RequestMapping("/listSimpleSossBatchJson.do")
	public @ResponseBody ModelAndView listSimpleSossBatchJson(DsBatchVO paramCondition) {	
		List<DsBatchVO> rstList = null;
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("jsonView"); 
		
		try {
			int count = batchService.getCountSimpleSossBatchList(paramCondition); 
			
			PageVO pageVO = new PageVO(count, paramCondition.getCurPage(), paramCondition.getPageScale());
			
			int start = pageVO.getPageBegin(); // 시작번호
			int end = pageVO.getPageEnd(); // 끝번호
			
			paramCondition.setStart(start);
			paramCondition.setEnd(end);
			
			rstList = batchService.getSimpleSossBatchList(paramCondition);
			mav.addObject("chkResult", "OK");
			mav.addObject("pageVO", pageVO);
			mav.addObject("count", count);
		} catch (Exception e) {
			mav.addObject("chkResult", "FAIL");
			mav.addObject("errorMSg", e.toString());
		}
		
		mav.addObject("paramCondition", paramCondition);
		mav.addObject("rstList", rstList);
		
		return mav;
	}	
}
