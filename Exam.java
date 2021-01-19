package onlineTest;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeMap;

public class Exam implements Serializable {

	TreeMap<Integer, Question> questions;
	TreeMap<Integer, String> answers;
	TreeMap<Integer, Double> pointsMap;

	private int examId;
	private String text;
	private String title;
	private double examScore;
	int numOfQuestions = 0;
	double[] answerArr;

	public Exam(int examId, String title) {
		this.examId = examId;
		this.title = title;
		examScore = 0;
		numOfQuestions = 0;
		answerArr = new double[numOfQuestions];
		answers = new TreeMap<>();
		pointsMap = new TreeMap<>();
		questions = new TreeMap<>();
	}

	public void addFITBQuestion(int examId, int questionNumber, String text, double points, String[] answer) {

		FillInTheBlanksQuestion a = new FillInTheBlanksQuestion(examId, questionNumber, text, points, answer);
		questions.put(questionNumber, a);
		answers.put(questionNumber, a.getAnswerString());
		pointsMap.put(questionNumber, points);
		examScore += points;
		numOfQuestions++;

	}

	public void addMultipleChoiceQuestion(int examId, int questionNumber, String text, double points, String[] answer) {

		MultipleChoiceQuestion a = new MultipleChoiceQuestion(examId, questionNumber, text, points, answer);
		questions.put(questionNumber, a);
		answers.put(questionNumber, a.getAnswerString());
		pointsMap.put(questionNumber, points);
		examScore += points;
		numOfQuestions++;
	}

	public void addTrueFalseQuestion(int examId, int questionNumber, String text, double points, boolean answer) {

		String[] an = new String[1];
		TrueFalseQuestion a = new TrueFalseQuestion(examId, questionNumber, text, points, answer);
		questions.put(questionNumber, a);
		if (answer) {
			an[0] = "True";
		} else {
			an[0] = "False";
		}
		answers.put(questionNumber, an[0]);
		pointsMap.put(questionNumber, points);
		examScore += points;
		numOfQuestions++;
	}

	public int getId() {
		return examId;
	}

	public String getText() {
		return text;
	}

	public String getTitle() {
		return title;
	}

	public double getExamScore() {
		return examScore;
	}

	public int getNumOfQuestions() {
		return numOfQuestions;
	}

	public TreeMap<Integer, Question> returnQuestTree() {
		return questions;
	}

	public TreeMap<Integer, String> returnAnsTree() {
		return answers;
	}

	public TreeMap<Integer, Double> returnPointsTree() {
		return pointsMap;
	}
}
