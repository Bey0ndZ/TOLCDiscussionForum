package edu.tolc.discussionforum.service;

import java.util.List;
import java.util.Map;

import edu.tolc.discussionforum.dto.GetCoursesDTO;
import edu.tolc.discussionforum.dto.GetPostsDTO;
import edu.tolc.discussionforum.dto.GetThreadInfoDTO;
import edu.tolc.discussionforum.dto.GetTickrDTO;
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
	
}
