package com.shinsegae.smon.security.code;

import java.util.HashMap;
import java.util.List;
//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae.smon.util.CastingJson;

@RequestMapping("/jekinsInfo")
@Controller
public class JenkinsInfoController {


	@Autowired
	JenkinsInfoService jenkinsInfoService;
	@Autowired
    CastingJson json;

	//private Logger logger = Logger.getLogger(getClass());

	/**
	 * @Method Name : Jenkins 개선현황
	 * @작성일 : 2018. 6. 8.
	 * @작성자 :
	 * @변경이력 :
	 * @Method 설명 :
	 * @return
	 */
	@RequestMapping("/jenkinsFixedInfo")
    public ModelAndView jenkinsFixedInfo() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("mtr/detail/jenkinsInfo/jenkinsFixedInfo");

        return mv;
    }

	/**
	 * @Method Name : Jenkins Combobox
	 * @작성일 : 2018. 6. 8.
	 * @작성자 :
	 * @변경이력 :
	 * @Method 설명 :
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/jenkinsTotalSystemList", method=RequestMethod.POST)
	public String jenkinsTotalSystemList(@RequestParam HashMap<String,String> map) {
		String strReturn = "";
		try {
			strReturn = json.setJsonData(jenkinsInfoService.selectTotalJobNm(map));
		} catch(Exception e) {
			//logger.debug("Exception : " + e.getMessage());
		}
		return strReturn;
	}


	/**
	 * @Method Name : Jenkins 개선현황
	 * @작성일 : 2018. 6. 8.
	 * @작성자 :
	 * @변경이력 :
	 * @Method 설명 :
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/jenkinsFixedInfoList", method=RequestMethod.POST)
	public String jenkinsFixedInfoList(@RequestParam HashMap<String,String> map) {
		String strReturn = "";
		try {
			// 총합계 조회
			List<HashMap<String, Object>> list = jenkinsInfoService.selectJenkinsTotalInfo(map);
			// 월별 개선 건수 조회
			list.addAll(jenkinsInfoService.selectFixedCntByMonth(map));
			strReturn = json.setJsonData(list);
		} catch(Exception e) {
			//logger.debug("Exception : " + e.getMessage());
		}
		return strReturn;
	}

	/**
	 * @Method Name : Jenkins 연간현황
	 * @작성일 : 2018. 6. 8.
	 * @작성자 :
	 * @변경이력 :
	 * @Method 설명 :
	 * @return
	 */
	@RequestMapping("/jenkins")
    public ModelAndView jenkins() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("mtr/detail/jenkinsInfo/jenkinsInfo");

        return mv;
    }

	/**
	 * @Method Name : Jenkins 연간현황 전체 정보
	 * @작성일 : 2018. 6. 8.
	 * @작성자 :
	 * @변경이력 :
	 * @Method 설명 :
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/jenkinsTotalInfo", method=RequestMethod.POST)
	public String jenkinsTotalInfo(@RequestParam HashMap<String,String> map) {
		String strReturn = "";
		try {
			strReturn = json.setJsonData(jenkinsInfoService.selectJenkinsTotalInfo(map));
		} catch(Exception e) {
			//logger.debug("Exception : " + e.getMessage());
		}
		return strReturn;
	}

	
	/**
	 * @Method Name : Jenkins 연간현황 상세정보
	 * @작성일 : 2018. 6. 8.
	 * @작성자 :
	 * @변경이력 :
	 * @Method 설명 :
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/jenkinsInfoList", method=RequestMethod.POST)
	public String jenkinsInfoList(@RequestParam HashMap<String,String> map) {
		String strReturn = "";
		try {
			strReturn = json.setJsonJqGridData(jenkinsInfoService.selectJenkinsJobInfo(map));
		} catch(Exception e) {
			//logger.debug("Exception : " + e.getMessage());
		}
		return strReturn;
	}

	/**
	 * @Method Name : Jenkins 룰셋검출현황
	 * @작성일 : 2018. 6. 8.
	 * @작성자 :
	 * @변경이력 :
	 * @Method 설명 :
	 * @return
	 */
	@RequestMapping("/jenkinsRuleSetInfo")
    public ModelAndView jenkinsRuleSetInfo() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("mtr/detail/jenkinsInfo/jenkinsRuleSetInfo");

        return mv;
    }


	/**
	 * @Method Name : Jenkins 등록시스템 정보 조회
	 * @작성일 : 2018. 6. 8.
	 * @작성자 :
	 * @변경이력 :
	 * @Method 설명 :
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/jenkinsRegisteredSystem", method=RequestMethod.POST)
	public String jenkinsRegisteredSystem(@RequestParam HashMap<String,Object> map) {
		String strReturn = "";
		try {
			strReturn = json.setJsonData(jenkinsInfoService.selectJenkinsRegisteredSystem(map));
		} catch(Exception e) {
			//logger.debug("Exception : " + e.getMessage());
		}
		return strReturn;
	}

	/**
	 * @Method Name : Jenkins 룰셋 검출현황
	 * @작성일 : 2018. 6. 8.
	 * @작성자 :
	 * @변경이력 :
	 * @Method 설명 :
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/jenkinsRuleSetInfoList", method=RequestMethod.POST)
	public String jenkinsRuleSetInfoList(@RequestParam HashMap<String,Object> map) throws Exception {
		String strReturn = "";
		try {
			strReturn = json.setJsonJqGridData(jenkinsInfoService.selectJenkinsRuleSetInfoList(map));
		} catch(Exception e) {
			//logger.debug("Exception : " + e.getMessage());
		}
		return strReturn;
	}
}
