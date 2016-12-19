package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * An implementation of the MTG interface that uses a list of lists.
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	private static final String SPACE = " ";

	// The list of words with their next words
	private List<ListNode> wordList;

	// The starting "word"
	private String starter;

	// The random number generator
	private Random rnGenerator;

	public MarkovTextGeneratorLoL(Random generator) {
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}

	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText) {
		// TODO: DONE - Implement this method
		sourceText = sourceText.replaceAll("[\\s]+", SPACE);
		String[] text = sourceText.split(SPACE);
		if (starter.isEmpty()) {
			starter = text[0];
		}
		String prevWord = starter;
		for (int i = 1; i < text.length; i++) {
			String w = text[i];
			addNextToWord(prevWord, w);
			prevWord = w;
		}
		if (prevWord != null) {
			addNextToWord(prevWord, starter);
		}
	}

	/**
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
		// TODO: DONE - Implement this method
		if (numWords == 0 || wordList.isEmpty()) {
			return "";
		}
		String currWord = starter;
		StringBuilder output = new StringBuilder();
		output.append(currWord);
		for (int genAmount = 1; genAmount < numWords; genAmount++) {
			ListNode storedWord = getListNodeByWord(currWord);
			final String randomNextWord = storedWord
			        .getRandomNextWord(rnGenerator);
			output.append(SPACE).append(randomNextWord);
			currWord = randomNextWord;
		}
		return output.toString();
	}

	// Can be helpful for debugging
	@Override
	public String toString() {
		String toReturn = "";
		for (ListNode n : wordList) {
			toReturn += n.toString();
		}
		return toReturn;
	}

	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText) {
		// TODO: DONE - Implement this method.
		wordList = new LinkedList<ListNode>();
		starter = "";
		train(sourceText);
	}

	// TODO: DONE - Add any private helper methods you need here.
	/**
	 * @param prevWord
	 * @param w
	 */
	private void addNextToWord(String prevWord, String w) {
		ListNode storedPrevWord = getListNodeByWord(prevWord);
		if (storedPrevWord == null) {
			storedPrevWord = new ListNode(prevWord);
			wordList.add(storedPrevWord);
		}
		storedPrevWord.addNextWord(w);
	}

	private ListNode getListNodeByWord(String w) {
		if (w != null) {
			for (ListNode node : wordList) {
				if (node != null && node.getWord().equals(w)) {
					return node;
				}
			}
		}
		return null;
	}

	/**
	 * This is a minimal set of tests. Note that it can be difficult
	 * to test methods/classes with randomized behavior.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// feed the generator a fixed random value for repeatable
		// behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString + "\n");
		gen.train(textString);
		System.out.println(gen);
		System.out.println("------------\n");
		gen.train("Zoologico en el Zoo. Zoo Zoologico en");
		System.out.println(gen);
	}

	// public static void main(String[] args) {
	// // feed the generator a fixed random value for repeatable
	// // behavior
	// MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new
	// Random(42));
	// String textString = "Hi There. Car is There.";
	// System.out.println(textString);
	// gen.train(textString);
	// // System.out.println(gen.generateText(0));
	// System.out.println(gen.generateText(20));
	//
	// }

}

/**
 * Links a word to the next words in the list You should use this
 * class in your implementation.
 */
class ListNode {
	// The word that is linking to the next words
	private String word;

	// The next words that could follow it
	private List<String> nextWords;

	ListNode(String word) {
		this.word = word;
		nextWords = new LinkedList<String>();
	}

	public String getWord() {
		return word;
	}

	public void addNextWord(String nextWord) {
		nextWords.add(nextWord);
	}

	public String getRandomNextWord(Random generator) {
		// TODO: DONE - Implement this method
		// The random number generator should be passed from
		// the MarkovTextGeneratorLoL class
		final int index = generator.nextInt(nextWords.size());
		return nextWords.get(index);
	}

	public String toString() {
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}

}
