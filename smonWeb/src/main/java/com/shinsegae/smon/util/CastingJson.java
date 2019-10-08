package com.shinsegae.smon.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;



@Component
public class CastingJson {
    
    public String setJsonData(List<HashMap<String, Object>> list){
    
    	
        JSONArray childNodes = JSONArray.fromObject(list);   
       

        return childNodes.toString();
    }
    
    
    public String setJsonJqGridData(List<HashMap<String, Object>> list){
        
        Map<String, Object> returnMap = new HashMap<String, Object>();
        
        JSONObject jsonObj = new JSONObject();
        
        Iterator<HashMap<String, Object>> iter =  list.iterator();
        HashMap<String, Object> objectMap;
        
        
        
        List<HashMap<String, Object>> jsonList = new ArrayList<HashMap<String, Object>>();
        
        int index = 1;
        
        
        while(iter.hasNext()){
            
            HashMap<String, Object> jsonMap = new HashMap<String, Object>();
            
            objectMap = iter.next();
            
            jsonMap.put("id", index);
            jsonMap.put("cell", objectMap);
            
            jsonList.add(jsonMap);
            
            index++;
        }
        
        returnMap.put("page", 1);
        returnMap.put("total", 1);
        returnMap.put("records", list.size());
        returnMap.put("rows", jsonList);
        
        jsonObj = JSONObject.fromObject(JSONSerializer.toJSON(returnMap));
        
        return jsonObj.toString();
    }
    
    public String setJsonDatatableData(List<HashMap<String, Object>> list){
        
        Map<String, Object> returnMap = new HashMap<String, Object>();
        
        JSONObject jsonObj = new JSONObject();
        
        Iterator<HashMap<String, Object>> iter =  list.iterator();
        HashMap<String, Object> objectMap;
        
        List<HashMap<String, Object>> jsonList = new ArrayList<HashMap<String, Object>>();
        
        int index = 1;
        
        while(iter.hasNext()){
            
            HashMap<String, Object> jsonMap = new HashMap<String, Object>();
            
            objectMap = iter.next();
            
            jsonMap.put("id", index);
            jsonMap.put("cell", objectMap);
            
            jsonList.add(jsonMap);
            
            index++;
        }
        
        returnMap.put("draw", 1);
        returnMap.put("recordsTotal", list.size());
        returnMap.put("recordsFiltered", list.size());
        returnMap.put("data", list);
        
        jsonObj = JSONObject.fromObject(JSONSerializer.toJSON(returnMap));
        
        return jsonObj.toString();
    }
    
    
    public String setJsonDHtmlxData(List<HashMap<String, Object>> list){
        
        ArrayList returnList = new ArrayList();

        Iterator<HashMap<String, Object>> iterator = list.iterator();
        
        while(iterator.hasNext()){
            
            ArrayList nodeList = new ArrayList();
            
            HashMap<String, Object> map = iterator.next();

            for( String key : map.keySet()){
                nodeList.add(map.get(key));
            }
            
            returnList.add(nodeList);
        }
        
        JSONArray childNodes = JSONArray.fromObject(returnList);

        return childNodes.toString();
    }


    public String setJsonJqGridData(List<HashMap<String, Object>> list, HashMap<String, Object> paramMap){
        
        Map<String, Object> returnMap = new HashMap<String, Object>();
        
        JSONObject jsonObj = new JSONObject();
        
        Iterator<HashMap<String, Object>> iter =  list.iterator();
        HashMap<String, Object> objectMap;
        
        
        
        List<HashMap<String, Object>> jsonList = new ArrayList<HashMap<String, Object>>();
        
        int index = 1;
        
        
        while(iter.hasNext()){
            
            HashMap<String, Object> jsonMap = new HashMap<String, Object>();
            
            objectMap = iter.next();
            
            jsonMap.put("id", index);
            jsonMap.put("cell", objectMap);
            
            jsonList.add(jsonMap);
            
            index++;
        }
        
        int records = (Integer) paramMap.get("records");
        Double rows = (Double) paramMap.get("rows");
        
        double totalTemp =  records / rows;
                
        returnMap.put("page", paramMap.get("page"));
        returnMap.put("total", Math.ceil(totalTemp));
        returnMap.put("records", records);
        returnMap.put("rows", jsonList);
        
        jsonObj = JSONObject.fromObject(JSONSerializer.toJSON(returnMap));
        
        return jsonObj.toString();
    }
    
}
