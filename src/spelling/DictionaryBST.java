package spelling;

import java.util.TreeSet;

/**
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class DictionaryBST implements Dictionary {
	private TreeSet<String> dict;

	// TODO: DONE - Implement the dictionary interface using a
	// TreeSet.
	// You'll need a constructor here
	public DictionaryBST() {
		dict = new TreeSet<>();
	}

	/**
	 * Add this word to the dictionary. Convert it to lowercase first
	 * for the assignment requirements.
	 * 
	 * @param word
	 *            The word to add
	 * @return true if the word was added to the dictionary (it wasn't
	 *         already there).
	 */
	public boolean addWord(String word) {
		// TODO: DONE - Implement this method
		if (word == null) {
			return false;
		}
		String wordLowerCase = word.toLowerCase();
		if (!isWord(wordLowerCase)) {
			return dict.add(wordLowerCase);
		}
		return false;
	}

	/** Return the number of words in the dictionary */
	public int size() {
		// TODO: DONE - Implement this method
		return dict.size();
	}

	/** Is this a word according to this dictionary? */
	public boolean isWord(String s) {
		// TODO: DONE - Implement this method
		if (s == null) {
			return false;
		}
		return dict.contains(s.toLowerCase());
	}

}
