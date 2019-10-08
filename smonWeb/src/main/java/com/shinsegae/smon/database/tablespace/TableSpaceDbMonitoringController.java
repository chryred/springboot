package com.shinsegae.smon.database.tablespace;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinsegae.smon.util.CastingJson;

@Controller
public class TableSpaceDbMonitoringController {
	@Autowired
    CastingJson json;
    
    @Autowired
    TableSpaceDbMonitoringService tableSpaceDbMonitoringService;

    @RequestMapping("/selectTableSpacePaging.do")
    public @ResponseBody String selectTableSpacePaging(@RequestParam(value="page", required=false) String page,
                                                       @RequestParam(value="rows", required=false) Double rows,
                                                       @RequestParam HashMap<String,Object> paramMap
                                                      ) {        
        
        String data ="";
        
        try{
            
            //총 건수 
            int records = tableSpaceDbMonitoringService.selectDbDictionaryTotalCnt(paramMap);
            
            paramMap.put("page", page);
            paramMap.put("rows", rows);
            paramMap.put("records", records);
            
            data = json.setJsonJqGridData(tableSpaceDbMonitoringService.selectDbDictionaryInfo(paramMap));
        }catch(Exception e){
            e.printStackTrace();
            data = null;
        }
        
        return data;
    }
    
    @RequestMapping("/selectUndoTableSpaceState.do")
    public @ResponseBody String selectUndoTableSpaceState(@RequestParam HashMap<String, Object> map) {        
        
        String data ="";
       
        try{
            data = json.setJsonData(tableSpaceDbMonitoringService.selectDbDictionaryInfoDashboard(map));
            //System.out.println(data);
        }catch(Exception e){
            e.printStackTrace();
            data = null;
        }
        
        return data;
    }
    
    @RequestMapping("/selectTableSpaceState.do")
    public @ResponseBody String selectTableSpaceState(@RequestParam HashMap<String,String> map) {        
        
        String data ="";
       
        try{
         
            data = json.setJsonData(tableSpaceDbMonitoringService.selectDbSpaceState(map));
        }catch(Exception e){
            e.printStackTrace();
            data = null;
        }
        
        return data;
    }
}
