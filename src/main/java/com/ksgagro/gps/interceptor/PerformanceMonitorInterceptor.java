package com.ksgagro.gps.interceptor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ksgagro.gps.domain.repository.UserActionRepository;

public class PerformanceMonitorInterceptor implements HandlerInterceptor {

	ThreadLocal<StopWatch> stopWatchLocal = new ThreadLocal<StopWatch>();
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private UserActionRepository userActionRepository;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		StopWatch stopWatch = new StopWatch(handler.toString());
		stopWatch.start(handler.toString());
		stopWatchLocal.set(stopWatch);
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println(new Date());
		//userActionRepository.setAction(user, getURLPath(request), new Date());
		System.out.println(user + " Accessing URL path: " + getURLPath(request));
		logger.info("Accessing URL path: " + getURLPath(request));
		logger.info("Request processing started on: " + getCurrentTime());
		return true;
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("Request processing ended on " + getCurrentTime());

	}

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		StopWatch stopWatch = stopWatchLocal.get();
		stopWatch.stop();

		logger.info("Total time taken for processing: " + stopWatch.getTotalTimeMillis() + " ms");
		stopWatchLocal.set(null);

		logger.info("========================================================================================");

	}

	private String getURLPath(HttpServletRequest request) {
		String currentPath = request.getRequestURI();
		String queryString = request.getQueryString();
		queryString = queryString == null ? "" : "?" + queryString;
		return currentPath + queryString;
	}

	private String getCurrentTime() {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy 'at' hh:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return formatter.format(calendar.getTime());
	}

}
