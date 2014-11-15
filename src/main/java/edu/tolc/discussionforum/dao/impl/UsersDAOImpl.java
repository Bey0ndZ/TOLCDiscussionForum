package edu.tolc.discussionforum.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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
				new Object[] { userInfo.getUsername(), userInfo.getPassword(),
						userInfo.getFirstname(), userInfo.getLastname(),
						userInfo.getPhonenumber(), userInfo.getEmail(),
						userInfo.getEnabled() });

		// Query 2
		userRolesTemplate.update(userRolesQuery,
				new Object[] { userInfo.getUsername(), "ROLE_STUDENT" });

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
				new Object[] { userInfo.getUsername(), userInfo.getPassword(),
						userInfo.getFirstname(), userInfo.getLastname(),
						userInfo.getPhonenumber(), userInfo.getEmail(),
						userInfo.getEnabled() });

		// Query 2
		userRolesTemplate.update(userRolesQuery,
				new Object[] { userInfo.getUsername(), "ROLE_INSTRUCTOR" });

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
		addCourseTemplate.update(addCourseQuery, new Object[] { courseName,
				courseDescription, instructorsName, 0 });
		return "Course added successfully.";
	}

	// We could reuse this method by displaying
	// the instructor name for the student in the open courses
	@Override
	public List<GetCoursesDTO> getCourseList(String instructorsName) {
		List<GetCoursesDTO> allCourses = new ArrayList<GetCoursesDTO>();

		// Query to retrieve courses
		String getCoursesQuery = "SELECT * FROM courses WHERE instructor=?";
		JdbcTemplate getCoursesTemplate = new JdbcTemplate(dataSource);

		// Get data from DB, map them row-wise
		allCourses = getCoursesTemplate.query(getCoursesQuery,
				new Object[] { instructorsName }, new GetCoursesMapper());
		return allCourses;
	}

	@Override
	public List<String> getInstructorsList() {
		// Get the instructors list from user_roles table
		String getInstructorsQuery = "SELECT username FROM user_roles WHERE "
				+ "role=?";
		JdbcTemplate getInstructorsTemplate = new JdbcTemplate(dataSource);
		List<String> instructorsList = getInstructorsTemplate.query(
				getInstructorsQuery, new Object[] { "ROLE_INSTRUCTOR" },
				new RowMapper<String>() {
					public String mapRow(ResultSet rs, int rowNum)
							throws SQLException {

						String instructor;
						instructor = rs.getString(1);
						return instructor;
					}
				});
		return instructorsList;
	}

	@Override
	public String enrollStudentInAllCourses(String studentName,
			String instructorsName) {
		// Update 2 tables
		// Courses and enrollment
		// But first, get number of people enrolled in a course
		String getNumberOfStudentsEnrolled = "SELECT numberofstudentsenrolled FROM "
				+ "courses where instructor=?";
		JdbcTemplate numberOfStudentsEnrolledTemplate = new JdbcTemplate(
				dataSource);
		int numberOfStudentsEnrolled = numberOfStudentsEnrolledTemplate
				.queryForObject(getNumberOfStudentsEnrolled,
						new Object[] { instructorsName }, Integer.class);

		// Update the courses table
		String updateCoursesQuery = "UPDATE courses SET numberofstudentsenrolled=? WHERE "
				+ "instructor=?";
		JdbcTemplate updateCoursesTemplate = new JdbcTemplate(dataSource);
		updateCoursesTemplate.update(updateCoursesQuery,
				new Object[] { numberOfStudentsEnrolled + 1 });

		// Insert into the enrollment table
		// Instructors may have multiple courses
		List<Integer> allCourseIDs = new ArrayList<Integer>();
		String getAllCourseIDsForInstructor = "SELECT courseid FROM courses where instructor=?";
		JdbcTemplate getAllCourseIDsForInstructorTemplate = new JdbcTemplate(
				dataSource);

		allCourseIDs = getAllCourseIDsForInstructorTemplate.query(
				getAllCourseIDsForInstructor, new RowMapper<Integer>() {
					public Integer mapRow(ResultSet rs, int rowNum)
							throws SQLException {

						int courseid;
						courseid = rs.getInt(1);
						return courseid;
					}
				});
		
		// Query to insert in enrollment table
		String enrollmentQuery = "INSERT INTO enrollment VALUES (?, ?)";
		JdbcTemplate enrollmentTemplate = new JdbcTemplate(dataSource);
		
		for (int courseid : allCourseIDs) {
			enrollmentTemplate.update(enrollmentQuery, new Object[] {courseid, studentName});
		}
		return "Successfully enrolled!";
	}
}
