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
	public List<GetCoursesDTO> getCourseList() {
		List<GetCoursesDTO> allCoursesInformation = new ArrayList<GetCoursesDTO>();
		
		String getAllCourseInformation = "SELECT * from courses";
		JdbcTemplate getAllCoursesInformation = new JdbcTemplate(dataSource);
		
		allCoursesInformation = getAllCoursesInformation.query(getAllCourseInformation,
				new GetCoursesMapper());
		
		return allCoursesInformation;
	}

	@Override
	public String enrollStudentInCourse(String courseID, String studentName) {
		// Update 2 tables
				// Courses and enrollment
				// But first, get number of people enrolled in a course
				String getNumberOfStudentsEnrolled = "SELECT numberofstudentsenrolled FROM "
						+ "courses where courseid=?";
				JdbcTemplate numberOfStudentsEnrolledTemplate = new JdbcTemplate(
						dataSource);
				int numberOfStudentsEnrolled = numberOfStudentsEnrolledTemplate
						.queryForObject(getNumberOfStudentsEnrolled,
								new Object[] { courseID }, Integer.class);

				// Update the courses table
				String updateCoursesQuery = "UPDATE courses SET numberofstudentsenrolled=? WHERE "
						+ "courseid=?";
				JdbcTemplate updateCoursesTemplate = new JdbcTemplate(dataSource);
				updateCoursesTemplate.update(updateCoursesQuery,
						new Object[] { numberOfStudentsEnrolled + 1, courseID });
				
				// Query to insert in enrollment table
				String enrollmentQuery = "INSERT INTO enrollment VALUES (?, ?)";
				JdbcTemplate enrollmentTemplate = new JdbcTemplate(dataSource);
				
				enrollmentTemplate.update(enrollmentQuery, new Object[] {courseID, studentName});
				return "Successfully enrolled!";
	}

	@Override
	public List<GetCoursesDTO> getStudentCourses(String studentName) {
		// Get the courseid
		String getStudentCoursesQuery = "select courses.courseid, courses.coursename, courses.instructor, courses.coursedescription, courses.numberofstudentsenrolled from courses inner join enrollment on courses.courseid=enrollment.courseid AND studentregistered=?";
		JdbcTemplate getStudentCoursesTemplate = new JdbcTemplate(dataSource);
		
		// Ignoring the last two rows after joining the two tables
		List<GetCoursesDTO> getStudentCourses = getStudentCoursesTemplate.query(getStudentCoursesQuery, 
				new Object[] {studentName}, new GetCoursesMapper());
		
		return getStudentCourses;
	}
}
