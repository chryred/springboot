package com.shinsegae.smon.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oreilly.servlet.MultipartRequest;

/**
 * @author name
 */
public class RequestMap<K, V> extends HashMap<K, V>
{
	private final static Logger log = LoggerFactory.getLogger(RequestMap.class);
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private MultipartRequest multi;
	
	
	public void setRequest(HttpServletRequest request)
	{
		this.request = request;
	}
	
	
	public void setResponse(HttpServletResponse response)
	{
		this.response = response;
	}
	
	
	public MultipartRequest getMulti()
	{
		return multi;
	}
	
	
	public void setMulti(MultipartRequest multi)
	{
		this.multi = multi;
	}
	
	
	public static RequestMap getRequestMap(HttpServletRequest request, HttpServletResponse response)
	{
		RequestMap param = new RequestMap();
		if (isMultipartForm(request))
		{
		}
		else
		{
			param = getParameters(param, request);
		}
		param.setRequest(request);
		param.setResponse(response);
		
		return param;
	}
	
	
	public static RequestMap getRequestMapNotMultipart(HttpServletRequest request, HttpServletResponse response)
	{
		RequestMap param = new RequestMap();
		param = getParameters(param, request);
		param.setRequest(request);
		param.setResponse(response);
		
		return param;
	}
	
	
	public V put(K key, V value)
	{
		return super.put(key, value);
	}
	
	
	public boolean getBoolean(String key)
	{
		
		String b = validFormValue(super.get(key));
		
		return Boolean.valueOf(b);
	}
	
	
	public String getString(String key)
	{
		return validFormValue(super.get(key));
	}
	
	
	public String getString(String key, String defaultString)
	{
		return nvl(super.get(key)).equals("") ? defaultString : validFormValue(super.get(key));
	}
	
	
	public String getStringNC(String key)
	{
		return validFormValueNC(nvl(super.get(key)));
	}
	
	
	public int getInt(String key)
	{
		return getInt(key, 0);
	}
	
	
	public int getInt(String key, int defalutInt)
	{
		return nvl(super.get(key)).equals("") ? defalutInt : (int) parseDouble(nvl(super.get(key)));
	}
	
	
	public long getLong(String key)
	{
		return getLong(key, 0L);
	}
	
	
	public long getLong(String key, long defalutLong)
	{
		return nvl(super.get(key)).equals("") ? defalutLong : parseLong(nvl(super.get(key)));
	}
	
	
	public float getFloat(String key)
	{
		return getFloat(key, 0.0f);
	}
	
	
	public float getFloat(String key, float defalutFloat)
	{
		return nvl(super.get(key)).equals("") ? defalutFloat : (float) parseDouble(nvl(super.get(key)));
	}
	
	
	public double getDouble(String key)
	{
		return getDouble(key, 0.0);
	}
	
	
	public double getDouble(String key, double defalutDouble)
	{
		return nvl(super.get(key)).equals("") ? defalutDouble : parseDouble(nvl(super.get(key)));
	}
	
	
	public Object getObject(String key)
	{
		return super.get(key);
	}
	
	
	public List<String> getList(String key)
	{
		try
		{
			return super.get(key) == null ? new ArrayList<String>() : (List<String>) super.get(key);
		} catch (Exception e)
		{
			log.error("[Exception] com.shinsegae.utils.RequestMap.getList : ", e.getMessage());
			List<String> returnList = new ArrayList<String>();
			returnList.add(getString(key));
			return returnList;
		}
	}
	
	
	public File getFile(String key)
	{
		try
		{
			return (File) super.get(key);
		} catch (ClassCastException e)
		{
			log.error("[ClassCastException] com.shinsegae.utils.RequestMap.getFile : ", e.getMessage());
			return null;
		}
	}
	
	
	public HttpSession getHttpSession()
	{
		return this.request.getSession(false);
	}
	
	
	public HttpServletRequest getRequest()
	{
		return this.request;
	}
	
	
	public HttpServletRequest getMultipartRequest()
	{
		HttpServletRequest request = null;
		try
		{
			request = (HttpServletRequest) super.get("MultipartRequest");
		} catch (Exception e)
		{
			log.error("[Exception] com.shinsegae.utils.RequestMap.getMultipartRequest : ", e.getMessage());
		}
		return request;
	}
	
	
	public HttpServletResponse getResponse()
	{
		return this.response;
	}
	
	
	public void setSession(String key, Object obj)
	{
		HttpSession session = this.getHttpSession();
		if (session != null)
		{
			session.setAttribute(key, obj);
		}
	}
	
	
	public void setSession(String key, int val)
	{
		setSession(key, (double) val);
	}
	
	
	public void setSession(String key, float val)
	{
		setSession(key, (double) val);
	}
	
	
	public void setSession(String key, double val)
	{
		HttpSession session = this.getHttpSession();
		if (session != null)
		{
			session.setAttribute(key, val);
		}
	}
	
	
	public void setSession(String key, long val)
	{
		HttpSession session = this.getHttpSession();
		if (session != null)
		{
			session.setAttribute(key, val);
		}
	}
	
	
	public void setSession(String key, char val)
	{
		HttpSession session = this.getHttpSession();
		if (session != null)
		{
			session.setAttribute(key, val);
		}
	}
	
	
	public void setSession(String key, byte val)
	{
		HttpSession session = this.getHttpSession();
		if (session != null)
		{
			session.setAttribute(key, val);
		}
	}
	
	
	public void removeSession(String key)
	{
		HttpSession session = this.getHttpSession();
		if (session != null)
		{
			session.removeAttribute(key);
		}
	}
	
	
	public void invalidateSession()
	{
		HttpSession session = this.getHttpSession();
		if (session != null)
		{
			session.invalidate();
		}
	}
	
	
	public Object getSessionObject(String key)
	{
		HttpSession session = this.getHttpSession();
		if (session != null)
		{
			return nvlObject(session.getAttribute(key));
		}
		return "";
	}
	
	
	public String getSession(String key)
	{
		HttpSession session = this.getHttpSession();
		if (session != null)
		{
			return nvl(session.getAttribute(key));
		}
		return "";
	}
	
	
	public String getSessionId()
	{
		HttpSession session = this.getHttpSession();
		String sessionId = "";
		
		if (session != null)
		{
			sessionId = session.getId();
		}
		return sessionId;
	}
	
	
	public boolean hasSession(String key)
	{
		return !getSession(key).equals("");
	}
	
	
	public void setSessionMaxInactiveInterval(int maxInterval)
	{
		HttpSession session = this.getHttpSession();
		if (session != null)
		{
			session.setMaxInactiveInterval(maxInterval);
		}
	}
	
	
	public void setCookie(String cookieName, String cookieValue, int maxage)
	{
		setCookie(cookieName, cookieValue, this.request.getServerName(), "/", maxage);
	}
	
	
	// 브라우져를 닫으면 바로 삭제
	public void setCookie(String cookieName, String cookieValue)
	{
		setCookie(cookieName, cookieValue, this.request.getServerName(), "/", -100);
	}
	
	
	// 브라우져를 닫으면 바로 삭제
	public void setCookie(String cookieName, String cookieValue, String domain, String path)
	{
		setCookie(cookieName, cookieValue, domain, path, -100);
	}
	
	
	public void setCookie(String cookieName, String cookieValue, String domain, String path, int maxage)
	{
		Cookie cookie = null;
		
		String cookieStr = "";
		
		try
		{
			if ((cookieName != null) && (!("".equals(cookieName))))
			{
				cookie = new Cookie(cookieName, java.net.URLEncoder.encode(cookieValue));
				
				if (!domain.equals("localhost") && !domain.equals("127.0.0.1"))
				{
					cookie.setDomain(domain);
				}
				cookie.setPath(path);
				
				// 최대값 제한 설정
				if(maxage > 3600 ) maxage = 3600; 
				
				if (maxage != -100)
				{
					cookie.setMaxAge(maxage);
				}
				
				this.response.addCookie(cookie);
			}
		} catch (Exception e)
		{
			log.error("[Exception] com.shinsegae.utils.RequestMap.setCookie : ", e.getMessage());
		}
	}
	
	
	public String getCookie(String cookieName)
	{
		String cookieValue = "";
		try
		{
			Cookie[] cookies = this.request.getCookies();
			
			if (cookies != null)
			{
				for (int i = 0; i < cookies.length; i++)
				{
					Cookie cookie = cookies[i];
					
					if (cookie.getName().equals(cookieName))
					{
						cookieValue = java.net.URLDecoder.decode(cookie.getValue());
						break;
					}
				}
			}
		} catch (Exception e)
		{
			log.error("[Exception] com.shinsegae.utils.RequestMap.getCookie : ", e.getMessage());
		}
		
		return cookieValue;
	}
	
	
	public void setAttr(String key, int val)
	{
		setAttr(key, (double) val);
	}
	
	
	public void setAttr(String key, float val)
	{
		setAttr(key, (double) val);
	}
	
	
	public void setAttr(String key, double val)
	{
		HttpServletRequest request = this.getRequest();
		if (request != null)
		{
			request.setAttribute(key, val);
		}
	}
	
	
	public void setAttr(String key, long val)
	{
		HttpServletRequest request = this.getRequest();
		if (request != null)
		{
			request.setAttribute(key, val);
		}
	}
	
	
	public void setAttr(String key, char val)
	{
		HttpServletRequest request = this.getRequest();
		if (request != null)
		{
			request.setAttribute(key, val);
		}
	}
	
	
	public void setAttr(String key, byte val)
	{
		HttpServletRequest request = this.getRequest();
		if (request != null)
		{
			request.setAttribute(key, val);
		}
	}
	
	
	public String getAttr(String key)
	{
		HttpServletRequest request = this.getRequest();
		if (request != null)
		{
			return nvl(request.getAttribute(key));
		}
		return "";
	}
	
	
	public Object getAttrObject(String key)
	{
		HttpServletRequest request = this.getRequest();
		if (request != null)
		{
			return nvlObject(request.getAttribute(key));
		}
		return "";
	}
	
	
	/** String 형 타입을 double 타입으로 변환 */
	private double parseDouble(String checkStr)
	{
		return parseDouble(checkStr, 0.0);
	}
	
	
	private double parseDouble(String checkStr, double defaultValue)
	{
		double nReturnValue;
		try
		{
			nReturnValue = Double.parseDouble(checkStr);
		} catch (NumberFormatException nfe)
		{
			log.error("[NumberFormatException] com.shinsegae.utils.RequestMap.parseDouble : ", nfe.getMessage());
			nReturnValue = defaultValue;
		}
		return nReturnValue;
	}
	
	
	/** String 형 타입을 long 타입으로 변환 */
	public static long parseLong(String checkStr)
	{
		return parseLong(checkStr, 0);
	}
	
	
	public static long parseLong(String checkStr, long defaultValue)
	{
		long nReturnValue;
		try
		{
			nReturnValue = Long.parseLong(checkStr);
		} catch (NumberFormatException nfe)
		{
			log.error("[NumberFormatException] com.shinsegae.utils.RequestMap.parseLong : ", nfe.getMessage());
			nReturnValue = defaultValue;
		}
		return nReturnValue;
	}
	
	
	/** String 형 타입을 int 형 타입으로 변환 */
	private int parseInt(String checkStr, int defaultValue)
	{
		int nReturnValue;
		try
		{
			nReturnValue = Integer.parseInt(checkStr);
		} catch (NumberFormatException nfe)
		{
			log.error("[NumberFormatException] com.shinsegae.utils.RequestMap.parseInt : ", nfe.getMessage());
			nReturnValue = defaultValue;
		}
		return nReturnValue;
	}
	
	
	/** null 체크후 기본값으로 리턴 */
	private static Object nvlObject(Object obj)
	{
		if (obj == null)
			return "";
		else
			return obj;
	}
	
	
	/** null 체크후 기본값으로 리턴 */
	private static String nvl(Object obj)
	{
		if (obj == null)
			return "";
		else
			return nvl(obj.toString());
	}
	
	
	/** null 체크후 기본값으로 리턴 */
	private static String nvl(String val)
	{
		return nvl(val, "");
	}
	
	
	/** null 체크후 기본값으로 리턴 */
	private static String nvl(String val, String defaultValue)
	{
		if (val == null || val.equals("null") || val.equals(""))
			val = defaultValue;
		return val;
	}
	
	
	/** null 체크후 기본값으로 리턴 */
	private static String[] nvl(String values[])
	{
		for (int i = 0; i < values.length; i++)
		{
			values[i] = nvl(values[i]);
		}
		return values;
	}
	
	
	/** unicode를 html 형식에 맞게 변환 */
	private static String validFormValueNC(String val)
	{
		val = nvl(val);
		// val = val.replace("#", "&#35;");
		// val = val.replace("&", "&#38;");
		val = val.replace("&lt;", "<");
		val = val.replace("&gt;", ">");
		val = val.replace("&#39;", "\'");
		val = val.replace("&quot;", "\"");
		val = val.replace("&#40;", "\\(");
		val = val.replace("&#41;", "\\)");
		val = val.replace("&#43;", "+");
		val = val.replace("&#166;", "|");
		//val = val.replace("&#45;", "-");
		val = val.replace("&#42;", "*");
		// val = val.replace("/", "&#47;");
		val = replaceXSS(val);
		
		return val;
	}
	
	
	private static String validFormValue(Object obj)
	{
		return validFormValue(nvl(obj));
	}
	
	
	/** html 태그를 unicode 형식에 맞게 변환 */
	private static String validFormValue(String val)
	{
		val = nvl(val);
		
		val = val.replace("\'", "&#39;");
		val = val.replace("\"", "&quot;");
		val = val.replace("\\(", "&#40;");
		val = val.replace("\\)", "&#41;");
		val = val.replace("+", "&#43;");
		val = val.replace("*", "&#42;");
		//val = val.replace("#", "&#35;");
		val = val.replace("&", "&#38;");
		val = val.replace("<", "&lt;");
		val = val.replace(">", "&gt;");
		val = val.replace("|", "&#166;");
		val = val.replace("-", "&#45;");
		val = val.replace("/", "&#47;");
		val = val.replace("<", " ");
		val = val.replace(">", " ");
		val = val.replace("\'", "\'\'");
		val = val.replace("\"", "\'\'");
		val = val.replace("(", "&#40;");
		val = val.replace(")", "&#41;");
		val = val.replace("+", "&#43;");
		val = val.replace("|", "&#166;");
		val = val.replace("-", " &#45;");
		val = val.replace("*", "&#42;");
		val = val.replace("$", "");
		val = replaceXSS(val);
		return val;
	}
	
	
	/** XSS 처리 */
	private static String replaceXSS(String str)
	{
		str = str.replaceAll("<(?i)script(.*?)>", "&lt;script&gt;");
		str = str.replaceAll("<(/)(?i)script>", "&lt;/script&gt;");
		str = str.replaceAll("&lt;(?i)script(.*?)&gt;", "&lt;script&gt;");
		str = str.replaceAll("&lt;(/)(?i)script&gt;", "&lt;/script&gt;");
		
		return str;
	}
	
	
	public static boolean isMultipartForm(HttpServletRequest request)
	{
		String contentType = nvl(request.getContentType());
		return contentType.indexOf("multipart/form") >= 0;
	}
	
	
	public static RequestMap getParameters(RequestMap requestMap, HttpServletRequest request)
	{
		String paramName = "";
		for (Enumeration e = request.getParameterNames(); e.hasMoreElements();)
		{
			paramName = (String) e.nextElement();
			if (request.getParameterValues(paramName) != null && request.getParameterValues(paramName).length > 1)
			{
				List<String> valuesList = new ArrayList<String>();
				for (int i = 0; i < request.getParameterValues(paramName).length; i++)
				{
					valuesList.add(validFormValue(request.getParameterValues(paramName)[i]));
				}
				requestMap.put(paramName, valuesList);
			}
			else
			{
				requestMap.put(paramName, validFormValue(request.getParameter(paramName)));
			}
			
		}
		//pageSize 무조건 추가
		requestMap.put("pageSize", ConstantsInterface.DISP_PAGE_ROW_NUM);
		
		return requestMap;
	}
	
	
	public static RequestMap getMultiParameters(RequestMap requestMap, MultipartRequest multi, String absoluteUploadPath)
	{
		String paramName = "";
		String debug = "";
		
		for (Enumeration e = multi.getParameterNames(); e.hasMoreElements();)
		{
			paramName = (String) e.nextElement();
			if (multi.getParameterValues(paramName) != null && multi.getParameterValues(paramName).length > 1)
			{
				List<String> valuesList = new ArrayList<String>();
				for (int i = 0; i < multi.getParameterValues(paramName).length; i++)
				{
					valuesList.add(validFormValue(multi.getParameterValues(paramName)[i]));
				}
				requestMap.put(paramName, valuesList);
			}
			else
			{
				requestMap.put(paramName, validFormValue(multi.getParameter(paramName)));
			}
		}
		for (Enumeration e = multi.getFileNames(); e.hasMoreElements();)
		{
			paramName = (String) e.nextElement();
			requestMap.put(paramName + ".name", multi.getFilesystemName(paramName));
			File file = new File(absoluteUploadPath + multi.getFilesystemName(paramName));

			try
			{
				file = ComUtil.fileRename(file, "rename");
				requestMap.put(paramName, file);
				requestMap.put(paramName + ".rename", file.getName());
			} catch (IOException e1)
			{
				log.error("[IOException] com.shinsegae.utils.RequestMap.getMultiParameters : ", e1.getMessage());
			}
		}
		return requestMap;
	}
	
	
	/**
	 * box 객체에 담겨져있는 모든 key, value 를 queryString으로 반환한다.
	 * @return
	 */
	public String getQueryString()
	{
		StringBuilder sbuilder = new StringBuilder();
		Iterator iter = super.keySet().iterator();
		String key = "";
		while (iter.hasNext())
		{
			key = (String) iter.next();
			if (sbuilder.length() > 0)
				sbuilder.append("&");
			sbuilder.append(key + "=" + getStringNC(key).replace("%", "\\%").replace("&", "%26").replace("\n", " ").replace("\r\n", " "));
		}
		return sbuilder.toString();
	}
	
	
	/**
	 * box 객체에 담겨져있는 모든 key, value 를 String 타입으로 반환한다.
	 * @return String 모든 key, value 를 String 타입으로 반환한다.
	 */
	public synchronized String toString()
	{
		StringBuilder sbuilder = new StringBuilder();
		Iterator iter = super.keySet().iterator();
		String key = "";
		while (iter.hasNext())
		{
			key = (String) iter.next();
			if (sbuilder.length() > 0)
				sbuilder.append(", ");
			sbuilder.append(key + " : " + getString(key));
		}
		
		return sbuilder.toString();
	}
}
