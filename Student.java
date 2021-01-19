package onlineTest;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeMap;

public class Student implements Serializable {
	
	//		examId 			question# 
	TreeMap<Integer, TreeMap<Integer, String>> the; 
	TreeMap<Integer, String[]> sAnswers;
	private String name;
	int examId;
	String[] answer1;
	
	public Student(String name) {
		this.name = name;
		sAnswers = new TreeMap<>();
		the = new TreeMap<Integer, TreeMap<Integer, String>>(); 
		answer1 = new String[1];
	}
	
	public void answerTrueFalseQuestion(String studentName, int examId, int questionNumber, boolean answer) {
		this.examId = examId;
		if (answer) {
			answer1[0] = "True";
		} else {
			answer1[0] = "False";
		}
		
		if (!the.containsKey(examId)) {
			the.put(examId, new TreeMap<Integer, String>());
		} 
		if (the.containsKey(examId)) {
			the.get(examId).put(questionNumber, answer1[0]);
			// sAnswers.put(questionNumber, answer1[0]);
		}
	}
	
	public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		String a = "";
		for (int i = 0; i < answer.length; i++) {
			a += answer[i];
		}
		if (!the.containsKey(examId)) {
			the.put(examId, new TreeMap<Integer, String>());
		} 
		if (the.containsKey(examId)) {
			the.get(examId).put(questionNumber, a);
			// sAnswers.put(questionNumber, a);
		}
	}
	
	public void answerFillInTheBlanksQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		String a = "";
		for (int i = 0; i < answer.length; i++) {
			a += answer[i] + " ";
		}
		if (!the.containsKey(examId)) {
			the.put(examId, new TreeMap<Integer, String>());
		} 
		if (the.containsKey(examId)) {
			the.get(examId).put(questionNumber, a);
			sAnswers.put(questionNumber, answer);
		}
	}
	
	public TreeMap<Integer, String> getStudentMap(Integer eId) {
		
		TreeMap<Integer, String> x = new TreeMap<>();
		x = the.get(eId);
		
		return x;
	}
}
