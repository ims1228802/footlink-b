package com.footlink.footlink.user.login.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		
		String sessionId = (String) session.getAttribute("SID");
		
		boolean isProcess = true;
		
		if(sessionId == null) {
			response.sendRedirect("/admin/login");
			isProcess = false;
		}else {
			String userGrade = (String) session.getAttribute("SGRD");
			
			String requestUri = request.getRequestURI();
			if("cd_02".equals(userGrade)) {				
				if(requestUri.indexOf("/admin") > -1) {
					response.sendRedirect("/main");
					isProcess = false;
				}
			}
		}
		
		return isProcess;
	}
}
