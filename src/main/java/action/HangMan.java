package action;

import java.util.ArrayList;
import java.util.List;

public class HangMan {
	private static final char SEPARATOR = '_';

	private int points;

	private String wordToGuess;
	private String word;
	private List<Character> misses = new ArrayList<>();

	public void setWordToGuess(String word) {
		this.wordToGuess = word;
	}

	public String getWordToGuess() {
		return this.wordToGuess;
	}

	public void setWord(String word) {
		String guess = "";
		for (int i = 0; i < word.length(); i++)
			guess += SEPARATOR;

		this.word = guess;
	}

	public String getWord() {
		return this.word;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getPoints() {
		return this.points;
	}

	public void setMisses(List<Character> misses) {
		this.misses = misses;
	}

	public List<Character> getMisses() {
		return this.misses;
	}

	public boolean verifyLetter(char c) {
		if (Character.isAlphabetic(c))
			return true;
		return false;
	}

	public List<Integer> countChar(String str, char c) {
		List<Integer> positions = new ArrayList<>();

		for (int i = 0; i < str.length(); i++)
			if (str.charAt(i) == c)
				positions.add(i);

		return positions;
	}

	public String replaceChar(String str, char c, int index) {
		return str.substring(0, index) + c + str.substring(index + 1);
	}

	public boolean verifyWinning(String str) {
		List<Integer> countChar = countChar(str, SEPARATOR);

		if (countChar.isEmpty())
			return true;
		return false;
	}

	public String setUpMisses(List<Character> misses) {
		String str = "";

		for (int i = 0; i < misses.size(); i++) {
			if (i == 0)
				str += misses.get(i).toString();
			else
				str += ", " + misses.get(i).toString();
		}

		return str;
	}

}