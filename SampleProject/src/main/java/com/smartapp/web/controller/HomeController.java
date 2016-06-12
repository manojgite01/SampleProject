package com.smartapp.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smartapp.web.domain.Employee;
import com.smartapp.web.domain.User;
import com.smartapp.web.domain.form.EmployeeForm;
import com.smartapp.web.domain.form.UserDetailsForm;
import com.smartapp.web.domain.form.UserForm;
import com.smartapp.web.service.HomeService;
import com.smartapp.web.service.UserHelper;

@Controller
public class HomeController {
	
	@Autowired
	@Qualifier("homeService")
	HomeService homeService;
	
	@Autowired
	@Qualifier("userHelper")
	UserHelper userHelper;

	@RequestMapping(value={"/search.htm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String getSearchPage(HttpServletRequest request, HttpServletResponse response, Model model,@ModelAttribute("employeeForm") EmployeeForm form){
				
		User user = (User)request.getSession().getAttribute("user");
			if(user != null && userHelper.isAuthorizedAdmin(user)){
			form = (EmployeeForm)form;
			if(form.getId() != null){
				Employee employee = homeService.getEmployee(form);
				if(employee != null){
					BeanUtils.copyProperties(employee, form);
					model.addAttribute("empName", employee.getName());
				}
				else
					model.addAttribute("failureMsg", "Employee Not Found!");
			}
			model.addAttribute("message", "Welcome to Home Page!");
			model.addAttribute("employeeForm", form);
			return "search";
		}else {
			return "unauthorized";
		}
		
	}
	
	@RequestMapping(value={"/signup.htm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String getSignupPage(HttpServletRequest request, HttpServletResponse response, Model model,@ModelAttribute("userDetailsForm") UserDetailsForm form){
		String userAction = request.getParameter("userAction");
		boolean createUserFlag = false;
		if(StringUtils.isNotEmpty(userAction) && userAction.equalsIgnoreCase("createUser")){
			createUserFlag = homeService.validateAndCreateUser(form);
		}
		if(createUserFlag){
			model.addAttribute("message", "User created successfully");
			return "logout";
		} else{
			if(form.getErrorMsg() != null)
				model.addAttribute("error", form.getErrorMsg());
			form = (UserDetailsForm)form;
			form.setPassword(null);
			model.addAttribute("userDetailsForm", form);
			return "signup";
		}
	}
	
	@RequestMapping(value={"/login.htm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String getLoginPage(HttpServletRequest request, HttpServletResponse response, Model model,@ModelAttribute("userForm") UserForm form){
		String userAction = request.getParameter("userAction");
		boolean validateUserFlag = false;
		if(StringUtils.isNotEmpty(userAction) && userAction.equalsIgnoreCase("autheticate")){
			validateUserFlag = homeService.validateUser(form);
		}
		if(validateUserFlag){
			request.getSession().setAttribute("userId", form.getUsername().trim());
			return "redirect:search.htm";
		}else{
			form.setPassword(null);
			model.addAttribute("userForm", form);
			if(form.getErrorMsg() != null)
				model.addAttribute("error", form.getErrorMsg());
			return "login";
		}
	}
	
	@RequestMapping(value={"/logout.htm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String getLogoutPage(HttpServletRequest request, HttpServletResponse response, Model model){
		request.getSession().invalidate();
		model.addAttribute("message", "Logged out successfully!");
		return "logout";
	}
}
