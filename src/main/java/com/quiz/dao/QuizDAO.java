package com.quiz.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.quiz.bean.Difficulty;
import com.quiz.bean.QuestionType;
import com.quiz.bean.Quiz;
import com.quiz.bean.QuizBean;
import com.quiz.bean.Topic;

public class QuizDAO extends DAO{

	public QuizDAO() throws ClassNotFoundException, SQLException {
		super();
		// TODO Auto-generated constructor stub
	}
//Creating a record in quiz table
public void createQuiz(String quizName) throws SQLException, ClassNotFoundException{
        stat.execute("insert into quiz values(quiz_seq_id.nextval,'"+quizName+"')");
}

/**
 * Listing all the quizes from the database
 */
public List<Quiz> getQuizList() throws SQLException, ClassNotFoundException{
	 rs = stat.executeQuery("select * from quiz");
	 List<Quiz> quizList= new ArrayList<Quiz>();
	 while(rs.next()){
		 Quiz quiz = new Quiz();
		 quiz.setId(rs.getInt("id"));
		 quiz.setName(rs.getString("name"));
	
		 quizList.add(quiz); 
	 }
	 return quizList; 
}

// Reading topics from database
public List<Topic> getTopics() throws SQLException, ClassNotFoundException{
	 rs = stat.executeQuery("select * from topic");
	 List<Topic> topicList= new ArrayList<Topic>();
	 while(rs.next()){
	 Topic topic = new Topic();
	 topic.setId(rs.getInt("id"));
	 topic.setTopicName(rs.getString("name"));
	
	 topicList.add(topic); 
	 }
	 return topicList; 
}
//Reading difficulties from the database

public List<Difficulty> getDifficulties() throws SQLException, ClassNotFoundException{
	 rs = stat.executeQuery("select * from difficulty");
	 List<Difficulty> difficultyList= new ArrayList<Difficulty>();
	 while(rs.next()){
		 Difficulty difficulty = new Difficulty();
		 difficulty.setId(rs.getInt("id"));
		 difficulty.setName(rs.getString("name"));
	
		 difficultyList.add(difficulty); 
	 }
	 return difficultyList; 
}

// Reading question types from the database
public List<QuestionType> getQuestionType() throws SQLException, ClassNotFoundException{
	 rs = stat.executeQuery("select * from questiontype");
	 List<QuestionType> questionTypeList= new ArrayList<QuestionType>();
	 while(rs.next()){
		 QuestionType questionType = new QuestionType();
		 questionType.setId(rs.getInt("id"));
		 questionType.setType(rs.getString("type"));
	
		 questionTypeList.add(questionType); 
	 }
	 return questionTypeList; 
}

// Inserting a topic into the database
public void createTopic(String topicName) throws SQLException, ClassNotFoundException{
    stat.execute("insert into topic values(topic_seq_id.nextval,'"+topicName+"')");
}

//Reading quiz detail (includes all the questions on the quiz)
public List<QuizBean> getQuiz(int quizId) throws SQLException {
	 rs = stat.executeQuery("select distinct  questiontype, description,difficulty.name as difficulty ,topic.name as topic,Null as option1, Null as option2,Null as option3, null as option4, "
	 		+ "null as correctanswer  from question, mcqquestiontype, difficulty,topic where question.difficulty=difficulty.id  and question.topic=topic.id and "
	 		+ "quiz = "+quizId+" and questiontype=2 union  select distinct  questiontype,"
	 				+ " description,difficulty.name as difficulty, topic.name as topic,option1,option2,option3,option4, correctanswer from question,"
	 				+ " mcqquestiontype, difficulty, topic where question.difficulty=difficulty.id and  question.topic=topic.id and quiz = "+quizId+" and question.id=mcqquestiontype .questionid and question.questiontype=1");
	 List<QuizBean> quizList= new ArrayList<QuizBean>();
	 while(rs.next()){
	 QuizBean quizBean = new QuizBean();
	 quizBean.setDescription(rs.getString("description"));
	 quizBean.setQuestionType(rs.getInt("questionType"));
	 quizBean.setDifficulty(rs.getString("difficulty"));
	 quizBean.setTopic(rs.getString("topic"));
	 if(quizBean.getQuestionType() ==1) {
		 String []option = new String[] {rs.getString("option1"),rs.getString("option2"),rs.getString("option3"),rs.getString("option4")};
		 quizBean.setOption(option);
	 quizBean.setCorrectAnswer(rs.getInt("correctanswer"));
	 }
	 quizList.add(quizBean); 
	 }
	 return quizList;
}
}
