package edu.tolc.discussionforum.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.tolc.discussionforum.service.UsersService;

@Controller
public class StudentController {
	@Autowired
	UsersService userService;
	
	// TODO: Need to have a default list of all the courses
	// the student is present in
	@RequestMapping(value="/welcome", method=RequestMethod.GET)
	public String welcomeGET() {
		// Get the student courses
		return "welcome";
	}
}
