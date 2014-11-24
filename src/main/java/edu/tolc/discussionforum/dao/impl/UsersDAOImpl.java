package edu.tolc.discussionforum.dao.impl;

import java.security.SecureRandom;
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
import edu.tolc.discussionforum.dto.CourseEnrollmentDTO;
import edu.tolc.discussionforum.dto.FollowTickrDTO;
import edu.tolc.discussionforum.dto.GetCalendarEventsDTO;
import edu.tolc.discussionforum.dto.GetCoursesDTO;
import edu.tolc.discussionforum.dto.GetPostsDTO;
import edu.tolc.discussionforum.dto.GetThreadInfoDTO;
import edu.tolc.discussionforum.dto.GetTickrDTO;
import edu.tolc.discussionforum.dto.UserInformationDTO;
import edu.tolc.discussionforum.mail.EmailService;
import edu.tolc.discussionforum.mappersandextractors.FollowTickrMapper;
import edu.tolc.discussionforum.mappersandextractors.GetCalendarEventsMapper;
import edu.tolc.discussionforum.mappersandextractors.GetCoursesMapper;
import edu.tolc.discussionforum.mappersandextractors.GetPostsMapper;
import edu.tolc.discussionforum.mappersandextractors.GetThreadInfoMapper;
import edu.tolc.discussionforum.mappersandextractors.GetTickrMapper;
import edu.tolc.discussionforum.mappersandextractors.UserInformationMapper;
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
	
	// Update 2 tables
	// Courses and enrollment
	// But first, get number of people enrolled in a course
	// Check whether the person has already enrolled in that course
	@Override
	public String enrollStudentInCourse(String courseID, String studentName) {
		// Check if the course is present
		String courseCheckQuery = "SELECT * FROM courses WHERE courseid=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		RowCountCallbackHandler checkCourseExists = new RowCountCallbackHandler();
		jdbcTemplate.query(courseCheckQuery, new Object[] {courseID}, checkCourseExists);
		
		int checkCourseFlag = checkCourseExists.getRowCount();
		
		if (checkCourseFlag==1) {
		
			String checkStudentEnrollmentQuery = "SELECT * FROM enrollment "
					+ "WHERE courseid=? AND studentregistered=?";
			
			RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
			jdbcTemplate.query(checkStudentEnrollmentQuery, 
					new Object[]{courseID, studentName}, countCallback);
			
			int enrollmentFlag = countCallback.getRowCount();
			
			if (enrollmentFlag>=1) {
				return "You have already enrolled in the course. Please select some other course.";
			} else {
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
		} else {
			return "Course ID does not exist. Please enter a valid course id.";
		}
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
				+ "threadcontent, createdby, postanonymously, firepadurl) VALUES (?,?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		// Create a random number for firepad URL
		SecureRandom rand = new SecureRandom();
		
		// Insert into DB
		jdbcTemplate.update(createThreadQuery, 
				new Object[] {courseid, threadName, threadSubject, threadContent, creatorsName, isanonymous,
				rand.nextInt()});
		
		// Send email to the individual people they are subscribed to
		String followerQuery = "SELECT firstname, lastname, username, email FROM users WHERE username=(SELECT username FROM follow WHERE following=? AND courseid=?)";
		List<UserInformationDTO> userInformation = jdbcTemplate.query(followerQuery, 
				new Object[] {creatorsName, courseid}, new UserInformationMapper());
		String followingSubject = "A new thread has been created by: "+creatorsName;
		String followingContent = "New post by the person you follow.\n\nThread Subject: \n"+threadSubject+"\n\n"+"Thread Content: \n"+threadContent;
		
		for (UserInformationDTO user : userInformation) {
			sendEmail(user.getEmail(), followingSubject, followingContent);
		}
		
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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		// Get the timestamp
		Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
		
		// Actual insert
		jdbcTemplate.update(newThreadPostQuery, 
				new Object[] {threadid, newPost, studentName, postAnonymously, currentTimestamp});
		
		// Also email people who have subscribed to this thread
		// saying so and so user modified the post
		// we could send content too
		String getSubscribersQuery = "SELECT studentname FROM subscriptions WHERE threadid=? AND subscription=true";
		
		List<String> getAllSubscribers = jdbcTemplate.query(getSubscribersQuery,
				new Object[] {threadid}, new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
		
		// Get the thread name
		String threadName = "SELECT threadname FROM discussionboard WHERE threadid=?";
		
		String threadNameObject = jdbcTemplate.queryForObject(threadName, 
				new Object[] {threadid}, String.class);
		
		String subject = "New post has been made in: "+threadNameObject;
		String content = "";
		if (!postAnonymously) { 
			content = "Post made by: "+studentName+"\n\nContent:\n"+newPost;
		} else {
			content = "Post has been made anonymously.\n\nContent:\n"+newPost;
		}
		
		// Get email addresses
		String getEmailQuery = "SELECT email from users where username=?";
		
		// Send the email to every subscriber of the thread
		for (String subscriber : getAllSubscribers) {
			String emailID = jdbcTemplate.queryForObject(getEmailQuery, new Object[]{subscriber},
					String.class);
			sendEmail(emailID, subject, content);
		}
		
		// Send email to the individual people they are subscribed to
		String followerQuery = "SELECT firstname, lastname, username, email FROM users WHERE username=(SELECT username FROM follow WHERE following=?)";
		List<UserInformationDTO> userInformation = jdbcTemplate.query(followerQuery, 
				new Object[] {studentName}, new UserInformationMapper());
		String followingSubject = "New post made by: "+studentName+" in thread: "+threadNameObject;
		String followingContent = "A new post has been made by the person you follow.\n\nPost content: \n"+newPost;
		
		for (UserInformationDTO user : userInformation) {
			sendEmail(user.getEmail(), followingSubject, followingContent);
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
		@SuppressWarnings("resource")
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
		
		// Send email to everyone registered in the course
		String getStudentsRegisteredForCourse = "SELECT email FROM users where username=(SELECT studentregistered FROM enrollment WHERE courseid=?)";
		JdbcTemplate getStudentsTemplate = new JdbcTemplate(dataSource);
		
		List<String> studentsEmail = getStudentsTemplate.query(getStudentsRegisteredForCourse, 
				new Object[]{globalCourseID}, new RowMapper<String>() {
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString(1);
					}
				});
		
		// Get coursename
		// Send it only if personalEvent has been set to false
		if (!personalEvent) {
			// Not a personalEvent
			String getCourseNameQuery = "SELECT coursename FROM courses WHERE courseid=?";
			JdbcTemplate getCourseNameTemplate = new JdbcTemplate(dataSource);
			
			String courseName = getCourseNameTemplate.queryForObject(getCourseNameQuery, new Object[]{
					globalCourseID}, String.class);
			
			String subject = "New Calendar Event for Course: "+courseName;
			String content = "Event details: "+eventDetails+"\n\nTime: "+eventTimestamp;
			
			for (String email : studentsEmail) {
				// Send email
				sendEmail(email, subject, content);
			}
		} else {
			// Personal event
			// Send email only to the concerned person
			String getEmailOfLoggedInPerson = "SELECT email FROM users where username=?";
			JdbcTemplate getEmailTemplate = new JdbcTemplate(dataSource);
			
			String emailOfLoggedInPerson = getEmailTemplate.queryForObject(getEmailOfLoggedInPerson, 
					new Object[] {loggedInPersonsName}, String.class);
			
			String subject = "New Calendar Event";
			String content = "Event details: "+eventDetails+"\n\nTime: "+eventTimestamp;
			
			sendEmail(emailOfLoggedInPerson, subject, content);
		}
		return "Event successfully created.";
	}

	@Override
	public List<GetCalendarEventsDTO> getCalendarEventInfo(int courseid) {
		List<GetCalendarEventsDTO> getCalendarEventInformation = new ArrayList<GetCalendarEventsDTO>();
		
		String getEventInfoQuery = "SELECT eventinformation, eventcreatedby, personalevent, eventtimestamp "
				+ "FROM calendarevents WHERE courseid=?";
		JdbcTemplate getEventInfoTemplate = new JdbcTemplate(dataSource);
		
		getCalendarEventInformation = getEventInfoTemplate.query(getEventInfoQuery, 
				new Object[] {courseid}, new GetCalendarEventsMapper());
		return getCalendarEventInformation;
	}

	@Override
	public List<UserInformationDTO> getEnrolledStudents(int globalCourseID) {
		List<UserInformationDTO> getEnrolledStudents = new ArrayList<UserInformationDTO>();
		
		String enrolledStudentsQuery = "select firstname, lastname, username, email FROM users inner join enrollment on users.username=enrollment.studentregistered WHERE courseid=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		
		getEnrolledStudents = jdbcTemplate.query(enrolledStudentsQuery, new Object[] {globalCourseID}, new UserInformationMapper());		
		return getEnrolledStudents;
	}

	@Override
	public String addFollower(String studentName, String username, int courseid) {
		String addFollowerQuery = "INSERT INTO follow VALUES (?,?,?)";
		JdbcTemplate addFollowerTemplate = new JdbcTemplate(dataSource);
		
		addFollowerTemplate.update(addFollowerQuery, new Object[] {courseid, studentName, username});
		return "You are following this person.";
	}

	@Override
	public List<UserInformationDTO> getUserInformation(String loggedInPerson) {
		List<UserInformationDTO> getUserInfo = new ArrayList<UserInformationDTO>();
		String userInfoQuery = "SELECT firstname, lastname, username, email FROM users WHERE username=?";
		JdbcTemplate userInfoTemplate = new JdbcTemplate(dataSource);
		
		getUserInfo = userInfoTemplate.query(userInfoQuery, new Object[] {loggedInPerson},
				new UserInformationMapper());
		
		return getUserInfo;
	}

	@Override
	public List<CourseEnrollmentDTO> getAllEnrolledStudents(String instructorsName) {
		List<CourseEnrollmentDTO> enrolledStudents = new ArrayList<CourseEnrollmentDTO>();
		String enrolledStudentsQuery = "SELECT courses.coursename, enrollment.studentregistered "
				+ "FROM enrollment INNER JOIN courses ON enrollment.courseid=courses.courseid WHERE "
				+ "instructor=?";
		JdbcTemplate enrolledStudentsTemplate = new JdbcTemplate(dataSource);
		
		enrolledStudents = enrolledStudentsTemplate.query(enrolledStudentsQuery, 
				new Object[] {instructorsName}, new RowMapper<CourseEnrollmentDTO>() {
			public CourseEnrollmentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CourseEnrollmentDTO enrollment = new CourseEnrollmentDTO();
				enrollment.setCoursename(rs.getString(1));
				enrollment.setStudentregistered(rs.getString(2));
				return enrollment;
			}
		});
		return enrolledStudents;
	}

	@Override
	public String deleteCourse(String courseid) {		
		// Delete from discussionposts - it only has ids of posts
		// this should happen before discussionboard
		String getThreadIDsForCourses = "SELECT threadid FROM discussionboard WHERE courseid=?";
		JdbcTemplate getThreadIDsForCoursesTemplate = new JdbcTemplate(dataSource);
		List<Integer> allThreadIDs = new ArrayList<Integer>();
		
		allThreadIDs = getThreadIDsForCoursesTemplate.query(getThreadIDsForCourses, new Object[] {
			courseid}, new RowMapper<Integer>() {
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt(1);
			}
		});
		
		String deleteDPQuery = "DELETE FROM discussionposts WHERE threadid=?";
		String deleteSubscriptionsQuery = "DELETE FROM subscriptions WHERE threadid=?";
		
		JdbcTemplate deleteTemplate = new JdbcTemplate(dataSource);
		
		for (int threadID : allThreadIDs) {
			deleteTemplate.update(deleteDPQuery, new Object[]{threadID});
			deleteTemplate.update(deleteSubscriptionsQuery, new Object[] {threadID});
		}
		
		// Delete from 1. courses, 2. discussionboard, 3. enrollment, 4. calendarevents		
		String deleteCalendarEventsQuery = "DELETE FROM calendarevents WHERE courseid=?";
		String deleteEnrollmentQuery = "DELETE FROM enrollment WHERE courseid=?";
		String deleteDiscussionBoardQuery = "DELETE FROM discussionboard WHERE courseid=?";
		String deleteFollowQuery = "DELETE FROM follow WHERE courseid=?";
		String deleteCourseQuery = "DELETE FROM courses WHERE courseid=?";
		
		JdbcTemplate deleteCourseTemplate = new JdbcTemplate(dataSource);
		
		deleteCourseTemplate.update(deleteCalendarEventsQuery, new Object[] {courseid});
		deleteCourseTemplate.update(deleteEnrollmentQuery, new Object[] {courseid});
		deleteCourseTemplate.update(deleteDiscussionBoardQuery, new Object[] {courseid});
		deleteCourseTemplate.update(deleteFollowQuery, new Object[] {courseid});
		deleteCourseTemplate.update(deleteCourseQuery, new Object[] {courseid});
		
		return "Course deleted.";	
	}

	@Override
	public boolean isFollowing(String follower,
			String enrolledStudent, int courseid) {
		String followingQuery = "SELECT * FROM follow WHERE courseid=? AND username=? AND following=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		RowCountCallbackHandler rowCountCallBackHandler = new RowCountCallbackHandler();
		
		jdbcTemplate.query(followingQuery, new Object[]{courseid, follower, enrolledStudent},
				rowCountCallBackHandler);
		int rowCount = rowCountCallBackHandler.getRowCount();
		
		if (rowCount==1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<FollowTickrDTO> getLastPostInAnyThread(int courseid) {
		String lastPostInCourse = "SELECT discussionboard.threadname, discussionposts.postedby, discussionposts.postedat, discussionposts.postanonymously FROM discussionposts INNER JOIN discussionboard ON discussionboard.threadid=discussionposts.threadid INNER JOIN courses ON discussionboard.courseid=courses.courseid WHERE courses.courseid=? ORDER BY postedat DESC LIMIT 1";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		List<FollowTickrDTO> lastPostDetails = jdbcTemplate.query(lastPostInCourse, new Object[] {courseid},
				new FollowTickrMapper());
		
		return lastPostDetails;
	}

	@Override
	public void sendRatingEmail(int globalCourseID, String userRated) {
		String sendRatingEmailQuery = "SELECT email FROM users INNER JOIN enrollment ON users.username=enrollment.studentregistered WHERE enrollment.courseid=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		List<String> emailList = new ArrayList<String>();
		emailList = jdbcTemplate.query(sendRatingEmailQuery, new Object[] {globalCourseID}, 
				new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
		
		String subject = "New Student of the Week Rating";
		String content = "Hello Students,\n\nOne of your peers has performed extremely well this week and has been given 5 stars rating.\n\n"
				+ "The username of the person receiving this is: "+userRated+"\n\nI hope that everyone gets motivated by this rating and tries to get the same through hardwork and deligence.\n\nBests,\nProfessor";
		for (String email : emailList) {
			sendEmail(email, subject, content);
		}
	}
}
 