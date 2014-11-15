package edu.tolc.discussionforum.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	@RequestMapping(value="/welcome/getCoursesForStudent/{instructorsname}", method=RequestMethod.GET)
	public ModelAndView getInstructorCourses(@PathVariable String instructorsname) {
		ModelAndView modelAndView = new ModelAndView();
		List<GetCoursesDTO> getCoursesList = new ArrayList<GetCoursesDTO>();
		getCoursesList = userService.getCourseList(instructorsname);
		modelAndView.addObject("courseInformation", getCoursesList);
		modelAndView.setViewName("getCoursesForStudent");
		return modelAndView;
	}
	
	// Enrollment of student in all courses
	// TODO: Redirect properly
	@RequestMapping(value="/welcome/getCoursesForStudent/{instructorsname}/enrollInAllCourses", method=RequestMethod.GET)
	public ModelAndView enrollInAllCourses(@PathVariable String instructorsName) {
		ModelAndView modelAndView = new ModelAndView();
		// Get the instructor name via session attribute
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String studentName = userDetail.getUsername();
		// Enroll in all courses obtained by URL
		String enrollmentSuccess = userService.enrollStudentInAllCourses(studentName, instructorsName);
		modelAndView.addObject("enrollmentSuccess", enrollmentSuccess);
		modelAndView.setViewName("getCoursesForStudent");
		
		} else {
			// permission-denied page
		}
		return modelAndView;
	}
	
}
