package com.shinsegae.smon.incident.contents;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae.smon.model.DefectMessageVO;
import com.shinsegae.smon.model.DefectVO;
import com.shinsegae.smon.model.UserVO;



/*******************************
 * 장애 등급 확인 컨트롤러
 * @author 175582
 * @since 2018.02.13
 *******************************/
@RequestMapping("/defect")
@Controller
public class DefectController {
	
	protected static Logger logger = LoggerFactory.getLogger(DefectController.class.getName());
	
	@Autowired
	DefectService defectService;

	/*******************************
	 * 장애 등급 리스트 작성 화면
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/findDefect.do")
    public ModelAndView findDefect() {
		logger.info("-----findDefect.do------");
        
		ModelAndView mav = new ModelAndView();

        Calendar 	cal = 	Calendar.getInstance();
	    Date 		dt 	=	(Date) cal.getTime();
	  
	    SimpleDateFormat 	sdf 	= 	new SimpleDateFormat("yyyy-MM-dd");
	    SimpleDateFormat 	sdfg 	= 	new SimpleDateFormat("HH:mm");
	    String 				temp 	= 	sdf.format(dt).toString() + "T" + sdfg.format(dt).toString();
	   
	    mav.addObject("cal",temp);
        mav.setViewName("defect/findDefect");
        
        return mav;
    }	
	
	
	/*******************************
	 * 장애 등급 리스트 결과 출력 화면
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/defectResult.do")
    public ModelAndView defectResult(DefectVO defect) throws Exception {
		logger.info("-----defectResult.do------"); 
		ModelAndView mav = new ModelAndView();
		
		HttpServletRequest	request 	= 	((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession 		session 	=  	request.getSession();

		// 날짜 포맷 변환
		String[] 	temp  			= 	defect.getMyLocalDate1().split("T");
		String 		myLocalDate1 	= 	temp[0] + " " + temp[1];
		
		defect.setMyLocalDate1(myLocalDate1);

		String[] 	temp1  			= 	defect.getMyLocalDate2().split("T");
		String 		myLocalDate2 	= 	temp1[0] + " " + temp1[1];
		
		defect.setMyLocalDate2(myLocalDate2);		
		
		UserVO userVO = (UserVO)session.getAttribute("userVO");
		
        String result 		= 	defectService.checkRating(defect);
        String resultStr 	= 	defectService.makeStr(defect, result);
        String receivers 	= 	defectService.findEmployee(result);
        String uId			= 	userVO.getMgrId();
        String uName		=   userVO.getMgrName();
        
        mav.addObject("result"		,	String.valueOf(result));	// 영향 등급
        mav.addObject("resultStr"	,	String.valueOf(resultStr));	// 영향도 메시지
        mav.addObject("defect"		,	defect);					// 영향도 정보가 들어있는 객체
        mav.addObject("receivers"	, 	receivers);					// 수신대상 리스트
        mav.addObject("uId"			,   uId);
        mav.addObject("uName"		,   uName);
      
        logger.info(receivers);
        logger.info(result);
        logger.info(resultStr);
        logger.info(defect.toString());
        mav.setViewName("defect/defectResult");
        
        
        return mav;
    }	

	/*******************************
	 * 장애 등급 도출 결과를 블라썸 톡에 보내고 다시 장애 등급 리스트 작성 화면으로 돌아옴
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/sendDefect.do")
    public ModelAndView sendDefect(DefectMessageVO defectMessage, DefectVO defect, HttpServletRequest request) throws Exception {
        logger.info("-----sendDefect.do------");     
		ModelAndView mav = new ModelAndView();   
		
        // 디비에 리스트 넣기
        HashMap<String, Object> params = new HashMap<String, Object>();
       
        params.put("defect",defectService.changeCode(defect));
  
        // 세션에서 로그인 사번 불러오기.
        HttpServletRequest 	req 		= 	((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession 		session 	=  	req.getSession();
		
		params.put("sId"				, 	(String)session.getAttribute("id")	);		
        params.put("defectMessage"		,	request.getParameter("holdtext")	); 
        params.put("defectGrade"		,	defectMessage.getDefectGrade()		);
        
        defectService.insertData(params);
        
        Calendar 	cal 	= 		Calendar.getInstance();
	    Date 		dt 		= 		(Date) cal.getTime();
	    
	    SimpleDateFormat dateHead 	= 	new SimpleDateFormat("yyyy-MM-dd");
	    SimpleDateFormat dateTail 	= 	new SimpleDateFormat("HH:mm");
	    
	    String temp = dateHead.format(dt).toString() + "T" + dateTail.format(dt).toString();
	    
	    mav.addObject("cal",temp);
        mav.setViewName("defect/findDefect");
        
        return mav;
    }	
	
	/*******************************
	 * 장애 결과 현황 리스트 화면
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/defectList.do")
    public ModelAndView findDefectList(DefectMessageVO defectMessage, HttpServletRequest request) throws Exception {
		logger.info("-----defectList.do------");  
		
		ModelAndView mav = new ModelAndView();          
        mav.setViewName("defect/defectList");
     
        return mav;
    }	
	
	/*******************************
	 * 리스트 조회
	 * @return list
	 *******************************/
	@ResponseBody
	@RequestMapping(value="/ajax/list", method=RequestMethod.POST)
	public List<HashMap<String, Object>> selectList(
			@RequestParam(value="effect")	 		String effect		,
			@RequestParam(value="effectRange") 		String effectRange	,
			@RequestParam(value="targetSystem") 	String targetSystem	,
			@RequestParam(value="status") 			String status		,
			@RequestParam(value="defectGrade") 		String defectGrade	,
			@RequestParam(value="period") 			String period			
		) {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("effect"			,	effect			);
		params.put("effectRange"	, 	effectRange		);
		params.put("targetSystem"	, 	targetSystem	);
		params.put("status"			, 	status			);
		params.put("defectGrade"	, 	defectGrade		);
		params.put("period"			, 	period			);
		
		List<HashMap<String, Object>> list = null;
		
		try {
			list = defectService.selectList(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("장애 리스트 조회 에러", e);
		}
		
		return list;
    }
	
/*	@RequestMapping(value="/detail", method=RequestMethod.GET)
	public ModelAndView detailView(HttpServletRequest req, HttpServletResponse res) {
        ModelAndView mv = new ModelAndView();
        
        mv.addObject("seq"	, req.getParameter("seq"));
        mv.addObject("isNew", req.getParameter("isNew"));
        mv.setViewName("defect/detailDefect");
        
        return mv;
    }*/
	
	/*******************************
	 * 현재 상태 업데이트 후 리스트 화면 출력
	 * @return ModelAndView
	 *******************************/
	@RequestMapping(value="/updateStatus", method=RequestMethod.POST)
	public ModelAndView updateStatus(HttpServletRequest req, HttpServletResponse res) {
		logger.info("-----updateStatus.do-----");
		
		ModelAndView mv = new ModelAndView();
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		params.put("rowId"	, req.getParameter("rowId"));
		params.put("status"	, defectService.changeStatusToCode(req.getParameter("status")));
        
		defectService.updateStatus(params);

        mv.setViewName("defect/defectList");
        
        return mv;
    }
}
