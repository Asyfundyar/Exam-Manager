package onlineTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.TreeMap;

public class SystemManager implements Manager, Serializable {

	TreeMap<Integer, Exam> exams;
	TreeMap<String, Student> students;
	String[] sKey;
	String[] aKey;
	double totalFBPoints;
	String[] letterGradesS = new String[0];
	double[] cutoffsS;

	public SystemManager() {
		exams = new TreeMap<>();
		students = new TreeMap<>();
		totalFBPoints = 0;
	}

	@Override
	public boolean addExam(int examId, String title) {
		Exam e1 = new Exam(examId, title);
		exams.put(examId, e1);
		return false;
	}

	@Override
	public void addTrueFalseQuestion(int examId, int questionNumber, String text, double points, boolean answer) {
		Exam e = exams.get(examId);
		e.addTrueFalseQuestion(examId, questionNumber, text, points, answer);
	}

	@Override
	public void addMultipleChoiceQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
		Exam e = exams.get(examId);
		e.addMultipleChoiceQuestion(examId, questionNumber, text, points, answer);
	}

	@Override
	public void addFillInTheBlanksQuestion(int examId, int questionNumber, String text, double points,
			String[] answer) {
		Exam e = exams.get(examId);
		e.addFITBQuestion(examId, questionNumber, text, points, answer);
	}

	@Override
	public String getKey(int examId) {
		Exam e = exams.get(examId);
		String key = "";

		Collection<Question> c = e.returnQuestTree().values();

		java.util.Iterator<Question> itr = c.iterator();

		while ((itr).hasNext()) {
			key += itr.next().getKey();
		}
		return key;
	}

	@Override
	public boolean addStudent(String name) {
		Student student = new Student(name);
		students.put(name, student);
		return false;
	}

	@Override
	public void answerTrueFalseQuestion(String studentName, int examId, int questionNumber, boolean answer) {
		Student s = students.get(studentName);
		s.answerTrueFalseQuestion(studentName, examId, questionNumber, answer);
	}

	@Override
	public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		Student s = students.get(studentName);
		s.answerMultipleChoiceQuestion(studentName, examId, questionNumber, answer);
	}

	@Override
	public void answerFillInTheBlanksQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		Student s = students.get(studentName);
		s.answerFillInTheBlanksQuestion(studentName, examId, questionNumber, answer);
	}

	@Override
	public double getExamScore(String studentName, int examId) {
		String answers = "";
		Exam e = exams.get(examId);
		Student s = students.get(studentName);
		sKey = new String[e.getNumOfQuestions()];
		sKey = getStudentAnswerKey(studentName, examId);
		aKey = new String[e.getNumOfQuestions()];
		aKey = getAnswerKey(examId);
		double sPoints = 0;
		double[] pKey = getPossiblePoints(examId);
		totalFBPoints = 0;

		for (int x = 0; x < aKey.length; x++) {
			if (!sKey[x].contains(" ")) {
				if (aKey[x].equals(sKey[x])) {
					sPoints += pKey[x];
				}
			} else {
				String[] sA = sKey[x].split(" ");
				String[] cA = aKey[x].split(" ");
				double fITBPoints = pKey[x] / cA.length;
				double totalPoints = 0;
				for (int l = 0; l < sA.length; l++) {
					for (int m = 0; m < cA.length; m++) {
						if (sA[l].equals(cA[m])) {
							totalPoints += fITBPoints;
						}
					}
				}
				totalFBPoints += totalPoints;
			}
		}

		return sPoints + this.getFBPoints(studentName, examId);
	}

	@Override
	public String getGradingReport(String studentName, int examId) {
		totalFBPoints = 0;
		String ans = "";
		Exam e = exams.get(examId);
		sKey = getStudentAnswerKey(studentName, examId);
		aKey = getAnswerKey(examId);
		double[] pKey = this.getPossiblePoints(examId);

		int x = 1;
		for (int i = 0; i < sKey.length; i++) {
			ans += "Question #" + x++;
			if (!sKey[i].contains(" ")) {
				if (sKey[i].equals(aKey[i])) {
					ans += " " + pKey[i];
				} else {
					ans += " " + 0.0;
				}
				ans += " points out of " + pKey[i] + " \n";
			} else {
				String[] sA = sKey[i].split(" ");
				String[] cA = aKey[i].split(" ");
				double fITBPoints = pKey[i] / cA.length;
				double totalPoints = 0;
				for (int l = 0; l < sA.length; l++) {
					for (int m = 0; m < cA.length; m++) {
						if (sA[l].equals(cA[m])) {
							totalPoints += fITBPoints;
						}
					}
				}
				ans += totalPoints + " points out of " + pKey[i] + " \n";
				totalFBPoints += totalPoints;
				// this.setFBPoints(studentName, examId, totalFBPoints);
			}
		}
		// double total = this.getExamScore(studentName, examId) + totalFBPoints;
		ans += "Final Score: " + this.getExamScore(studentName, examId) + " out of " + e.getExamScore();
		return ans;
	}

	@Override
	public void setLetterGradesCutoffs(String[] letterGrades, double[] cutoffs) {
		letterGradesS = new String[letterGrades.length];
		cutoffsS = new double[cutoffs.length];
		for (int i = 0; i < letterGrades.length; i++) {
			letterGradesS[i] = letterGrades[i];
		}
		for (int i = 0; i < cutoffs.length; i++) {
			cutoffsS[i] = cutoffs[i];
		}
	}

	@Override
	public double getCourseNumericGrade(String studentName) {
		double totalExam = 0;
		double rValue = 0;
		if (letterGradesS.length < 1) {
			Student s = students.get(studentName);
			for (int i = 1; i < s.the.size(); i++) {
				totalExam = this.getExamScore(studentName, i);
			}
			totalExam = totalExam / s.the.size();
			System.out.println(totalExam);
		} else {
			Student s = students.get(studentName);
			for (int i = 1; i < s.the.size(); i++) {
				totalExam = this.getExamScore(studentName, i);
			}
			totalExam = totalExam / s.the.size();
			System.out.println(totalExam);
			System.out.println(letterGradesS.length);
			for (int x = 0; x < letterGradesS.length; x++) {
				if (totalExam < cutoffsS[x]) {
					rValue = cutoffsS[x];
				}
			}
		}
		return rValue;
	}

	@Override
	public String getCourseLetterGrade(String studentName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCourseGrades() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getMaxScore(int examId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getMinScore(int examId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAverageScore(int examId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void saveManager(Manager manager, String fileName) {
		File file = new File(fileName);
		ObjectOutputStream output = null;
		try {
			output = new ObjectOutputStream(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			output.writeObject(manager);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Manager restoreManager(String fileName) {
		File file = new File(fileName);
		Manager systemManager = null;

		if (!file.exists()) {
			return new SystemManager();
		} else {
			ObjectInputStream input = null;
			try {
				input = new ObjectInputStream(new FileInputStream(file));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				systemManager = (SystemManager) input.readObject();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return systemManager;
		}
	}

	private double[] getPossiblePoints(int examId) {
		Exam e = exams.get(examId);
		double[] ppp = new double[e.getNumOfQuestions()];
		Collection<Double> po = e.returnPointsTree().values();
		java.util.Iterator<Double> pe = po.iterator();
		int z = 0;
		while (pe.hasNext()) {
			ppp[z] = pe.next();
			z++;
		}
		return ppp;
	}

	private String[] getAnswerKey(int examId) {
		Exam e = exams.get(examId);
		String[] aaa = new String[e.getNumOfQuestions()];
		Collection<String> b = e.returnAnsTree().values();
		java.util.Iterator<String> d = b.iterator();
		int j = 0;
		while (d.hasNext()) {
			aaa[j] = d.next();
			j++;
		}
		return aaa;
	}

	private String[] getStudentAnswerKey(String studentName, int examId) {
		Exam e = exams.get(examId);
		Student s = students.get(studentName);
		String[] sss = new String[e.getNumOfQuestions()];
		Collection<String> a = s.getStudentMap(examId).values();
		java.util.Iterator<String> c = a.iterator();
		int i = 0;
		while (c.hasNext()) {
			sss[i] = c.next();
			i++;
		}
		return sss;
	}

	private double getFBPoints(String studentName, int examId) {
		return totalFBPoints;
	}

}
