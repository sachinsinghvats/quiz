package com.quiz.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QuestionDAO extends DAO{

	public QuestionDAO() throws ClassNotFoundException, SQLException {
		super();
		// TODO Auto-generated constructor stub
	}
	//Inserting question into the database
	public void createQuestion(String questionDescription, int quiz, int questionType, int difficulty, int topic, String[] choice, int correctanswer) throws SQLException, ClassNotFoundException{
        stat.execute("insert into question values(question_seq_id.nextval, "+questionType+",'"+questionDescription+"',"+difficulty+","+topic+","+quiz+")");
        //if its multiple choice question insert options in MCQQUESTIONTYPE table.
        if(questionType==1)
        stat.execute("insert into mcqquestiontype values(mcqquestiontype_seq_id.nextval, question_seq_id.currval,'"+choice[0]+"','"+choice[1]+"','"+choice[2]+"','"+choice[3]+"',"+correctanswer+")");  
}
}
