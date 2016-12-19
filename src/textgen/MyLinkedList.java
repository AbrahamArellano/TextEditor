package textgen;

import java.util.AbstractList;

/**
 * A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E>
 *            The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: DONE - Implement this method
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.setNext(tail);
		tail.setPrev(head);
		size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * 
	 * @param element
	 *            The element to add
	 */
	public boolean add(E element) {
		// TODO: DONE - Implement this method
		add(size(), element);
		return true;
	}

	/**
	 * Get the element at position index
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of bounds.
	 */
	public E get(int index) {
		// TODO: DONE - Implement this method.
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		LLNode<E> expected = getNode(index);

		return expected != null ? expected.getData() : null;
	}

	/**
	 * @param index
	 * @return
	 */
	private LLNode<E> getNode(int index) {
		LLNode<E> expected = null;
		LLNode<E> current = head;
		for (int i = 0; i < size; i++) {
			current = current.getNext();
			if (i == index && current != null) {
				expected = current;
				break;
			}
		}
		return expected;
	}

	/**
	 * Add an element to the list at the specified index
	 * 
	 * @param The
	 *            index where the element should be added
	 * @param element
	 *            The element to add
	 */
	public void add(int index, E element) {
		// TODO: DONE - Implement this method
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}

		LLNode<E> insertionNode = index == size ? tail : getNode(index);

		LLNode<E> newElement = new LLNode<E>(element);
		final LLNode<E> prev = insertionNode.getPrev();
		newElement.setPrev(prev);
		newElement.setNext(insertionNode);

		prev.setNext(newElement);
		insertionNode.setPrev(newElement);
		size++;
	}

	/** Return the size of the list */
	public int size() {
		// TODO: DONE - Implement this method
		return size;
	}

	/**
	 * Remove a node at the specified index and return its data
	 * element.
	 * 
	 * @param index
	 *            The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException
	 *             If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) {
		// TODO: DONE - Implement this method
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		final LLNode<E> nodeToRemove = getNode(index);
		final LLNode<E> prev = nodeToRemove.getPrev();
		final LLNode<E> next = nodeToRemove.getNext();

		prev.setNext(next);
		next.setPrev(prev);
		size--;
		return nodeToRemove.getData();
	}

	/**
	 * Set an index position in the list to a new element
	 * 
	 * @param index
	 *            The index of the element to change
	 * @param element
	 *            The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of bounds.
	 */
	public E set(int index, E element) {
		// TODO: DONE - Implement this method
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}

		final LLNode<E> node = getNode(index);
		E oldData = node.getData();
		node.setData(element);
		return oldData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		LLNode<E> current = head;
		for (int i = 0; i < size; i++) {
			current = current.getNext();
			sb.append(current.toString());
		}
		return sb.toString();
	}
}

class LLNode<E> {
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: DONE - Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) {
		this.data = e;
		this.prev = null;
		this.next = null;
	}

	/**
	 * @return the prev
	 */
	public LLNode<E> getPrev() {
		return prev;
	}

	/**
	 * @param prev
	 *            the prev to set
	 */
	public void setPrev(LLNode<E> prev) {
		this.prev = prev;
	}

	/**
	 * @return the next
	 */
	public LLNode<E> getNext() {
		return next;
	}

	/**
	 * @param next
	 *            the next to set
	 */
	public void setNext(LLNode<E> next) {
		this.next = next;
	}

	/**
	 * @return the data
	 */
	public E getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(E data) {
		this.data = data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[data=" + data + "]";
	}

}
