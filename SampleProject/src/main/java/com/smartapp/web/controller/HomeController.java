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

import com.smartapp.web.domain.Employee;
import com.smartapp.web.domain.form.EmployeeForm;
import com.smartapp.web.service.HomeService;

@Controller
public class HomeController {
	
	@Autowired
	@Qualifier("homeService")
	HomeService homeService; 

	@RequestMapping(value="/home.htm", method = {RequestMethod.GET, RequestMethod.POST})
	public String getHomePage(HttpServletRequest request, HttpServletResponse response, Model model,@ModelAttribute("employeeForm") EmployeeForm form){
		model.addAttribute("message", "Welcome to Home Page!");
		form = (EmployeeForm)form;
		Employee employee = homeService.getEmployee(form);
		if(employee != null)
			model.addAttribute("empName", employee.getName());
		else
			model.addAttribute("failureMsg", "Sorry Employee Not Found!");
		model.addAttribute("employeeForm", form);
		return "home";
	}
	
}
