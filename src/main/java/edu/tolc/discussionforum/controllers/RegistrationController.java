package edu.tolc.discussionforum.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.tolc.discussionforum.model.UserInformation;
import edu.tolc.discussionforum.service.UsersService;

@Controller
public class RegistrationController {
	@Autowired
	UsersService userService;
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String registerGETRequest() {
		return "register";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerPOSTRequest(@ModelAttribute("registrationInformation") UserInformation userInfo) {
		String successMsg = userService.userRegistration(userInfo);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("successMsg", successMsg);
		return "register";
	}
}
