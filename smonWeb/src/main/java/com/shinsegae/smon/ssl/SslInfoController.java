package com.shinsegae.smon.ssl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae.smon.util.CastingJson;

@Controller
public class SslInfoController {
	@Autowired
	CastingJson json;
	
	@Autowired
	SslInfoService sslInfoService;
	
	@RequestMapping(value="/sslInfo")
    public ModelAndView sslInfoManagement(Locale locale, Model model) {

        ModelAndView view = new ModelAndView("mtr/detail/ssl/sslInfoManagement");
        
        return view;
    }
	
	
	@RequestMapping("/sslInfoDataTables")
	public @ResponseBody String sslInfoDataTables() {
	    
	    String data ="";
	   
	    try{
	        data = json.setJsonData(sslInfoService.sslInfoDataTables());
	    }catch(Exception e){
	        e.printStackTrace();
	        data = null;
	    }
	    
	    return data;
	}

}
