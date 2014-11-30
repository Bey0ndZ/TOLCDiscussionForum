package edu.tolc.discussionforum.service;

import java.sql.Timestamp;
import java.util.List;

import edu.tolc.discussionforum.dto.CourseEnrollmentDTO;
import edu.tolc.discussionforum.dto.FollowTickrDTO;
import edu.tolc.discussionforum.dto.GetCalendarEventsDTO;
import edu.tolc.discussionforum.dto.GetCoursesDTO;
import edu.tolc.discussionforum.dto.GetPostsDTO;
import edu.tolc.discussionforum.dto.GetThreadInfoDTO;
import edu.tolc.discussionforum.dto.GetTickrDTO;
import edu.tolc.discussionforum.dto.UserInformationDTO;
import edu.tolc.discussionforum.model.UserInformation;

public interface UsersService {
	// Registration
	public String userRegistration(UserInformation userInfo);
	// Register instructors
	public String instructorRegistration(UserInformation userInfo);
	// For instructors - adding a new course
	public String addCourse(String courseName, String courseDescription,
			String instructorsName);
	// For instructors
	public List<GetCoursesDTO> getCourseList(String instructorsName);
	// This method could be reused for admin
	public List<String> getInstructorsList();
	// Get all the courses for student
	public List<GetCoursesDTO> getCourseList();
	// Enroll student in course
	public String enrollStudentInCourse(String courseID, String studentName);
	// Get student courses
	public List<GetCoursesDTO> getStudentCourses(String studentName);
	// Creating the thread
	public String createThread(int courseid, String threadName,
			String threadSubject, String threadContent, String studentName, boolean isanonymous);
	// Get the thread information
	public List<GetThreadInfoDTO> getThreadInformation(int courseid);
	// Get thread information
	public List<GetThreadInfoDTO> getThreadInfoByThreadID(int threadid);
	// Post to thread
	public void postToThread(int threadid, String newPost, String studentName, boolean postAnonymously);
	// Get posts till that time
	public List<GetPostsDTO> getPosts(int threadid);
	// Get details for tickr
	public List<GetTickrDTO> getDetailsForTickr(int threadid);
	// Subscription to a particular thread
	public String subscribeToThread(int getThreadID, String studentName,
			boolean subscribe);
	// Check for user subscription
	public boolean hasSubscribed(int threadid, String studentName);
	// Saving the event
	public String createCalendarEvent(int globalCourseID, String eventDetails,
			String loggedInPersonsName, boolean personalEvent, Timestamp eventTimestamp);
	// Get calendar events
	public List<GetCalendarEventsDTO> getCalendarEventInfo(int courseid);
	// Get enrolled users
	public List<UserInformationDTO> getEnrolledStudents(int globalCourseID);
	// Add followers
	public String addFollower(String studentName, String username, int getCourseID);
	// Get user information
	public List<UserInformationDTO> getUserInformation(String loggedInPerson);
	// Get all enrolled students for instructor
	public List<CourseEnrollmentDTO> getAllEnrolledStudents(String instructorsName);
	// Delete course from all tables
	public String deleteCourse(String courseid);
	public boolean isFollowing(String follower,
			String string, int courseid);
	public List<FollowTickrDTO> getLastPostInAnyThread(int courseid);
	// Send rating email to people in the same class
	public void sendRatingEmail(int globalCourseID, String userRated);
	// Get the firepad url value
	public int getFirepadURLValue(int threadid);
	List<String> deleteInstructor();
}
