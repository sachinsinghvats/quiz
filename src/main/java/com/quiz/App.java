package com.quiz;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.quiz.bean.Difficulty;
import com.quiz.bean.QuestionType;
import com.quiz.bean.Quiz;
import com.quiz.bean.QuizBean;
import com.quiz.bean.Topic;
import com.quiz.dao.DBManager;
import com.quiz.dao.QuestionDAO;
import com.quiz.dao.QuizDAO;
import com.quiz.dao.UserDAO;

public class App {
	/**
	 * This is main entry point for the quiz application
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// Scanner to receive input from system console
		Scanner scan = new Scanner(System.in);

		System.out.print("Who are you? Enter the number: \n 1. Professor \n 2. Student\n input your answer(1,2):");
		int input = Integer.parseInt(scan.nextLine());
		if (input == 1) {
			// Professor Login
			System.out.print("Enter your userId:");
			String userid = scan.nextLine();
			System.out.print("Enter your password:");
			String pass = scan.nextLine();
			UserDAO userDAO = new UserDAO();
			// Authenticating user
			boolean isValidUser = userDAO.isValidUser(userid, pass);
			if (isValidUser) {
				int option = 0;
				while (option != 4) {
					System.out.print(
							"\n\n\n\nWhich operation you want to perform? Enter the number: \n 1. Create new Quiz \n 2. Create a topic\n 3. Create a question\n input your answer( Allowed choices 1, 2, 3 or 4 to exit):");
					option = Integer.parseInt(scan.nextLine());
					switch (option) {
					case 1:
						System.out.print("Enter quiz name:");
						String quizName = scan.nextLine();
						QuizDAO quizDAO = new QuizDAO();
						quizDAO.createQuiz(quizName);
						System.out.println("Quiz created successfully!Exiting...");
						break;
					case 2:
						System.out.print("Enter topic name:");
						String topic = scan.nextLine();
						quizDAO = new QuizDAO();
						quizDAO.createTopic(topic);
						System.out.println("Topic created successfully!Exiting...");
						break;
					case 3:
						quizDAO = new QuizDAO();
						List<Topic> topicList = quizDAO.getTopics();
						System.out.println("Listed topics are:");
						for (Topic topicinput : topicList) {
							System.out.println(topicinput.getId() + ". " + topicinput.getTopicName());
						}
						System.out.print("Enter topic number:");
						int topicId = Integer.parseInt(scan.nextLine());

						System.out.println("Listed difficulties are:");

						List<Difficulty> difficultyList = quizDAO.getDifficulties();
						for (Difficulty difficultyinput : difficultyList) {
							System.out.println(difficultyinput.getId() + ". " + difficultyinput.getName());
						}
						System.out.print("Enter difficulty number:");
						int difficultyId = Integer.parseInt(scan.nextLine());

						System.out.println("Listed quizes are:");

						List<Quiz> quizList = quizDAO.getQuizList();
						for (Quiz quizinput : quizList) {
							System.out.println(quizinput.getId() + ". " + quizinput.getName());
						}
						System.out.print("Enter quiz number:");
						int quizId = Integer.parseInt(scan.nextLine());

						System.out.println("Listed question types are:");

						List<QuestionType> qList = quizDAO.getQuestionType();
						for (QuestionType qinput : qList) {
							System.out.println(qinput.getId() + ". " + qinput.getType());
						}
						System.out.print("Enter question type number:");
						int questionTypeId = Integer.parseInt(scan.nextLine());

						System.out.print("Enter question detail:");
						String questionDescription = scan.nextLine();
						String[] choice = new String[4];
						int correctanswer = 0;
						if(questionTypeId==1) {
						System.out.println("Enter choice detail:");
						
						System.out.print("1. ");
						choice[0] = scan.nextLine();
						System.out.print("2. ");
						choice[1] = scan.nextLine();
						System.out.print("3. ");
						choice[2] = scan.nextLine();
						System.out.print("4. ");
						choice[3] = scan.nextLine();
						System.out.print("Enter sequence# of correct answer: ");
						correctanswer = Integer.parseInt(scan.nextLine());
						}
						QuestionDAO questionDAO = new QuestionDAO();
						questionDAO.createQuestion(questionDescription, quizId, questionTypeId, difficultyId, topicId,
								choice, correctanswer);
						System.out.println("Question inserted successfully! Exiting...");
						break;
					default:
						System.out.println("Thank you");
					}
				}

			} else {
				System.out.println("Sorry! We failed to recognize you.");

			}
		} else if (input == 2) {
			// student Login
			System.out.print("Enter your Name:");
			String studentName = scan.nextLine();
			char playerchoice = 'y';
			while (playerchoice == 'y') {
				System.out.println("Listed quizes are:");
				QuizDAO quizDAO = new QuizDAO();
				List<Quiz> quizList = quizDAO.getQuizList();
				Collections.shuffle(quizList, new Random(5));
				for (Quiz quizinput : quizList) {
					System.out.println(quizinput.getId() + ". " + quizinput.getName());
				}
				System.out.print("Enter quiz number you want to play:");
				int quizId = Integer.parseInt(scan.nextLine());
				List<QuizBean> quizlist = quizDAO.getQuiz(quizId);
				System.out.println("Quiz Starting now... ");
				int scoredmcq = 0;
				int totalmcq = 0;
				int totalopenq = 0;
				// Alternate scenario where there is no question in the quiz
				if (quizlist.size() == 0)
					System.out.println("Sorry this quiz does not contain any question, please contact your professor.");
				else {
					for (QuizBean quizBean : quizlist) {
						if (quizBean.getQuestionType() == 1) {
							totalmcq++;
							System.out.println(quizBean.getDescription() + "\n 1. " + quizBean.getOption()[0] + "\t 2. "
									+ quizBean.getOption()[1] + "\n 3. " + quizBean.getOption()[2] + "\t 4. "
									+ quizBean.getOption()[3]);
							System.out.print("Enter your choice:");
							int answer = Integer.parseInt(scan.nextLine());
							if (answer == quizBean.getCorrectAnswer()) {
								scoredmcq++;
							}
						} else if (quizBean.getQuestionType() == 2) {
							totalopenq++;
							System.out.println(quizBean.getDescription());
							String answeropen = scan.nextLine();
						} else {
							System.out.println("Invalid entry! Bye!!!");
						}

					}

					//Scorring
					System.out.println("\n\n\n\n\n\n Total question asked: " + quizlist.size() + "\n Total MCQ: "
							+ totalmcq + "\n " + studentName + " you scored: " + scoredmcq
							+ "\n Total open questions asked(Will be scored later): " + totalopenq);
									}
				System.out.println(" Quiz Over, do you want to play more? y/n");
				playerchoice = (char) scan.nextLine().trim().charAt(0);

			}
			System.out.println("Thanks for playing!");
			
		} else {
			System.out.println("Invalid entry, try again!");
		}
		DBManager.getInstance().closeAll();
	}

}
