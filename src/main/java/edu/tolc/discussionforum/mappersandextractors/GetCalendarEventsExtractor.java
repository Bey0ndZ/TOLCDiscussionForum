package edu.tolc.discussionforum.mappersandextractors;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import edu.tolc.discussionforum.dto.GetCalendarEventsDTO;

public class GetCalendarEventsExtractor implements
		ResultSetExtractor<GetCalendarEventsDTO> {
	public GetCalendarEventsDTO extractData(ResultSet resultSet)
			throws SQLException, DataAccessException {

		GetCalendarEventsDTO getCalendarEventsInfo = new GetCalendarEventsDTO();

		getCalendarEventsInfo.setEventinformation(resultSet.getString(1));
		getCalendarEventsInfo.setEventcreatedby(resultSet.getString(2));
		getCalendarEventsInfo.setPersonalevent(resultSet.getBoolean(3));
		getCalendarEventsInfo.setEventtimestamp(resultSet.getTimestamp(4));

		return getCalendarEventsInfo;
	}
}
