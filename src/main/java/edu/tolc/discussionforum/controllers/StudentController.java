package edu.tolc.discussionforum.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.tolc.discussionforum.dto.GetCoursesDTO;
import edu.tolc.discussionforum.service.UsersService;

@Controller
public class StudentController {
	@Autowired
	UsersService userService;
	
	// TODO: Need to have a default list of all the courses
	// the student is present in
	@RequestMapping(value="/welcome", method=RequestMethod.GET)
	public ModelAndView welcomeGET() {
		ModelAndView modelAndView = new ModelAndView();
		// Get all the courses
		// We are reusing the method written for the instructor
		// So, get a list of instructors
		List<String> instructorsList = new ArrayList<String>();
		instructorsList = userService.getInstructorsList();
		
		modelAndView.addObject("instructorsList", instructorsList);
		modelAndView.setViewName("welcome");
		return modelAndView;
	}
	
	// Get the instructors courses to display to the student
	// Call the same method
	@RequestMapping(value="/getCourses/{instructorsname}", method=RequestMethod.GET)
	public ModelAndView getInstructorCourses(@PathVariable String instructorsName) {
		ModelAndView modelAndView = new ModelAndView();
		System.out.println(instructorsName);
		List<GetCoursesDTO> getCoursesList = new ArrayList<GetCoursesDTO>();
		getCoursesList = userService.getCourseList(instructorsName);
		modelAndView.addObject("courseListInformation", getCoursesList);
		modelAndView.setViewName("getCoursesForStudent");
		return modelAndView;
	}
}
