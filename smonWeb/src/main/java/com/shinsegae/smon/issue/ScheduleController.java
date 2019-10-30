package com.shinsegae.smon.issue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinsegae.smon.schedule.config.SpringScheduleService;
import com.shinsegae.smon.util.CastingJson;

@Controller
public class ScheduleController {

    @Autowired
    ScheduleService svc;
    @Autowired
    CastingJson json; 
    
    @RequestMapping("/searchComboData")
    public @ResponseBody String searchComboData(@RequestParam HashMap<String,String> map) {        
        
        String data = ""; 
        
        try{
            List<HashMap<String, Object>> list = svc.searchComboData(map);
            
            data = json.setJsonData(list);
        }catch(Exception e){
            e.printStackTrace();
            data = null;
        }
        return data;
    }
    
    @RequestMapping("/searchProjectGridData")
    public @ResponseBody String searchProjectGridData(@RequestParam HashMap<String,String> map) {        
        String data = ""; 
        
        System.out.println(map.toString());
        try{
            List<HashMap<String, Object>> list = svc.searchProjectGridData(map);
            
            data = json.setJsonDatatableData(list);
            //System.out.println(data);
        }catch(Exception e){
            e.printStackTrace();
            data = null;
        }
        return data;
    }
    
    @RequestMapping("/searchAllData")
    public @ResponseBody String searchAllData(@RequestParam HashMap<String,String> map) {        
        String data = ""; 
        
        try{
            List<HashMap<String, Object>> list = svc.searchAllData(map);
            
            data = json.setJsonData(list);
        }catch(Exception e){
            e.printStackTrace();
            data = null;
        }
        return data;
    }

    
    @RequestMapping("/saveProjectData")
    public @ResponseBody String tranPgm(@RequestParam HashMap<String,String> map) {
        List list = new ArrayList();
        String data = ""; 
        
        try{
            
            svc.updateGrid(map);
            
            list.add("SUCCESS");
        }catch(Exception e){
            e.printStackTrace();
            list.add("FAIL");

        }finally{
            data = json.setJsonData(list);
        }
        return data; 
    }
    
}
