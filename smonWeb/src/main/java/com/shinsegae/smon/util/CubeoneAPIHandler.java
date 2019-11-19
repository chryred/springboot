package com.shinsegae.smon.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shinsegae.smon.model.CubeoneVO;

public class CubeoneAPIHandler {
	
	private String strCubeoneServer;
	
	
	public CubeoneAPIHandler(String server) {
		strCubeoneServer = server;
	}
	
	public Map<String, Object> callCubeoneAPI(String strCubeoneMethod, CubeoneVO cubeoneVO) {
		StringBuilder sbResult = null;
		URL url = null;
		HttpURLConnection urlConnection = null;
		OutputStream out = null;
		BufferedReader in = null;
		try
		{
			sbResult = new StringBuilder();
			NLogger.debug("strCubeoneServer : ", strCubeoneServer);
			
			String wsUrl = "http://" + strCubeoneServer + "/cubeone/api/" + strCubeoneMethod;

			url = new URL(wsUrl);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);

			out = urlConnection.getOutputStream();
			out.write(cubeoneVO.toParamValue().getBytes("UTF-8"));
			out.flush();

			urlConnection.getInputStream();
			
			in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
			String buffer = null;
			while ((buffer = in.readLine())!= null){
				sbResult.append(buffer);
			}
		} catch (Exception e) {
			sbResult.append("{\"status\":\"500\", \"retMsg\":\"연결도중 오류가 발생하였습니다.\"}");
		} finally {
			try {
				if(out != null) {
					out.close();
				}
				if(in != null) {
					in.close();
				}
			} catch(IOException e) {
				NLogger.debug("IOException : ", e.getMessage());
			}
			
		}
		
		return setValueObject(sbResult.toString());
	}
	
	public Map<String, Object> setValueObject(String strRetMsg) {
		NLogger.debug("strRetMsg : " + strRetMsg);

		Map<String, Object> data = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			data = mapper.readValue(strRetMsg, new TypeReference<Map<String,Object>>(){});
		} catch (JsonParseException e) {
			NLogger.debug("JsonParseException : ", e.getMessage());
		} catch (JsonMappingException e) {
			NLogger.debug("JsonMappingException : ", e.getMessage());
		} catch (IOException e) {
			NLogger.debug("IOException : ", e.getMessage());
		}
		return data;
	}
	
	public static void main(String[] ar) throws Exception {
		CubeoneVO cubeoneVO = new CubeoneVO();
		
		cubeoneVO.setMsg("173917");
		cubeoneVO.setCrudLog(10);
		cubeoneVO.setItemCd("PWD");
		cubeoneVO.setTableName("MGR");
		cubeoneVO.setColumnName("MGR_PWD");
		CubeoneAPIHandler cubeoneAPIHandler = new CubeoneAPIHandler("10.222.6.53");
		System.out.println(cubeoneAPIHandler.callCubeoneAPI("coencbyte", cubeoneVO));
	}
	
}