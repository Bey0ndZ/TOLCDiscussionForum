package edu.tolc.discussionforum.controllers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.tolc.discussionforum.dto.CourseEnrollmentDTO;
import edu.tolc.discussionforum.dto.GetCoursesDTO;
import edu.tolc.discussionforum.dto.GetPostsDTO;
import edu.tolc.discussionforum.dto.GetThreadInfoDTO;
import edu.tolc.discussionforum.dto.GetTickrDTO;
import edu.tolc.discussionforum.dto.UserInformationDTO;
import edu.tolc.discussionforum.service.UsersService;

@Controller
public class InstructorController {
	@Autowired
	UsersService userService;
	int globalCourseID = 0;
	int globalThreadID = 0;
	
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
		// Setting the global variable
		globalCourseID = courseid;
		// Get the information from discussionboard table
		// using a DTO
		List<GetThreadInfoDTO> getThreadInformation = new ArrayList<GetThreadInfoDTO>();
		getThreadInformation = userService.getThreadInformation(courseid);
		
		// Check if the user has selected anonymous
		for (GetThreadInfoDTO threadInfo : getThreadInformation) {
			if (threadInfo.isPostanonymously()) {
				threadInfo.setCreatedby("Anonymous");
			} 
		}
		
		// Adding it to the view
		modelAndView.addObject("getThreadInformation", getThreadInformation);
		
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
		List<GetCoursesDTO> getAllCourses = new ArrayList<GetCoursesDTO>();
		
		// Get the instructors name
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String instructorsName = userDetail.getUsername();
			
			// Reuse method which has been already created
			getAllCourses = userService.getCourseList(instructorsName);
			modelAndView.addObject("getAllCourses", getAllCourses);			
		} else {
			// permission-denied
			// user must be logged in
		}
		modelAndView.setViewName("deleteCourse");
		return modelAndView;
	}
	
	// Delete course
	// POST method
	@RequestMapping(value="/deleteCourse", method=RequestMethod.POST)
	public ModelAndView deleteCoursePOST(@RequestParam("courseToDelete") String courseid) {
		ModelAndView modelAndView = new ModelAndView();
		
		// Delete the course from all tables
		String courseDeletionMsg = userService.deleteCourse(courseid);
		modelAndView.addObject("courseDeletionMsg", courseDeletionMsg);
		
		modelAndView.setViewName("deleteCourse");
		return modelAndView;
	}
	
	// Rate Students
	// Get Request
	@RequestMapping(value="/getMyCourses/rateStudents", method=RequestMethod.GET)
	public ModelAndView rateStudentsGET() {
		ModelAndView modelAndView = new ModelAndView();
		// Get the students who are enrolled in course
		List<UserInformationDTO> getEnrolledUsersInfo = new ArrayList<UserInformationDTO>();
		getEnrolledUsersInfo = userService.getEnrolledStudents(globalCourseID);
		
		modelAndView.addObject("getAllEnrolledStudents", getEnrolledUsersInfo);
		modelAndView.setViewName("rateStudents");
		return modelAndView;
	}
	
	// Rate Students
	// POST Request
	@RequestMapping(value="/getMyCourses/rateStudents", method=RequestMethod.POST)
	public ModelAndView rateStudentsPOST(@RequestParam("rateStudentList") String userRated,
			@RequestParam("starRating") String ratingGiven) {
		ModelAndView modelAndView = new ModelAndView();
		List<UserInformationDTO> getEnrolledUsersInfo = new ArrayList<UserInformationDTO>();
		getEnrolledUsersInfo = userService.getEnrolledStudents(globalCourseID);
		
		modelAndView.addObject("getAllEnrolledStudents", getEnrolledUsersInfo);
		modelAndView.addObject("ratingGiven","Rating has been posted. Email has been send to everyone in class.");
		
		// No need to store the values in the table
		// Just email everyone when the rating is done
		// Return type void - nothing worth returning
		if (ratingGiven.equalsIgnoreCase("5")) {
			userService.sendRatingEmail(globalCourseID, userRated);
		} else {
			// Do not send any email
		}
	
		modelAndView.setViewName("rateStudents");
		return modelAndView;
	}
	
	// Show thread
	// GET Request
	// GET the showThread page
	@RequestMapping(value="getMyCourses/discussionBoard/showThread/{threadid}", method=RequestMethod.GET)
	public ModelAndView getThread(@PathVariable int threadid) {
		ModelAndView modelAndView = new ModelAndView();
		// Get the threadname, threadsubject and threadcontent
		List<GetThreadInfoDTO> getThreadInformation = new ArrayList<GetThreadInfoDTO>();
		getThreadInformation = userService.getThreadInfoByThreadID(threadid);
		
		// Store the threadid in the global variable to be used during insertion
		globalThreadID = threadid;
		
		for (GetThreadInfoDTO threadInfo : getThreadInformation) {
			modelAndView.addObject("threadid", threadInfo.getThreadid());
			modelAndView.addObject("threadname", threadInfo.getThreadname());
			modelAndView.addObject("threadsubject", threadInfo.getThreadsubject());
			modelAndView.addObject("threadcontent", threadInfo.getThreadcontent());
			modelAndView.addObject("createdby", threadInfo.getCreatedby());
		}
		
		// For the tickr feature
		List<GetTickrDTO> tickr = new ArrayList<GetTickrDTO>();
		tickr = userService.getDetailsForTickr(threadid);
		
		// If the person checks anonymous functionality
		// do not display his name
		for (GetTickrDTO tickrInformation : tickr) {
			if (tickrInformation.isPostanonymously()) {
				tickrInformation.setPostedby("Anonymous");
			} else {
				// Do nothing
			}
		}
		modelAndView.addObject("tickr", tickr);
		// Adding the global variable courseid so that
		// we can redirect at the showThread page
		modelAndView.addObject("globalCourseIDSet", globalCourseID);
		
		// Populate the remaining discussions going on
		// When adding the object to the model, add the html tags with them
		// Get discussion posts
		List<GetPostsDTO> getAllPosts = new ArrayList<GetPostsDTO>();
		getAllPosts = userService.getPosts(threadid);
		for (GetPostsDTO post : getAllPosts) {
			if (post.isPostanonymously()) {
				post.setPostedby("Anonymous");
			}
		}
		
		// Check if the user is subscribed to the current thread
		// if he or she haven't subscribed, only then display the form
		// Get the username of the student logged in
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String studentName = userDetail.getUsername();
			
			if (userService.hasSubscribed(threadid, studentName)) {
				modelAndView.addObject("subscriptionMsg", "You have been subscribed to this thread.");
			} else {
				modelAndView.addObject("displayForm", "Subscription form");
			}
			
		} else {
			// Not logged in
		}		
		
		// Get the value for firepadURL
		int firepadURL = userService.getFirepadURLValue(threadid);
		modelAndView.addObject("firepadURL", firepadURL);
		
		modelAndView.addObject("getAllPosts", getAllPosts);
		modelAndView.setViewName("showThread");
		return modelAndView;
	}
}
