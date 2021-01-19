package onlineTest;

public class TrueFalseQuestion extends Question {
	
	String[] answer1 = new String[1];

	public TrueFalseQuestion(int examId, int questionNumber, String text, double points, boolean answer) {
		super(text, points);
		this.examId = examId;
		this.questionNumber = questionNumber;
		this.text = text;
		this.points = points;

		if (answer) {
			answer1[0] = "True";
		} else {
			answer1[0] = "False";
		}
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
		return answer1;
	}
	
	@Override
	public String getKey() {
		return "Question Text: " + getText() + 
				" Points: " + getPoints() + 
				" Correct Answer: " + answer1[0] + "\n";
	}
	
}
