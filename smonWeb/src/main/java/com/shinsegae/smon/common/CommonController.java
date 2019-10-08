package com.shinsegae.smon.common;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinsegae.smon.util.CastingJson;

@Controller
public class CommonController {

	@Autowired
	CommonService commonService;
	
	@Autowired
    CastingJson json; 
	
	@RequestMapping(value="/searchSystemCombo")
	@ResponseBody
    public String searchComboData(@RequestParam HashMap<String,String> map) {        
        
        String data = ""; 
        
        try{
            
            List<HashMap<String, Object>> list = commonService.searchSystemCombo(map);
            
            data = json.setJsonData(list);
            
            //System.out.println(data);
            
        }catch(Exception e){
            e.printStackTrace();
            data = null;
        }
        return data;
    }
}
