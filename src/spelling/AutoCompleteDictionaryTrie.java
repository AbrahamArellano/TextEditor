package spelling;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * An trie data structure that implements the Dictionary and the
 * AutoComplete ADT
 * 
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements Dictionary, AutoComplete {

	private TrieNode root;
	private int size;

	public AutoCompleteDictionaryTrie() {
		root = new TrieNode();
	}

	/**
	 * Insert a word into the trie. For the basic part of the
	 * assignment (part 2), you should ignore the word's case. That
	 * is, you should convert the string to all lower case as you
	 * insert it.
	 */
	public boolean addWord(String word) {
		// TODO: DONE - Implement this method.
		if (word == null) {
			return false;
		}
		String wordToLowerCase = word.toLowerCase();
		final char[] charArray = wordToLowerCase.toCharArray();
		TrieNode curr = root;
		for (int i = 0; i < charArray.length; i++) {
			final Character charObj = Character.valueOf(charArray[i]);
			TrieNode child = curr.getChild(charObj);
			if (child == null) {
				child = curr.insert(charObj);
			}
			curr = child;
		}

		if (curr != null && !curr.endsWord()) {
			curr.setEndsWord(true);
		} else {
			return false;
		}

		size++;
		return true;
	}

	/**
	 * Return the number of words in the dictionary. This is NOT
	 * necessarily the same as the number of TrieNodes in the trie.
	 */
	public int size() {
		// TODO: DONE - Implement this method
		return size;
	}

	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String word) {
		// TODO: DONE - Implement this method
		if (word == null) {
			return false;
		}
		TrieNode curr = extractStemFromWord(word);
		return curr != null ? curr.endsWord() : false;
	}

	/**
	 * @param charArray
	 * @return
	 */
	private TrieNode extractStemFromWord(final String word) {
		String wordToLowerCase = word.toLowerCase();
		final char[] charArray = wordToLowerCase.toCharArray();
		TrieNode curr = root;
		for (int i = 0; i < charArray.length; i++) {
			final Character charObj = Character.valueOf(charArray[i]);
			TrieNode child = curr.getChild(charObj);
			if (child == null) {
				return null;
			}
			curr = child;

		}
		return curr;
	}

	/**
	 * Returns up to the n "best" predictions, including the word
	 * itself, in terms of length If this string is not in the trie,
	 * it returns null.
	 * 
	 * @param text
	 *            The text to use at the word stem
	 * @param n
	 *            The maximum number of predictions desired.
	 * @return A list containing the up to n best predictions
	 */
	@Override
	public List<String> predictCompletions(String prefix, int numCompletions) {
		// TODO: DONE - Implement this method
		// This method should implement the following algorithm:

		// 1. Find the stem in the trie. If the stem does not appear
		// in the trie, return an
		// empty list
		TrieNode stem = extractStemFromWord(prefix);
		if (stem == null || numCompletions < 1) {
			return new ArrayList<>();
		}

		// 2. Once the stem is found, perform a breadth first search
		// to generate completions
		// using the following algorithm:
		// Create a queue (LinkedList) and add the node that completes
		// the stem to the back
		// of the list.
		LinkedList<TrieNode> queue = new LinkedList<>();
		addAllChildTriesToQueue(stem, queue);
		// Create a list of completions to return (initially empty)
		LinkedList<String> listOfCompletions = new LinkedList<>();
		if (stem.endsWord()) {
			listOfCompletions.add(stem.getText());
		}
		// While the queue is not empty and you don't have enough
		// completions:
		while (!queue.isEmpty() && listOfCompletions.size() < numCompletions) {
			// remove the first Node from the queue
			final TrieNode firstTrie = queue.removeFirst();
			// If it is a word, add it to the completions list
			if (firstTrie.endsWord()) {
				listOfCompletions.add(firstTrie.getText());
			}
			// Add all of its child nodes to the back of the queue
			addAllChildTriesToQueue(firstTrie, queue);
		}
		// Return the list of completions
		return listOfCompletions;
	}

	/**
	 * Add all child tries to queue.
	 * 
	 * @param root
	 * @param queue
	 */
	private void addAllChildTriesToQueue(TrieNode root,
	        LinkedList<TrieNode> queue) {
		for (Character c : root.getValidNextCharacters()) {
			queue.addLast(root.getChild(c));
		}
	}

	// For debugging
	public void printTree() {
		printNode(root);
	}

	/** Do a pre-order traversal from this node down */
	public void printNode(TrieNode curr) {
		if (curr == null)
			return;

		System.out.println(curr.getText());

		TrieNode next = null;
		for (Character c : curr.getValidNextCharacters()) {
			next = curr.getChild(c);
			printNode(next);
		}
	}

}