package edu.tolc.discussionforum.dto;

import java.sql.Timestamp;

public class FollowTickrDTO {
	private String threadname;
	private String postedby;
	private Timestamp postedat;
	private boolean postanonymously;
	/**
	 * @return the postedby
	 */
	public String getPostedby() {
		return postedby;
	}
	/**
	 * @param postedby the postedby to set
	 */
	public void setPostedby(String postedby) {
		this.postedby = postedby;
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
	 * @return the postedat
	 */
	public Timestamp getPostedat() {
		return postedat;
	}
	/**
	 * @param postedat the postedat to set
	 */
	public void setPostedat(Timestamp postedat) {
		this.postedat = postedat;
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
}
