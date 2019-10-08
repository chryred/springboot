package com.shinsegae.smon.database.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinsegae.smon.util.CastingJson;

@Controller
public class InvalidObjectsDbMonitoringController {
	@Autowired
    CastingJson json;
    
    @Autowired
    InvalidObjectsDbMonitoringService invalidObjectsDbMonitoringService;

    @RequestMapping("/selectInvalidObjects")
    public @ResponseBody HashMap<String, Object> selectInvalidObjects(
	    		@RequestParam(value="rows") int rows,
				@RequestParam(value="page") int page,
				@RequestParam(value="system") String system
    		) {        
        
        HashMap<String, Object> params = new HashMap<String, Object>();
        int start = (rows * (page-1)) + 1;
        int end = page * rows;
        params.put("start", start);
        params.put("end", end);
        params.put("system", system);
        
    	HashMap<String, Object> jqgridResult = new HashMap<String, Object>();
       
        try{
        	List<HashMap<String, Object>> list = invalidObjectsDbMonitoringService.selectDbDictionaryInfo(params);
        	
        	int records = list.size() > 0 ? Integer.parseInt(String.valueOf(list.get(0).get("TOTCNT"))) : 0;
        	int total = records%rows == 0 ? records/rows : records/rows + 1;
    		
    		jqgridResult.put("page", page);
    		jqgridResult.put("records", records);
    		jqgridResult.put("total", total);
    		jqgridResult.put("rows", list);
    		
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return jqgridResult;
    }
    
    @RequestMapping("/selectInvalidObjectsState")
    public @ResponseBody List<HashMap<String, Object>> selectInvalidObjectsState(@RequestParam String system) {        
    	List<HashMap<String, Object>> data = null;
    	
    	try{
    		data = invalidObjectsDbMonitoringService.selectInvalidObjectsState(system);
    	}catch(Exception e){
    		e.printStackTrace();
    		data = null;
    	}
    	
    	return data;
    }
}
