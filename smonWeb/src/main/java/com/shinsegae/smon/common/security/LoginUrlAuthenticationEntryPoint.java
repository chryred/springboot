package com.shinsegae.smon.common.security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.PortMapper;
import org.springframework.security.web.PortMapperImpl;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.shinsegae.smon.util.NLogger;

public class LoginUrlAuthenticationEntryPoint implements AuthenticationEntryPoint, InitializingBean {

	private String loginFormUrl;
	private boolean forceHttps = false;
	private boolean useForward = false;
	private PortMapper portMapper = new PortMapperImpl();
	private PortResolver portResolver = new PortResolverImpl();
	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	String domainName; // https 대상 도매인 변경

	@Deprecated
	public LoginUrlAuthenticationEntryPoint() {
	}

	@Deprecated
	public LoginUrlAuthenticationEntryPoint(String loginFormUrl) {
		this.loginFormUrl = loginFormUrl;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isTrue(StringUtils.hasText(loginFormUrl) && UrlUtils.isValidRedirectUrl(loginFormUrl),
				"loginFormUrl must be specified and must be a valid redirect URL");
		if (useForward && UrlUtils.isAbsoluteUrl(loginFormUrl)) {
			throw new IllegalArgumentException("useForward must be false if using an absolute loginFormURL");
		}
		Assert.notNull(portMapper, "portMapper must be specified");
		Assert.notNull(portResolver, "portResolver must be specified");

	}

	protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
		return getLoginFormUrl();
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		// https 포워딩
		String redirectUrl = null;

		if (useForward) {
			if (forceHttps && "http".equals(request.getScheme())) {
				redirectUrl = buildHttpsRedirectUrlForRequest(request); // http header 값을 조작 한다 .
			}

			if (redirectUrl == null) {
				String loginForm = determineUrlToUseForThisRequest(request, response, authException);

				RequestDispatcher dispatcher = request.getRequestDispatcher(loginForm);
				dispatcher.forward(request, response);
				return;
			}
		} else {
			redirectUrl = buildRedirectUrlToLoginPage(request, response, authException);
			
			// 인증오류 발생시 처리
			if (authException != null) {
				redirectUrl += "?type=error";
			}
		}

		redirectStrategy.sendRedirect(request, response, redirectUrl);
	}

	protected String buildRedirectUrlToLoginPage(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
		String loginForm = determineUrlToUseForThisRequest(request, response, authException);

		if (UrlUtils.isAbsoluteUrl(loginForm)) {
			return loginForm;
		}

		int serverPort = portResolver.getServerPort(request);
		String scheme = request.getScheme();

		RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();

		urlBuilder.setScheme(scheme);
		urlBuilder.setServerName(request.getServerName());
		urlBuilder.setPort(serverPort);
		urlBuilder.setContextPath(request.getContextPath());
		urlBuilder.setPathInfo(loginForm);

		// 도메인 대한 호출일 경우 https 로 변경한다.
//		String sendUrl = request.getRequestURL().toString();
		forceHttps = true;

		/*****************************************
		 * if(sendUrl.indexOf(domainName) != -1){ forceHttps = true ; }
		 * if(sendUrl.indexOf(domainName) != -1){ forceHttps = true ; }
		 * String reuqestUrl = request.getRequestURI()
		 * if( domainName.equals(request.getServerName()) ){ forceHttps = true ; }
		 * if (forceHttps || "http".equals(scheme)) { Integer httpsPort =
		 * portMapper.lookupHttpsPort(Integer.valueOf(serverPort));
		 * if (httpsPort != null) { // Overwrite scheme and port in the redirect URL
		 * urlBuilder.setScheme("http"); urlBuilder.setPort(httpsPort.intValue()); } }
		 ********************************************/
		NLogger.info("Send getUrl ", urlBuilder.getUrl());
		return urlBuilder.getUrl();
	}

	protected String buildHttpsRedirectUrlForRequest(HttpServletRequest request) throws IOException, ServletException {
		int serverPort = portResolver.getServerPort(request);
		Integer httpsPort = portMapper.lookupHttpsPort(Integer.valueOf(serverPort));

		if (httpsPort != null) {
			RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();
			urlBuilder.setScheme("https");
			urlBuilder.setServerName(request.getServerName());
			urlBuilder.setPort(httpsPort.intValue());
			urlBuilder.setContextPath(request.getContextPath());
			urlBuilder.setServletPath(request.getServletPath());
			urlBuilder.setPathInfo(request.getPathInfo());
			urlBuilder.setQuery(request.getQueryString());

			return urlBuilder.getUrl();
		}

		// Fall through to server-side forward with warning message
		// LOGGER.warn("https 포트 :: " + serverPort);

		return null;
	}

	@Deprecated
	public void setLoginFormUrl(String loginFormUrl) {
		this.loginFormUrl = loginFormUrl;
	}

	public String getLoginFormUrl() {
		return loginFormUrl;
	}

	public void setPortMapper(PortMapper portMapper) {
		this.portMapper = portMapper;
	}

	protected PortMapper getPortMapper() {
		return portMapper;
	}

	public void setPortResolver(PortResolver portResolver) {
		this.portResolver = portResolver;
	}

	protected PortResolver getPortResolver() {
		return portResolver;
	}

	/**
	 * Tells if we are to do a forward to the {@code loginFormUrl} using the
	 * {@code RequestDispatcher}, instead of a 302 redirect.
	 * 
	 * @param useForward
	 *            true if a forward to the login page should be used. Must be false
	 *            (the default) if {@code loginFormUrl} is set to an absolute value.
	 */
	public void setUseForward(boolean useForward) {
		this.useForward = useForward;
	}

	protected boolean isUseForward() {
		return useForward;
	}

}
