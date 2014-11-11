package edu.tolc.discussionforum.mappersandextractors;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import edu.tolc.discussionforum.dto.GetCoursesDTO;

public class GetCoursesExtractor implements ResultSetExtractor<GetCoursesDTO> {
	
	 public GetCoursesDTO extractData(ResultSet resultSet) throws SQLException,
	   DataAccessException {
		 
	  GetCoursesDTO getCoursesInfoDTO = new GetCoursesDTO();
	  
	  getCoursesInfoDTO.setCourseid(resultSet.getInt(1));
	  getCoursesInfoDTO.setCoursename(resultSet.getString(2));
	  getCoursesInfoDTO.setCoursedescription(resultSet.getString(3));
	  getCoursesInfoDTO.setInstructor(resultSet.getString(4));		  
	  
	  return getCoursesInfoDTO;
	 }
}
