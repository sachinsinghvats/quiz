package com.quiz.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO extends DAO{

	public UserDAO() throws ClassNotFoundException, SQLException {
		super();
		// TODO Auto-generated constructor stub
	}

public boolean isValidUser(String username, String password) throws SQLException, ClassNotFoundException{
	  
       rs = stat.executeQuery("select * from user where name='"+username+"' and password='"+password+"'");
       while (rs.next()) {
    	   return true;
       }
 return false;
}
}
