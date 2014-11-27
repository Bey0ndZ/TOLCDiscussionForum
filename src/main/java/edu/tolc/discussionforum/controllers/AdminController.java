package edu.tolc.discussionforum.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.tolc.discussionforum.model.UserInformation;
import edu.tolc.discussionforum.service.UsersService;

@Controller
public class AdminController {
	@Autowired
	UsersService userService;

	@RequestMapping(value = "/welcomeAdmin", method = RequestMethod.GET)
	public String welcomeAdminGET() {
		return "welcomeAdmin";
	}

	@RequestMapping(value = "/addNewInstructor", method = RequestMethod.GET)
	public String addNewInstructorGET() {
		return "addNewInstructor";
	}

	@RequestMapping(value = "/addNewInstructor", method = RequestMethod.POST)
	public String addNewInstructorPOST(
			@ModelAttribute("registrationInformation") UserInformation userInfo,
			Model model) {
		String successMsg = userService.instructorRegistration(userInfo);
		model.addAttribute("successMsg", successMsg);
		return "addNewInstructor";
	}

	@RequestMapping(value = "/viewInstructor", method = RequestMethod.GET)
	public String viewInstructorGET() {
		return "viewInstructor";
	}

	@RequestMapping(value = "/deleteInstructor", method = RequestMethod.GET)
	public String deleteInstructorGET() {
		return "deleteInstructor";
	}
}
