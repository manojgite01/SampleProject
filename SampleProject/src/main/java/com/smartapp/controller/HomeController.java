package com.smartapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping(value="/home.htm", method = {RequestMethod.GET, RequestMethod.POST})
	public String getHomePage(HttpServletRequest request, HttpServletResponse response, Model model){
		model.addAttribute("message", "Welcome to Home Page!");
		return "home";
	}
	
}
