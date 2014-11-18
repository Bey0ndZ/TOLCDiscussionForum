package edu.tolc.discussionforum.mappersandextractors;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.tolc.discussionforum.dto.GetCalendarEventsDTO;

public class GetCalendarEventsMapper implements RowMapper<GetCalendarEventsDTO> {
		public GetCalendarEventsDTO mapRow(ResultSet resultSet, int line) throws SQLException {
			GetCalendarEventsExtractor extractor = new GetCalendarEventsExtractor();
		  return extractor.extractData(resultSet);
	}
}
