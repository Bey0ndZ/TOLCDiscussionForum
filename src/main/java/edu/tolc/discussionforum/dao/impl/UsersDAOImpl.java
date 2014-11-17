package edu.tolc.discussionforum.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import edu.tolc.discussionforum.dao.UsersDAO;
import edu.tolc.discussionforum.dto.GetCoursesDTO;
import edu.tolc.discussionforum.dto.GetPostsDTO;
import edu.tolc.discussionforum.dto.GetThreadInfoDTO;
import edu.tolc.discussionforum.dto.GetTickrDTO;
import edu.tolc.discussionforum.mail.EmailService;
import edu.tolc.discussionforum.mappersandextractors.GetCoursesMapper;
import edu.tolc.discussionforum.mappersandextractors.GetPostsMapper;
import edu.tolc.discussionforum.mappersandextractors.GetThreadInfoMapper;
import edu.tolc.discussionforum.mappersandextractors.GetTickrMapper;
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

	@Override
	public String createThread(int courseid, String threadName,
			String threadSubject, String threadContent, String creatorsName, boolean isanonymous) {
		String createThreadQuery = "INSERT INTO discussionboard (courseid, threadname, threadsubject,"
				+ "threadcontent, createdby, postanonymously) VALUES (?,?,?,?,?,?)";
		JdbcTemplate createThreadTemplate = new JdbcTemplate(dataSource);
		
		// Insert into DB
		createThreadTemplate.update(createThreadQuery, 
				new Object[] {courseid, threadName, threadSubject, threadContent, creatorsName, isanonymous});
		
		return "Thread created.";
	}

	@Override
	public List<GetThreadInfoDTO> getThreadInformation(int courseid) {
		List<GetThreadInfoDTO> getThreadInformation = new ArrayList<GetThreadInfoDTO>();
		String getThreadInfoQuery = "SELECT courseid, threadid, threadname, threadsubject, threadcontent,"
				+ "createdby, postanonymously FROM discussionboard WHERE courseid=?";
		JdbcTemplate getThreadInfoTemplate = new JdbcTemplate(dataSource);
		
		getThreadInformation = getThreadInfoTemplate.query(getThreadInfoQuery, 
				new Object[] {courseid}, new GetThreadInfoMapper());
		
		return getThreadInformation;
	}

	@Override
	public List<GetThreadInfoDTO> getThreadInfoByThreadID(int threadid) {
		List<GetThreadInfoDTO> getThreadInformation = new ArrayList<GetThreadInfoDTO>();
		String getThreadInfoQuery = "SELECT courseid, threadid, threadname, threadsubject, threadcontent,"
				+ "createdby, postanonymously FROM discussionboard "
				+ "WHERE threadid=?";
		JdbcTemplate getThreadInfoTemplate = new JdbcTemplate(dataSource);
		
		getThreadInformation = getThreadInfoTemplate.query(getThreadInfoQuery, 
				new Object[] {threadid}, new GetThreadInfoMapper());
		return getThreadInformation;
	}

	@Override
	public void postToThread(int threadid, String newPost, String studentName, boolean postAnonymously) {	
		String newThreadPostQuery = "INSERT INTO discussionposts(threadid, postcontent, postedby, postanonymously, postedat) "
				+ "VALUES (?,?,?,?,?)";
		JdbcTemplate newThreadPostTemplate = new JdbcTemplate(dataSource);
		
		// Get the timestamp
		Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
		
		// Actual insert
		newThreadPostTemplate.update(newThreadPostQuery, 
				new Object[] {threadid, newPost, studentName, postAnonymously, currentTimestamp});
		
		// Also email people who have subscribed to this thread
		// saying so and so user modified the post
		// we could send content too
		String getSubscribersQuery = "SELECT studentname FROM subscriptions WHERE threadid=? AND subscription=true";
		JdbcTemplate getSubscribersTemplate = new JdbcTemplate(dataSource);
		
		List<String> getAllSubscribers = getSubscribersTemplate.query(getSubscribersQuery,
				new Object[] {threadid}, new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
		
		// Get the thread name
		String threadName = "SELECT threadname FROM discussionboard WHERE threadid=?";
		JdbcTemplate threadNameTemplate = new JdbcTemplate(dataSource);
		
		String threadNameObject = threadNameTemplate.queryForObject(threadName, 
				new Object[] {threadid}, String.class);
		
		String subject = "New post has been made in: "+threadNameObject;
		String content = "Post made by: "+studentName+"\n\nContent:\n"+newPost;
		
		// Get email addresses
		String getEmailQuery = "SELECT email from users where username=?";
		JdbcTemplate getEmailTemplate = new JdbcTemplate(dataSource);
		
		// Send the email to every subscriber of the thread
		for (String subscriber : getAllSubscribers) {
			String emailID = getEmailTemplate.queryForObject(getEmailQuery, new Object[]{subscriber},
					String.class);
			sendEmail(emailID, subject, content);
		}
	}

	@Override
	public List<GetPostsDTO> getPosts(int threadid) {
		String getPostsQuery = "SELECT postcontent, postedby, postanonymously, postedat "
				+ "FROM discussionposts WHERE threadid=?";
		JdbcTemplate getPostsTemplate = new JdbcTemplate(dataSource);
		List<GetPostsDTO> getAllPosts = new ArrayList<GetPostsDTO>();
		
		getAllPosts = getPostsTemplate.query(getPostsQuery, 
				new Object[] {threadid}, new GetPostsMapper());
		return getAllPosts;
	}

	@Override
	public List<GetTickrDTO> getDetailsForTickr(int threadid) {
		String getDetailsQuery = "SELECT postedby, postedat, postanonymously FROM discussionposts WHERE threadid=? ORDER BY postedat desc LIMIT 1";
		JdbcTemplate getDetailsTemplate = new JdbcTemplate(dataSource);
		
		List<GetTickrDTO> getNameAndTimeForTickr = new ArrayList<GetTickrDTO>();
		getNameAndTimeForTickr = getDetailsTemplate.query(getDetailsQuery, new Object[] {threadid},
				new GetTickrMapper());
		return getNameAndTimeForTickr;
	}

	@Override
	public String subscribeToThread(int getThreadID, String studentName,
			boolean subscribe) {
		String subscribeQuery = "INSERT INTO subscriptions (threadid, studentname, "
				+ "subscription) VALUES (?,?,?)";
		JdbcTemplate subscribeTemplate = new JdbcTemplate(dataSource);
		
		subscribeTemplate.update(subscribeQuery, new Object[] {getThreadID, studentName,
				subscribe});
		return "You have been subscribed to this thread.";
	}

	@Override
	public boolean hasSubscribed(int threadid, String studentName) {
		String userSubscribedQuery = "SELECT subscription FROM subscriptions WHERE "
				+ "threadid=? AND studentname=?";
		JdbcTemplate userSubscribedTemplate = new JdbcTemplate(dataSource);
		RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
		
		userSubscribedTemplate.query(userSubscribedQuery, new Object[] {threadid, studentName}, countCallback);
		int rowCount = countCallback.getRowCount();
		
		if (rowCount==1) {
			return true;
		} else {
			return false;
		}
	}
	
	// Email functionality
	// Just call the method with params
	public void sendEmail(String to, String subject, String msg) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring-mail.xml");
		EmailService emailService = (EmailService) context.getBean("email");
		emailService.sendMail("tolcdiscussionforum@gmail.com", to, subject, msg);
	}

	@Override
	public String createCalendarEvent(int globalCourseID, String eventDetails,
			String loggedInPersonsName, boolean personalEvent, Timestamp eventTimestamp) {
		String createCalendarEventQuery = "INSERT INTO calendarevents(courseid,"
				+ "eventinformation, eventcreatedby, personalevent, eventtimestamp) VALUES (?,?,?,?,?)";
		JdbcTemplate createCalendarEventTemplate = new JdbcTemplate(dataSource);
		createCalendarEventTemplate.update(createCalendarEventQuery, new Object[] {globalCourseID,
				eventDetails, loggedInPersonsName, personalEvent, eventTimestamp});
		return "Event successfully created.";
	}
}
