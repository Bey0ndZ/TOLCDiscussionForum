package edu.tolc.discussionforum.controllers;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.tolc.discussionforum.service.UsersService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	UsersService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = {"/home", "/", "/index"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "home";
	}	
}
