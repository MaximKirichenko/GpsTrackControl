package com.ksgagro.gps.interceptor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuditingInterceptor extends HandlerInterceptorAdapter{
	Logger logger = Logger.getLogger("auditLogger");
	private String user;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if(request.getRequestURI().endsWith("gps/") && response.getStatus()==200){
			user = SecurityContextHolder.getContext().getAuthentication().getName();
			logger.info(String.format("User %s login at %s", user, getCurrentTime()));
		}
		if(request.getRequestURI().endsWith("/logout")){
			logger.info(String.format("User %s logout at %s", user, getCurrentTime()));
		}
		if(request.getRequestURI().endsWith("/loginfailed")){
			logger.info(String.format("User %s failed authentitication at %s", user, getCurrentTime()));
		}
	}


	private String getCurrentTime(){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy 'at' hh:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return dateFormat.format(calendar.getTime());
	}
	
	

}
