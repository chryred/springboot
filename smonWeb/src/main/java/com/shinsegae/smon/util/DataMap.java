package com.shinsegae.smon.util;


import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

/**
 * @author name
 *
 */
public class DataMap<K, V> extends HashMap<K, V>
{
	private static final long serialVersionUID = 1L;
	
	@Override
	public V get(Object key)
	{
		return (V)getString(key.toString());
	}

	public String getString(String key)
	{
		return getString(key, "");
	}

	public String getString(String key, String defaultString)
	{
		return nvl(super.get(key)).equals("") ? defaultString : nvl(super.get(key));
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
	
	public boolean getBoolean(String key){
		return Boolean.valueOf(getString(key, ""));
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
		}
		catch(NumberFormatException nfe)
		{
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
		}
		catch(NumberFormatException nfe)
		{
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
		}
		catch(NumberFormatException nfe)
		{
			nReturnValue = defaultValue;
		}
		return nReturnValue;
	}

	/** null 체크후 기본값으로 리턴 */
	private String nvl(Object obj)
	{
		if(obj == null) return "";
		else return nvl(obj.toString());
	}

	/** null 체크후 기본값으로 리턴 */
	private String nvl(String val)
	{
		return nvl(val, "");
	}

	/** null 체크후 기본값으로 리턴 */
	private String nvl(String val, String defaultValue)
	{
		if(val == null || val.equals("null") || val.equals("")) val = defaultValue;
		return val;
	}

	/** null 체크후 기본값으로 리턴 */
	private String[] nvl(String values[])
	{
		for(int i = 0; i < values.length; i++)
		{
			values[i] = nvl(values[i]);
		}
		return values;
	}

	/** unicode를 html 형식에 맞게 변환 */
	private String validFormValueNC(String val)
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
		val = val.replace("&#45;", "-");
		val = val.replace("&#42;", "*");
		// val = val.replace("/", "&#47;");
		val = replaceXSS(val);

		return val;
	}

	private String validFormValue(Object obj)
	{
		return validFormValue(nvl(obj));
	}

	/** html 태그를 unicode 형식에 맞게 변환 */
	private String validFormValue(String val)
	{
		val = nvl(val);
		// val = val.replace("#", "&#35;");
		// val = val.replace("&", "&#38;");
		val = val.replace("<", "&lt;");
		val = val.replace(">", "&gt;");
		val = val.replace("\'", "&#39;");
		val = val.replace("\"", "&quot;");
		val = val.replace("\\(", "&#40;");
		val = val.replace("\\)", "&#41;");
		val = val.replace("+", "&#43;");
		val = val.replace("|", "&#166;");
		val = val.replace("-", "&#45;");
		val = val.replace("*", "&#42;");
		// val = val.replace("/", "&#47;");
		val = replaceXSS(val);

		return val;
	}

	/** XSS 처리 */
	private String replaceXSS(String str)
	{
		str = str.replaceAll("<(?i)script(.*?)>", "&lt;script&gt;");
		str = str.replaceAll("<(/)(?i)script>", "&lt;/script&gt;");
		str = str.replaceAll("&lt;(?i)script(.*?)&gt;", "&lt;script&gt;");
		str = str.replaceAll("&lt;(/)(?i)script&gt;", "&lt;/script&gt;");

		return str;
	}

	/**
	 * box 객체에 담겨져있는 모든 key, value 를 String 타입으로 반환한다.
	 * 
	 * @return String 모든 key, value 를 String 타입으로 반환한다.
	 */
	public synchronized String toString()
	{
		int max = super.size() - 1;
		StringBuilder sbuilder = new StringBuilder();
		Iterator iter = super.keySet().iterator();
		String key = "";
		while(iter.hasNext())
		{
			key = (String) iter.next();
			if(sbuilder.length() > 0) sbuilder.append(", ");
			sbuilder.append(key + " : " + getString(key));
		}

		return "DataMap[" + sbuilder.toString() + "]";
	}
}
