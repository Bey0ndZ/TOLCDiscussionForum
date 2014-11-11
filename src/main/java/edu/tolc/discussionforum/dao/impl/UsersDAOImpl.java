package edu.tolc.discussionforum.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import edu.tolc.discussionforum.dao.UsersDAO;
import edu.tolc.discussionforum.dto.GetCoursesDTO;
import edu.tolc.discussionforum.mappersandextractors.GetCoursesMapper;
import edu.tolc.discussionforum.model.UserInformation;

// Perform CRUD Operations
public class UsersDAOImpl implements UsersDAO {
	@Autowired
	DataSource dataSource;
	
	@Override
	public String userRegistration(UserInformation userInfo) {
		// Setting all the accounts to enabled by default
		userInfo.setEnabled(1);
		
		String userRegistrationQuery = "INSERT INTO users VALUES (?,?,?,?,?,?,?)";
		String userRolesQuery = "INSERT INTO user_roles(username, role) VALUES (?,?)";
		
		JdbcTemplate userRegistrationTemplate = new JdbcTemplate(dataSource);
		JdbcTemplate userRolesTemplate = new JdbcTemplate(dataSource);
		
		// Insert into DB
		// Query 1
		userRegistrationTemplate.update(userRegistrationQuery,
				new Object[] {userInfo.getUsername(), userInfo.getPassword(),
				userInfo.getFirstname(), userInfo.getLastname(),
				userInfo.getPhonenumber(), userInfo.getEmail(), 
				userInfo.getEnabled()});
		
		// Query 2
		userRolesTemplate.update(userRolesQuery, new Object[] {userInfo.getUsername(),
				"ROLE_STUDENT"});
		
		return "User registration successful.";
	}
	
	@Override
	public String instructorRegistration(UserInformation userInfo) {
		// Setting all the accounts to enabled by default
		userInfo.setEnabled(1);
		
		String userRegistrationQuery = "INSERT INTO users VALUES (?,?,?,?,?,?,?)";
		String userRolesQuery = "INSERT INTO user_roles(username, role) VALUES (?,?)";
		
		JdbcTemplate userRegistrationTemplate = new JdbcTemplate(dataSource);
		JdbcTemplate userRolesTemplate = new JdbcTemplate(dataSource);
		
		// Insert into DB
		// Query 1
		userRegistrationTemplate.update(userRegistrationQuery,
				new Object[] {userInfo.getUsername(), userInfo.getPassword(),
				userInfo.getFirstname(), userInfo.getLastname(),
				userInfo.getPhonenumber(), userInfo.getEmail(), 
				userInfo.getEnabled()});
		
		// Query 2
		userRolesTemplate.update(userRolesQuery, new Object[] {userInfo.getUsername(),
				"ROLE_INSTRUCTOR"});
		
		return "Instructor registration successful.";
	}

	// Creates a new course
	@Override
	public String addCourse(String courseName, String courseDescription,
			String instructorsName) {
		String addCourseQuery = "INSERT INTO courses (coursename, "
				+ "coursedescription, instructor, numberofstudentsenrolled) VALUES (?,?,?,?)";
		JdbcTemplate addCourseTemplate = new JdbcTemplate(dataSource);
		
		// Insert into DB
		addCourseTemplate.update(addCourseQuery, 
				new Object[]{courseName, courseDescription, instructorsName,
				0});
		return "Course added successfully.";
	}

	@Override
	public List<GetCoursesDTO> getCourseList(String instructorsName) {
		List<GetCoursesDTO> allCourses = new ArrayList<GetCoursesDTO>();
		
		// Query to retrieve courses
		String getCoursesQuery = "SELECT * FROM courses WHERE instructor=?";
		JdbcTemplate getCoursesTemplate = new JdbcTemplate(dataSource);
		
		// Get data from DB, map them row-wise
		allCourses = getCoursesTemplate.query(getCoursesQuery,
				new Object[]{instructorsName}, new GetCoursesMapper());
		return allCourses;
	}

}
