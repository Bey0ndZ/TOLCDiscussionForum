package edu.tolc.discussionforum.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.tolc.discussionforum.dao.UsersDAO;
import edu.tolc.discussionforum.dto.CourseEnrollmentDTO;
import edu.tolc.discussionforum.dto.FollowTickrDTO;
import edu.tolc.discussionforum.dto.GetCalendarEventsDTO;
import edu.tolc.discussionforum.dto.GetCoursesDTO;
import edu.tolc.discussionforum.dto.GetPostsDTO;
import edu.tolc.discussionforum.dto.GetThreadInfoDTO;
import edu.tolc.discussionforum.dto.GetTickrDTO;
import edu.tolc.discussionforum.dto.UserInformationDTO;
import edu.tolc.discussionforum.model.UserInformation;
import edu.tolc.discussionforum.service.UsersService;

public class UsersServiceImpl implements UsersService{
	@Autowired
	UsersDAO userDAO;
	
	@Override
	public String userRegistration(UserInformation userInfo) {
		return userDAO.userRegistration(userInfo);
	}
	
	@Override
	public String instructorRegistration(UserInformation userInfo) {
		return userDAO.instructorRegistration(userInfo);
	}

	@Override
	public String addCourse(String courseName, String courseDescription,
			String instructorsName) {
		return userDAO.addCourse(courseName, courseDescription, instructorsName);
	}

	@Override
	public List<GetCoursesDTO> getCourseList(String instructorsName) {
		return userDAO.getCourseList(instructorsName);
	}

	@Override
	public List<String> getInstructorsList() {
		return userDAO.getInstructorsList();
	}

	@Override
	public List<GetCoursesDTO> getCourseList() {
		return userDAO.getCourseList();
	}

	@Override
	public String enrollStudentInCourse(String courseID, String studentName) {
		return userDAO.enrollStudentInCourse(courseID, studentName);
	}

	@Override
	public List<GetCoursesDTO> getStudentCourses(String studentName) {
		return userDAO.getStudentCourses(studentName);
	}

	@Override
	public String createThread(int courseid, String threadName,
			String threadSubject, String threadContent, String studentName, boolean isanonymous) {
		return userDAO.createThread(courseid, threadName, threadSubject, threadContent, studentName, isanonymous);
	}

	@Override
	public List<GetThreadInfoDTO> getThreadInformation(int courseid) {
		return userDAO.getThreadInformation(courseid);
	}

	@Override
	public List<GetThreadInfoDTO> getThreadInfoByThreadID(int threadid) {
		return userDAO.getThreadInfoByThreadID(threadid);
	}

	@Override
	public void postToThread(int threadid, String newPost, String studentName, boolean postAnonymously) {
		userDAO.postToThread(threadid, newPost, studentName, postAnonymously);		
	}

	@Override
	public List<GetPostsDTO> getPosts(int threadid) {
		return userDAO.getPosts(threadid);
	}

	@Override
	public List<GetTickrDTO> getDetailsForTickr(int threadid) {
		return userDAO.getDetailsForTickr(threadid);
	}

	@Override
	public String subscribeToThread(int getThreadID, String studentName,
			boolean subscribe) {
		return userDAO.subscribeToThread(getThreadID, studentName, subscribe);
	}

	@Override
	public boolean hasSubscribed(int threadid, String studentName) {
		return userDAO.hasSubscribed(threadid, studentName);
	}

	@Override
	public String createCalendarEvent(int globalCourseID, String eventDetails,
			String loggedInPersonsName, boolean personalEvent, Timestamp eventTimestamp) {
		return userDAO.createCalendarEvent(globalCourseID, eventDetails, loggedInPersonsName, personalEvent, eventTimestamp);
	}

	@Override
	public List<GetCalendarEventsDTO> getCalendarEventInfo(int courseid) {
		return userDAO.getCalendarEventInfo(courseid);
	}

	@Override
	public List<UserInformationDTO> getEnrolledStudents(int globalCourseID) {
		return userDAO.getEnrolledStudents(globalCourseID);
	}

	@Override
	public String addFollower(String studentName, String username, int courseid) {
		return userDAO.addFollower(studentName, username, courseid);
	}

	@Override
	public List<UserInformationDTO> getUserInformation(String loggedInPerson) {
		return userDAO.getUserInformation(loggedInPerson);
	}

	@Override
	public List<CourseEnrollmentDTO> getAllEnrolledStudents(String instructorsName) {
		return userDAO.getAllEnrolledStudents(instructorsName);
	}

	@Override
	public String deleteCourse(String courseid) {
		return userDAO.deleteCourse(courseid);
	}

	@Override
	public boolean isFollowing(String follower,
			String enrolledStudent, int courseid) {
		return userDAO.isFollowing(follower, enrolledStudent, courseid);
	}

	@Override
	public List<FollowTickrDTO> getLastPostInAnyThread(int courseid) {
		return userDAO.getLastPostInAnyThread(courseid);
	}
}	
