package edu.tolc.discussionforum.dto;

public class GetThreadInfoDTO {
	private int courseid;
	private int threadid;
	private String threadname;
	private String threadsubject;
	private String threadcontent;
	private String createdby;
	private boolean postanonymously;
	/**
	 * @return the threadid
	 */
	public int getThreadid() {
		return threadid;
	}
	/**
	 * @param threadid the threadid to set
	 */
	public void setThreadid(int threadid) {
		this.threadid = threadid;
	}
	/**
	 * @return the threadname
	 */
	public String getThreadname() {
		return threadname;
	}
	/**
	 * @param threadname the threadname to set
	 */
	public void setThreadname(String threadname) {
		this.threadname = threadname;
	}
	/**
	 * @return the threadsubject
	 */
	public String getThreadsubject() {
		return threadsubject;
	}
	/**
	 * @param threadsubject the threadsubject to set
	 */
	public void setThreadsubject(String threadsubject) {
		this.threadsubject = threadsubject;
	}
	/**
	 * @return the threadcontent
	 */
	public String getThreadcontent() {
		return threadcontent;
	}
	/**
	 * @param threadcontent the threadcontent to set
	 */
	public void setThreadcontent(String threadcontent) {
		this.threadcontent = threadcontent;
	}
	/**
	 * @return the createdby
	 */
	public String getCreatedby() {
		return createdby;
	}
	/**
	 * @param createdby the createdby to set
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	/**
	 * @return the postanonymously
	 */
	public boolean isPostanonymously() {
		return postanonymously;
	}
	/**
	 * @param postanonymously the postanonymously to set
	 */
	public void setPostanonymously(boolean postanonymously) {
		this.postanonymously = postanonymously;
	}
	/**
	 * @return the courseid
	 */
	public int getCourseid() {
		return courseid;
	}
	/**
	 * @param courseid the courseid to set
	 */
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
}
