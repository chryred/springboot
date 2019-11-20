package com.shinsegae.smon.tool;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae.smon.model.tool.EncDecVO;
import com.shinsegae.smon.model.tool.SystemCodeVO;


/*******************************
 * 개발도구 컨트롤러
 * @author 153712
 * @since 2018.03.19
 *******************************/
@RequestMapping("/tool")
@Controller
public class ToolController {
	@Autowired
	ToolService toolService;
	
	/*******************************
	 * ENC / DEC 화면
	 * @param PmdVO paramVO
	 * @return ModelAndView
	 *******************************/
	@RequestMapping("/encDecView.do")
    public ModelAndView encDecView() {
        ModelAndView mav = new ModelAndView();
        
        EncDecVO searchCondition = new EncDecVO();
        
        List<SystemCodeVO> systemCodes = null;
        
        SystemCodeVO paramVO = new SystemCodeVO();
        	
        paramVO.setGroupCode("003");
        
        try {
        	systemCodes = toolService.getSystemCodes(paramVO);
        } catch (Exception e) {
        	System.out.println(e.toString());        	
        }
        
        mav.setViewName("tool/encDecView");
        
        mav.addObject("searchCondition", searchCondition);
        mav.addObject("systemCodes", systemCodes); // 시스템 콤보
        
        return mav;
    }		
	
	/*******************************
	 * TOOL ENC/DEC 상세 조회
	 * @param DsBatchVO
	 * @return ModelAndView
	 * @throws Exception
	 *******************************/	
	@RequestMapping("/getEncDecData.do")
	public @ResponseBody ModelAndView getEncDecData(EncDecVO paramCondition) {	
		String afterData = "";
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("jsonView"); 
		
		try {
			afterData = toolService.getEncDecData(paramCondition);
			
			/*
			List<Object> supported = new ArrayList<>();
			List<Object> unsupported = new ArrayList<>();
			
			for (Object algorithm : AlgorithmRegistry.getAllPBEAlgorithms()) {
				try {
					StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
					encryptor.setPassword("somePassword");
					encryptor.setAlgorithm(String.valueOf(algorithm));
					String str = "test";
					String encStr = encryptor.encrypt(str);
					String decStr = encryptor.decrypt(encStr);
					supported.add(algorithm);
				} catch (EncryptionOperationNotPossibleException e) {
					unsupported.add(algorithm);
				}
			}
			
			System.out.println(supported.toString());
			System.out.println(unsupported.toString());
			*/
			
			mav.addObject("chkResult", "OK");
			mav.addObject("afterData", afterData);
		} catch (Exception e) {
			mav.addObject("chkResult", "FAIL");
			mav.addObject("errorMSg", e.toString());
		}
		
		mav.addObject("paramCondition", paramCondition);
		
		return mav;
	}	
	
}
