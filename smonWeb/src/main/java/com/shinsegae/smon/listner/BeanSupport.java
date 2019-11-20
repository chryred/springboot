package com.shinsegae.smon.listner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 설명: Bean을 동적으로 취득합니다.(singleTone Life Cycle만 해당)
 * (BatchJobProccess)(BeanSupport.getWebApplicationContext().getBean(beanId)
 */
@Component
public class BeanSupport implements ServletContextListener {

	private static WebApplicationContext webApplicationContext;

	@Override
	public void contextDestroyed(ServletContextEvent contextEvent)
	{
		// do Nothing.
	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent)
	{
		ServletContext servletContext = contextEvent.getServletContext();
		webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	}

	public static WebApplicationContext getWebApplicationContext() {
		return webApplicationContext;
	}
	
	public static Object getBean(String strBeanId) {
		return webApplicationContext.getBean(strBeanId);
	}
}