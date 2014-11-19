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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import edu.tolc.discussionforum.dto.GetCalendarEventsDTO;
import edu.tolc.discussionforum.dto.GetCoursesDTO;
import edu.tolc.discussionforum.dto.GetPostsDTO;
import edu.tolc.discussionforum.dto.GetThreadInfoDTO;
import edu.tolc.discussionforum.dto.GetTickrDTO;
import edu.tolc.discussionforum.dto.UserInformationDTO;
import edu.tolc.discussionforum.service.UsersService;

@Controller
public class StudentController {
	@Autowired
	UsersService userService;
	int getCourseID = 0;
	int getThreadID = 0;
	
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
	
	// Get the discussionBoard for a particular course
	// The courseid will be in the path variable
	@RequestMapping(value="welcome/discussionBoard/{courseid}", method=RequestMethod.GET)
	public ModelAndView getDiscussionBoardForCourse(@PathVariable int courseid) {
		ModelAndView modelAndView = new ModelAndView();
		// Get the information from discussionboard table
		// using a DTO
		List<GetThreadInfoDTO> getThreadInformation = new ArrayList<GetThreadInfoDTO>();
		// Setting the global variable
		getCourseID = courseid;
		
		getThreadInformation = userService.getThreadInformation(courseid);
		
		// Check if the user has selected anonymous
		for (GetThreadInfoDTO threadInfo : getThreadInformation) {
			if (threadInfo.isPostanonymously()) {
				threadInfo.setCreatedby("Anonymous");
			} 
		}
		// Adding it to the view
		modelAndView.addObject("getThreadInformation", getThreadInformation);
		modelAndView.setViewName("discussionBoard");
		return modelAndView;
	}
	
	// Creating a new thread
	// GET Request
	@RequestMapping(value="welcome/discussionBoard/createThread",
			method=RequestMethod.GET)
	public ModelAndView createThreadGET() {
		ModelAndView modelAndView = new ModelAndView();	
		modelAndView.addObject("getCourseID", getCourseID);
		modelAndView.setViewName("createThread");
		return modelAndView;
	}
	
	// Creating a new thread
	// POST Request
	@RequestMapping(value="welcome/discussionBoard/createThread", method=RequestMethod.POST)
	public ModelAndView createThreadPOST(@RequestParam("threadName") String threadName,
			@RequestParam("threadSubject") String threadSubject, @RequestParam("threadContent") String threadContent,
			@RequestParam("anonymousPost") String anonymousPost) {
		ModelAndView modelAndView = new ModelAndView();
		boolean isanonymous;
		int courseid = 0;
		courseid = getCourseID;
		if (threadName != null && threadSubject != null && threadContent != null) {
			
			// Get the username of the student logged in
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetail = (UserDetails) auth.getPrincipal();
				String studentName = userDetail.getUsername();
				
				if (anonymousPost.equalsIgnoreCase("yes")) {
					isanonymous = true;
				} else {
					isanonymous = false;
				}
				
				String threadCreationSuccessMsg = userService.createThread(courseid, 
						threadName, threadSubject, threadContent, studentName, isanonymous);
				
			} else {
				// permission-denied page
				// Must log in
			}
		} else {
			modelAndView.addObject("inputValidationMsg", "Please do not leave the fields empty.");
		}
		modelAndView.setViewName("redirect:/welcome/discussionBoard/"+getCourseID);
		return modelAndView;
	}
	
	// GET the showThread page
	@RequestMapping(value="welcome/discussionBoard/showThread/{threadid}", method=RequestMethod.GET)
	public ModelAndView getThread(@PathVariable int threadid) {
		ModelAndView modelAndView = new ModelAndView();
		// Get the threadname, threadsubject and threadcontent
		List<GetThreadInfoDTO> getThreadInformation = new ArrayList<GetThreadInfoDTO>();
		getThreadInformation = userService.getThreadInfoByThreadID(threadid);
		
		// Store the threadid in the global variable to be used during insertion
		getThreadID = threadid;
		
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
		modelAndView.addObject("globalCourseIDSet", getCourseID);
		
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
		
		
		modelAndView.addObject("getAllPosts", getAllPosts);
		modelAndView.setViewName("showThread");
		return modelAndView;
	}
	
	// POST request
	// Redirect it to another page which displays the discussion
	// board posts
	@RequestMapping(value="welcome/discussionBoard/showThread", 
			method=RequestMethod.POST)
	public ModelAndView postToThread(@RequestParam("discussion") String newPost,
			@RequestParam("postanonymously") String postAnon) {
		ModelAndView modelAndView = new ModelAndView();
		boolean postAnonymously = false;
		if (newPost != null) {
			// Get the student logged in
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetail = (UserDetails) auth.getPrincipal();
				String studentName = userDetail.getUsername();
				
				// Let the return type of the method be void
				// since we would just be redirecting to another
				// page which would extract the data and populate the view
				
				if (postAnon.equalsIgnoreCase("yes")) {
					postAnonymously = true;
				} else {
					postAnonymously = false;
				}
				
				userService.postToThread(getThreadID, newPost, studentName, postAnonymously);
				// Update view
				modelAndView.setViewName("redirect:showThread/"+getThreadID);
			} else {
				// permission-denied
				// must log in
			}
		} else {
			modelAndView.addObject("discussionMsg", "Please do not leave the text area empty.");
			modelAndView.setViewName("showThread");
		}
		return modelAndView;
	}
	
	// Subscribe to thread
	// POST action
	@RequestMapping(value="welcome/discussionBoard/subscribeToThread", method=RequestMethod.POST)
	public ModelAndView subscribeToThreadPost(@RequestParam("subscribeToThread") String subscription) {
		ModelAndView modelAndView = new ModelAndView();
		boolean subscribe = false;
		
		// Get the studentName
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String studentName = userDetail.getUsername();
			
			if (subscription.equalsIgnoreCase("yes")) {
				subscribe = true;
			} else {
				subscribe = false;
			}
			
			String subscriptionMsg = userService.subscribeToThread(getThreadID, studentName, subscribe);
			modelAndView.addObject("subscriptionMsg", subscriptionMsg);
		} else {
			// permission-denied page
			// user must log in
		}
		modelAndView.setViewName("redirect:showThread/"+getThreadID);
		return modelAndView;
	}
	
	// Calendar
	// Group the personal events and the course events together
	@RequestMapping(value="/viewMyCalendar", method=RequestMethod.GET)
	public ModelAndView viewMyCalenderGET() {
		ModelAndView modelAndView = new ModelAndView();
		// Get the student logged in
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			String studentName = userDetail.getUsername();

			// Get the courses in which the student is registered in
			List<GetCoursesDTO> getStudentCourses = userService.getStudentCourses(studentName);
			List<GetCalendarEventsDTO> getCalendarEventInformation;
			
			for (GetCoursesDTO course : getStudentCourses) {
				getCalendarEventInformation = userService.getCalendarEventInfo(course.getCourseid());
				for (GetCalendarEventsDTO calendarEvent : getCalendarEventInformation) {
					if (calendarEvent.isPersonalevent()) {
						if (calendarEvent.getEventcreatedby().equalsIgnoreCase(studentName)) {
							// The student created the event
							// OK to display
							// Add individual objects
							modelAndView.addObject("coursenameForPersonalCalendarEvents", course.getCoursename());
							modelAndView.addObject("personalCalendarEvents", getCalendarEventInformation);
						} else {
							// Others personal event
							// Do not display here
						}
					} else {
						// Course event
						// Add individual objects
						modelAndView.addObject("coursenameForCourseCalendarEvents", course.getCoursename());
						modelAndView.addObject("courseCalendarEvents", getCalendarEventInformation);
					}
				}
			}
		} else {
			// permission-denied page
			// user must log in
		}
		modelAndView.setViewName("studentCalendar");
		return modelAndView;
	}
	
	// Display the course calendar
	@RequestMapping(value="/welcome/discussionBoard/courseCalendar", method=RequestMethod.GET)
	public ModelAndView courseCalendarGET() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("studentCourseCalendar");
		return modelAndView;
	}
	
	// Process the events
	@RequestMapping(value="/welcome/discussionBoard/courseCalendar", method=RequestMethod.POST)
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
			
			String eventCreationMsg = userService.createCalendarEvent(getCourseID, eventDetails,
					loggedInPersonsName, personalEvent, eventTimestamp);
			modelAndView.addObject("eventCreationMsg", eventCreationMsg);
		} else {
			// permission-denied page
		}
		
		modelAndView.setViewName("courseCalendar");
		return modelAndView;
	}
	
	// Get enrolled students list for student
	@RequestMapping(value="welcome/discussionBoard/viewEnrolledStudents", method=RequestMethod.GET)
	public ModelAndView viewEnrolledStudentsGET() {
		ModelAndView modelAndView = new ModelAndView();
		List<UserInformationDTO> getEnrolledStudents = new ArrayList<UserInformationDTO>();
		getEnrolledStudents = userService.getEnrolledStudents(getCourseID);
		
		modelAndView.addObject("enrolledStudents", getEnrolledStudents);
		modelAndView.setViewName("enrolledInSameCourse");
		return modelAndView;
	}
}
