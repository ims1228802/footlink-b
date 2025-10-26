package com.footlink.footlink.user.login.interceptor;

import java.util.Set;
import java.util.StringJoiner;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommonInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Set<String> paramKeys = request.getParameterMap().keySet();
		
		StringJoiner param = new StringJoiner(", "); 
		
		for(String paramkey : paramKeys) {
			param.add(paramkey + ": " + request.getParameter(paramkey));
		}
		
		log.info("ACCESS INFO ============================");
		log.info("PORT 		:::::::: {}", request.getLocalPort());
		log.info("SERVERNAME 	:::::::: {}", request.getServerName());
		log.info("HTTP METHOD 	:::::::: {}", request.getMethod());
		log.info("URI 		:::::::: {}", request.getRequestURI());
		log.info("CLIENT IP 	:::::::: {}", request.getRemoteAddr());
		log.info("PARAMETER 	:::::::: {}", param);
		log.info("=========================================");
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
}
