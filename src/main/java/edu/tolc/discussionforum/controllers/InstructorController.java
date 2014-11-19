package edu.tolc.discussionforum.controllers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.tolc.discussionforum.dto.CourseEnrollmentDTO;
import edu.tolc.discussionforum.dto.GetCoursesDTO;
import edu.tolc.discussionforum.dto.UserInformationDTO;
import edu.tolc.discussionforum.service.UsersService;

@Controller
public class InstructorController {
	@Autowired
	UsersService userService;
	int globalCourseID = 0;
	
	// TODO: Get all the list of courses as soon as the instructor 
	// logs in the system
	@RequestMapping(value="/welcomeInstructor", method=RequestMethod.GET)
	public ModelAndView welcomeInstructorGET() {
		ModelAndView modelAndView = new ModelAndView();
		List<UserInformationDTO> getInstructorInformation = new ArrayList<UserInformationDTO>();
		
		// Get the instructor name via session attribute
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String loggedInPerson = userDetail.getUsername();
			
			getInstructorInformation = userService.getUserInformation(loggedInPerson);
			modelAndView.addObject("getUserInfo", getInstructorInformation);
			
		} else {
			// permission-denied page
			// must be logged in
		}
		
		modelAndView.setViewName("welcomeInstructor");
		return modelAndView;
	}
	
	@RequestMapping(value="/addCourse", method=RequestMethod.GET)
	public String addCourseGET() {
		return "addCourse";
	}
	
	// Adding a new course by the instructor
	@RequestMapping(value="/addCourse", method=RequestMethod.POST)
	public ModelAndView addCoursePOST(@RequestParam("courseName") String courseName,
			@RequestParam("courseDescription") String courseDescription) {
		ModelAndView modelAndView = new ModelAndView();
		
		// Get the instructor name via session attribute
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String instructorsName = userDetail.getUsername();
			
		// Call the service layer method
		String successMsg = userService.addCourse(courseName, courseDescription,
				instructorsName);
		modelAndView.addObject("addCourseConfirmation", successMsg);
		} else {
				// permission-denied page because
				// user is not logged in
		}
		
		modelAndView.setViewName("addCourse");
		return modelAndView;
	}
	
	// Get courses for instructor
	@RequestMapping(value="/getMyCourses", method=RequestMethod.GET)
	public ModelAndView getMyCourses() {
		ModelAndView modelAndView = new ModelAndView();
		
		// Get the instructor name via session attribute
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String instructorsName = userDetail.getUsername();
			
			// Get list of courses
			List<GetCoursesDTO> allCourses = new ArrayList<GetCoursesDTO>();
			allCourses = userService.getCourseList(instructorsName);
			
			// Add the object to model
			modelAndView.addObject("courseInformationForInstructor", allCourses);
			
		} else {
			// permission-denied page
			// user is not logged in
		}
		modelAndView.setViewName("getMyCourses");
		return modelAndView;
	}
	
	// Get the discussion board page based on the GET parameter
	@RequestMapping(value="/getMyCourses/discussionBoard/{courseid}", method=RequestMethod.GET)
	public ModelAndView discussionBoardGET(@PathVariable int courseid) {
		ModelAndView modelAndView = new ModelAndView();
		globalCourseID = courseid;
		modelAndView.setViewName("discussionBoardForInstructor");
		return modelAndView;	
	}
	
	// Display the course calendar
	@RequestMapping(value="/getMyCourses/discussionBoard/courseCalendar", method=RequestMethod.GET)
	public ModelAndView courseCalendarGET() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("courseCalendar");
		return modelAndView;
	}
	
	// Process the events
	@RequestMapping(value="/getMyCourses/discussionBoard/courseCalendar", method=RequestMethod.POST)
	public ModelAndView courseCalendarPOST(@RequestParam("dateandtime") String dateandtime,
			@RequestParam("eventDetails") String eventDetails,
			@RequestParam("eventType") String eventType) throws ParseException {
		ModelAndView modelAndView = new ModelAndView();
		boolean personalEvent = false;
		
		// Changing the string timestamp to a timestamp variable
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		Date date = simpleDateFormat.parse(dateandtime);
		Timestamp eventTimestamp = new Timestamp(date.getTime());
		
		// Check whether personal event of course event
		if (eventType.equalsIgnoreCase("personalEvent")) {
			personalEvent = true;
		} else {
			personalEvent = false;
		}
		
		// Get the logged in person
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String loggedInPersonsName = userDetail.getUsername();
			
			String eventCreationMsg = userService.createCalendarEvent(globalCourseID, eventDetails,
					loggedInPersonsName, personalEvent, eventTimestamp);
			modelAndView.addObject("eventCreationMsg", eventCreationMsg);
		} else {
			// permission-denied page
		}
		
		modelAndView.setViewName("courseCalendar");
		return modelAndView;
	}
	
	// Get the enrolled students list
	// This method could be reused for the follow concept
	// for an individual student
	@RequestMapping(value="/getMyCourses/discussionBoard/getEnrolledStudentsList", method=RequestMethod.GET)
	public ModelAndView getEnrolledStudentsListGET() {
		ModelAndView modelAndView = new ModelAndView();
		
		// Get the students who are enrolled in course
		List<UserInformationDTO> getEnrolledUsersInfo = new ArrayList<UserInformationDTO>();
		getEnrolledUsersInfo = userService.getEnrolledStudents(globalCourseID);
		
		modelAndView.addObject("enrolledStudents", getEnrolledUsersInfo);
		
		modelAndView.setViewName("enrolledStudentsForInstructor");
		return modelAndView;
	}
	
	// View all enrolled students in all courses taken by the professor
	@RequestMapping(value="/viewAllEnrolledStudents", method=RequestMethod.GET)
	public ModelAndView returnAllEnrolledStudentsGET() {
		ModelAndView modelAndView = new ModelAndView();
		List<CourseEnrollmentDTO> enrolledStudents = new ArrayList<CourseEnrollmentDTO>();
		
		// Get the logged in person
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String instructorsName = userDetail.getUsername();
			
			enrolledStudents = userService.getAllEnrolledStudents(instructorsName);
			modelAndView.addObject("enrolledStudents", enrolledStudents);
			
		} else {
			// permission-denied
			// must be logged in
		}
		
		modelAndView.setViewName("getAllEnrolledStudents");
		return modelAndView;
	}
	
	// Delete course 
	// GET request
	@RequestMapping(value="/deleteCourse", method=RequestMethod.GET)
	public ModelAndView deleteCourseGET() {
		ModelAndView modelAndView = new ModelAndView();
		
		// Get the logged in person
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String instructorsName = userDetail.getUsername();
		} else {
			// permission-denied
			// user must be logged in
		}
		modelAndView.setViewName("deleteCourse");
		return modelAndView;
	}
}
