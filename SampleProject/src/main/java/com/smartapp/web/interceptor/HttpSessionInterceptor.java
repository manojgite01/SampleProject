package com.smartapp.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/** This intercepter will intercept all request and response for a controller. */
@Component(value = "httpSessionInterceptor")
public class HttpSessionInterceptor extends HandlerInterceptorAdapter {

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
		/*HttpSession session = request.getSession();
		String userId = ServletUtility.getUserID(request);
		String env = System.getProperty("env").toUpperCase();

		if (env.equalsIgnoreCase("Local")||env.equalsIgnoreCase("Devl"))
			userId = "da77241";

		// setting userid in MDC so that it available in all layers and can be used for
		// logging etc if there is no user object in session then assume that it
		// is new session, because of first request or because of session timeout
		String sessionId = session.getId();
		MDC.put("userId", userId);
		MDC.put("sessionId", sessionId);

		if (session.getAttribute("user") == null) {
			userId = ServletUtility.getUserID(request);

			if (env.equalsIgnoreCase("Local") && userId == null)
				userId = "da77241";

			request.setAttribute("FirstRequest", "true");
			// if there is error while calling uuc then catch exception and log it as warning
			VTUser user = new VTUser();
			try {
				user = userHelper.getUserDetails(userId);
			} catch (Exception ex) {
				logger.warn("Got exception while fetching user details for userId"+ userId, ex);
				request.setAttribute("flashError","Got exception while fetching user details for userId"+ userId);
			}
			session.setAttribute("user", user);
		}*/
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
		/*VTUser user = (VTUser) request.getSession().getAttribute("user");
		if (modelAndView != null) {
			modelAndView.addObject("user", user);
		}*/
	}
}