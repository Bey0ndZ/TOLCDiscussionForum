package edu.tolc.discussionforum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.tolc.discussionforum.dao.UsersDAO;
import edu.tolc.discussionforum.dto.GetCoursesDTO;
import edu.tolc.discussionforum.dto.GetThreadInfoDTO;
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
}	
