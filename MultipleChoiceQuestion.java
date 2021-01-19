package onlineTest;

import java.io.Serializable;
import java.util.Arrays;

public class MultipleChoiceQuestion extends Question implements Serializable {

	String[] answer;

	public MultipleChoiceQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
		super(text, points);
		this.examId = examId;
		this.questionNumber = questionNumber;
		this.text = text;
		this.points = points;
		this.answer = answer;
		Arrays.sort(answer);
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public int getExamId() {
		return examId;
	}

	@Override
	public int getQuestionNumber() {
		return questionNumber;
	}

	@Override
	public double getPoints() {
		return points;
	}

	@Override
	public String[] getAnswer() {
		return answer;
	}

	public String getAnswerString() {
		String ans = "";
		for (int i = 0; i < answer.length; i++) {
			ans += answer[i];
		}
		return ans;
	}

	public String getAnswerStringKey() {
		String ans = "";
		for (int i = 0; i < answer.length; i++) {
			ans += answer[i];
			if (answer.length > 1 && i < answer.length - 1) {
				ans += ",";
			}
		}
		return ans;
	}

	@Override
	public String getKey() {
		return "Question Text: " + getText() + " Points: " + getPoints() + " Correct Answer: ["
				+ this.getAnswerStringKey() + "]";
	}
}
