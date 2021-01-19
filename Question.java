package onlineTest;

import java.io.Serializable;
import java.util.TreeMap;

public abstract class Question implements Serializable {

	int examId;
	int questionNumber;
	String text;
	double points;
	String[] answer1;

	public Question(String text, double points) {
		this.text = text;
		this.points = points;
	}

	public abstract int getExamId();

	public abstract int getQuestionNumber();

	public abstract String getText();

	public abstract double getPoints();

	public abstract String[] getAnswer();

	public abstract String getKey();

}
