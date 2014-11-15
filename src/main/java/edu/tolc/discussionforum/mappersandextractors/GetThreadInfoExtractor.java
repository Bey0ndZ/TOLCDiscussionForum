package edu.tolc.discussionforum.mappersandextractors;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import edu.tolc.discussionforum.dto.GetThreadInfoDTO;

public class GetThreadInfoExtractor implements
		ResultSetExtractor<GetThreadInfoDTO> {
	public GetThreadInfoDTO extractData(ResultSet resultSet)
			throws SQLException, DataAccessException {

		GetThreadInfoDTO getThreadInfoDTO = new GetThreadInfoDTO();

		getThreadInfoDTO.setCourseid(resultSet.getInt(1));
		getThreadInfoDTO.setThreadid(resultSet.getInt(2));
		getThreadInfoDTO.setThreadname(resultSet.getString(3));
		getThreadInfoDTO.setThreadsubject(resultSet.getString(4));
		getThreadInfoDTO.setThreadcontent(resultSet.getString(5));
		getThreadInfoDTO.setCreatedby(resultSet.getString(6));
		getThreadInfoDTO.setPostanonymously(resultSet.getBoolean(7));

		return getThreadInfoDTO;
	}
}
