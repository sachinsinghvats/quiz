package com.quiz.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
public static DBManager dbManager = null;
private Statement stmt = null;
private Connection conn = null;

//constructor to initialize database
private DBManager() throws ClassNotFoundException, SQLException {
	 Class.forName("org.h2.Driver");
      conn = DriverManager.getConnection("jdbc:h2:~/test","sa","");
     stmt = conn.createStatement();

}
//Singleton object
public static DBManager getInstance() throws ClassNotFoundException, SQLException {
	if (dbManager == null)
		dbManager= new DBManager();
	return dbManager;
}

//returning intialized statement
public Statement getStatement() {
	return stmt;
}
//closing database connection.
public void closeAll() throws SQLException {
	   stmt.close();
     conn.close();
}
}
