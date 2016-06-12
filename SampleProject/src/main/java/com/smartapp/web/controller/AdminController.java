package com.smartapp.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smartapp.web.domain.User;
import com.smartapp.web.domain.form.UserDetailsForm;
import com.smartapp.web.service.AdminService;
import com.smartapp.web.service.CacheService;
import com.smartapp.web.service.UserHelper;

@Controller
public class AdminController {
	
	@Autowired
	@Qualifier("adminService")
	AdminService adminService;
	
	@Autowired
	@Qualifier("cacheService")
	CacheService cacheService;
	
	@Autowired
	@Qualifier("userHelper")
	UserHelper userHelper;

	@RequestMapping(value={"/manageUsers.htm"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String getSearchPage(HttpServletRequest request, HttpServletResponse response, Model model,@ModelAttribute("userDetailsForm") UserDetailsForm form){
				
		User user = (User)request.getSession().getAttribute("user");
		if(user != null && userHelper.isAuthorizedAdmin(user)){
			form = (UserDetailsForm)form;
			model.addAttribute("usersList", cacheService.getUsersList());
			model.addAttribute("userDetailsForm", form);
			model.addAttribute("message", "Welcome to Manage Users Screen!");
			return "manageUsers";
		}else {
			return "unauthorized";
		}
		
	}
	
}
