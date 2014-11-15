package edu.tolc.discussionforum.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.tolc.discussionforum.dto.GetCoursesDTO;
import edu.tolc.discussionforum.service.UsersService;

@Controller
public class StudentController {
	@Autowired
	UsersService userService;
	String globalInstructorsName = "";
	
	// TODO: Need to have a default list of all the courses
	// the student is present in
	@RequestMapping(value="/welcome", method=RequestMethod.GET)
	public ModelAndView welcomeGET() {
		ModelAndView modelAndView = new ModelAndView();
		// Get course list for which the student is present in
		// Reason: I would want to see MY courses
		// not all the courses present
		
		// Get the studentName who has logged in
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String studentName = userDetail.getUsername();
			
			List<GetCoursesDTO> getMyCourses = new ArrayList<GetCoursesDTO>();
			getMyCourses = userService.getStudentCourses(studentName);
			
			modelAndView.addObject("getMyCourses", getMyCourses);
			
			
		} else {
			// permission-denied page
			// Please log in
		}
		
		modelAndView.setViewName("welcome");
		return modelAndView;
	}
	
	@RequestMapping(value="/viewAllCourses", method=RequestMethod.GET)
	public ModelAndView viewAllCoursesGET() {
		ModelAndView modelAndView = new ModelAndView();
		// Get all the courses list
		List<GetCoursesDTO> courseInformation = new ArrayList<GetCoursesDTO>();
		
		// Get all the course information
		courseInformation = userService.getCourseList();
		
		// Populate our view
		modelAndView.addObject("courseInformation", courseInformation);
		modelAndView.setViewName("viewAllCourses");
		
		return modelAndView;
	}
	
	// Enroll student in course
	@RequestMapping(value="/enrollInCourse", method=RequestMethod.POST)
	public ModelAndView enrollStudentInCourse(@RequestParam("enrollInCourseID") String courseID) {
		ModelAndView modelAndView = new ModelAndView();
		if (courseID != null) {
			// Call the student enrollment method
			// Params: course id, student name
			
			// Get the student name
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetail = (UserDetails) auth.getPrincipal();
				String studentName = userDetail.getUsername();
				
				String courseEnrollmentMsg = userService.enrollStudentInCourse(courseID, studentName);
				modelAndView.addObject("courseEnrollmentMsg", courseEnrollmentMsg);
			} else {
				// permission denied
				// log in
			}
			
		} else {
			modelAndView.addObject("courseEnrollmentMsg", "Please enter a value in the textbox");
		}
		modelAndView.setViewName("viewAllCourses");
		return modelAndView;
	}
}
