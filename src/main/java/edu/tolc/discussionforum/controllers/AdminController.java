package edu.tolc.discussionforum.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	public String welcomeAdminGET(Model model) {

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
	public String viewInstructorGET(Model model) {
		List<String> instructorUserName = userService
				.getInstructorsUserNameList();
		model.addAttribute("instructorUserName", instructorUserName);
		List<String> instructor = userService.getInstructorsList();
		model.addAttribute("instructor", instructor);
		Map<String, String> map = new LinkedHashMap<String, String>();

		for (int i = 0; i < instructorUserName.size(); i++) {
			map.put(instructorUserName.get(i), instructor.get(i));
		}
		model.addAttribute("map", map);
		return "viewInstructor";
	}

	@RequestMapping(value = "/deleteInstructor", method = RequestMethod.GET)
	public String deleteInstructorGET(Model model) {
		List<String> instructorUserName = userService
				.getInstructorsUserNameList();
		model.addAttribute("instructorUserName", instructorUserName);
		List<String> instructor = userService.getInstructorsList();
		model.addAttribute("instructor", instructor);
		Map<String, String> map = new LinkedHashMap<String, String>();

		for (int i = 0; i < instructorUserName.size(); i++) {
			map.put(instructorUserName.get(i), instructor.get(i));
		}
		model.addAttribute("map", map);
		return "deleteInstructor";
	}

	@RequestMapping(value = "/deleteInstructor", method = RequestMethod.POST)
	public String deleteInstructorPOST(
			@ModelAttribute("deleteInformation") UserInformation userInfo,
			Model model) {
		List<String> instructor = userService.deleteInstructor(userInfo
				.getUsername());
		model.addAttribute("instructor", instructor);
		List<String> instructorUserName = userService
				.getInstructorsUserNameList();
		Map<String, String> map = new LinkedHashMap<String, String>();

		for (int i = 0; i < instructorUserName.size(); i++) {
			map.put(instructorUserName.get(i), instructor.get(i));
		}
		model.addAttribute("map", map);
		return "deleteInstructor";
	}
}
