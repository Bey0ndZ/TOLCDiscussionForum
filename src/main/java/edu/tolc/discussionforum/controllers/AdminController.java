package edu.tolc.discussionforum.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.tolc.discussionforum.service.UsersService;

@Controller
public class AdminController {
	@Autowired
	UsersService userService;
	
	@RequestMapping(value="/welcomeAdmin", method=RequestMethod.GET)
	public String welcomeAdminGET() {
		return "welcomeAdmin";
	}
}
