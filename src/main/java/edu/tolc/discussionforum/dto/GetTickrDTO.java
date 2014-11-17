package edu.tolc.discussionforum.dto;

import java.sql.Timestamp;

public class GetTickrDTO {
	private String postedby;
	private Timestamp postedat;
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
}
