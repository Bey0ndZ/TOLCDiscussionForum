package edu.tolc.discussionforum.dto;

import java.sql.Timestamp;

public class GetCalendarEventsDTO {
	private String eventinformation;
	private String eventcreatedby;
	private boolean personalevent;
	private Timestamp eventtimestamp;
	/**
	 * @return the eventinformation
	 */
	public String getEventinformation() {
		return eventinformation;
	}
	/**
	 * @param eventinformation the eventinformation to set
	 */
	public void setEventinformation(String eventinformation) {
		this.eventinformation = eventinformation;
	}
	/**
	 * @return the eventcreatedby
	 */
	public String getEventcreatedby() {
		return eventcreatedby;
	}
	/**
	 * @param eventcreatedby the eventcreatedby to set
	 */
	public void setEventcreatedby(String eventcreatedby) {
		this.eventcreatedby = eventcreatedby;
	}
	/**
	 * @return the eventtimestamp
	 */
	public Timestamp getEventtimestamp() {
		return eventtimestamp;
	}
	/**
	 * @param eventtimestamp the eventtimestamp to set
	 */
	public void setEventtimestamp(Timestamp eventtimestamp) {
		this.eventtimestamp = eventtimestamp;
	}
	/**
	 * @return the personalevent
	 */
	public boolean isPersonalevent() {
		return personalevent;
	}
	/**
	 * @param personalevent the personalevent to set
	 */
	public void setPersonalevent(boolean personalevent) {
		this.personalevent = personalevent;
	}
}
