package edu.tolc.discussionforum.dto;

import java.sql.Timestamp;

public class GetPostsDTO {
	private String postcontent;
	private String postedby;
	private boolean postanonymously;
	private Timestamp postedat;
	private boolean editedwiki;
	/**
	 * @return the postcontent
	 */
	public String getPostcontent() {
		return postcontent;
	}
	/**
	 * @param postcontent the postcontent to set
	 */
	public void setPostcontent(String postcontent) {
		this.postcontent = postcontent;
	}
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
	 * @return the editedwiki
	 */
	public boolean isEditedwiki() {
		return editedwiki;
	}
	/**
	 * @param editedwiki the editedwiki to set
	 */
	public void setEditedwiki(boolean editedwiki) {
		this.editedwiki = editedwiki;
	}
}
