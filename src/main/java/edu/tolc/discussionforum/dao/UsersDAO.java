package edu.tolc.discussionforum.dao;

import java.sql.Timestamp;
import java.util.List;

import edu.tolc.discussionforum.dto.GetCalendarEventsDTO;
import edu.tolc.discussionforum.dto.GetCoursesDTO;
import edu.tolc.discussionforum.dto.GetPostsDTO;
import edu.tolc.discussionforum.dto.GetThreadInfoDTO;
import edu.tolc.discussionforum.dto.GetTickrDTO;
import edu.tolc.discussionforum.dto.UserInformationDTO;
import edu.tolc.discussionforum.model.UserInformation;

public interface UsersDAO {
	// Registration
	public String userRegistration(UserInformation userInfo);

	public String addCourse(String courseName, String courseDescription,
			String instructorsName);

	public List<GetCoursesDTO> getCourseList(String instructorsName);

	public String instructorRegistration(UserInformation userInfo);

	public List<String> getInstructorsList();
	
	// For the students when they click on View All Courses tab
	public List<GetCoursesDTO> getCourseList();
	
	// Enroll student in course
	public String enrollStudentInCourse(String courseID, String studentName);

	// Get student courses
	public List<GetCoursesDTO> getStudentCourses(String studentName);

	// Create a new thread
	public String createThread(int courseid, String threadName,
			String threadSubject, String threadContent, String studentName, boolean isanonymous);
	// Get thread information
	public List<GetThreadInfoDTO> getThreadInformation(int courseid);
	
	// Get thread by threadid
	public List<GetThreadInfoDTO> getThreadInfoByThreadID(int threadid);
	
	// Post to thread
	public void postToThread(int threadid, String newPost, String studentName, boolean postAnonymously);
	// Get all posts till then
	public List<GetPostsDTO> getPosts(int threadid);
	
	// Get details for tickr
	public List<GetTickrDTO> getDetailsForTickr(int threadid);
	
	// Subscribe to thread
	public String subscribeToThread(int getThreadID, String studentName,
			boolean subscribe);
	// Check for user subscription
	public boolean hasSubscribed(int threadid, String studentName);
	// Save the calendar
	public String createCalendarEvent(int globalCourseID, String eventDetails,
			String loggedInPersonsName, boolean personalEvent, Timestamp eventTimestamp);
	// Get calendar event information
	public List<GetCalendarEventsDTO> getCalendarEventInfo(int courseid);
	// Get user information
	public List<UserInformationDTO> getEnrolledStudents(int globalCourseID);
	// Add followers
	public String addFollower(String studentName, String username);
}
