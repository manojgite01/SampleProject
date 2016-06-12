package com.smartapp.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.smartapp.web.domain.User;
import com.smartapp.web.service.UserHelper;


/** This intercepter will intercept all request and response for a controller. */
@Component(value = "httpSessionInterceptor")
public class HttpSessionInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	@Qualifier("userHelper")
	UserHelper userHelper;

	/** logger object used to log the messages for this class */
	private final static Logger logger = LoggerFactory.getLogger(HttpSessionInterceptor.class);

	/**
	 * This method will be called each time before any controller method is
	 * called.This method checks if user is present in session,if not then it
	 * calls the UnifiedUserService to get the user object and puts in session.
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		// check if data is present in session
		HttpSession session = request.getSession();
		String userId = null;
		if(session.getAttribute("userId") != null){
			userId = (String)session.getAttribute("userId");
		}
		String sessionId = session.getId();
		MDC.put("userId", userId);
		MDC.put("sessionId", sessionId);

		if (session.getAttribute("userId") == null) {
			request.setAttribute("FirstRequest", "true");
			User user = new User();
			try {
				if(userId != null)
					user = userHelper.findByUserName(userId);
			} catch (Exception ex) {
				logger.warn("Got exception while fetching user details for userId"+ userId, ex);
				request.setAttribute("flashError","Got exception while fetching user details for userId"+ userId);
			}
			session.setAttribute("user", user);
		}
		System.out.println("Prehandle********** [Inside Interceptor] *************");
		return true;
	}

	/**
	 * This method will be called each time after the execution of any method in
	 * a controller.
	 */
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("Posthandle********** [Inside Interceptor] *************");
		String userId = (String) request.getSession().getAttribute("userId");
		User user = userHelper.findByUserName(userId);
		request.getSession().setAttribute("user", user);
		if (modelAndView != null) {
			modelAndView.addObject("user", user);
		}
	}
}