// TO DO: add your implementation and JavaDocs.

import java.util.Collection;
import java.util.Iterator;

/**
 * The base data structure for the simulation. 
 * Manages the positions of elements.
 * Can be resized by factors of 2.
 * @author Nicholas Szymonik
 * @param <T> The type of object held by the DynamicArray
 */
public class DynamicArray<T> implements Iterable<T> {
	/**
	 * The Default Initial Capacity.
	 */
	private static final int INITCAP = 2; //default initial capacity
	/**
	 * The object that stores values.
	 */
	private T[] storage; //underlying array, you MUST use this for credit (do not change the name or type)
	/**
	 * The actual number of elements in the array. 
	 * This one's mine! 
	 */
	private int trueSize; 
	/**
	 * Constructor for the Dynamic Array.
	 * Uses default initial capacity.
	 */
	@SuppressWarnings("unchecked")
	public DynamicArray(){
		storage = (T[]) new Object[INITCAP];
		trueSize = 0;
	}

	/**
	 * Constructor for the Dynamic Array.
	 * @param initCapacity the initial capacity of this array.
	 */
	@SuppressWarnings("unchecked")
	public DynamicArray(int initCapacity) {
		if (initCapacity < 1) {
			throw new IllegalArgumentException("Capacity cannot be zero or negative.");
		}
		storage = (T[]) new Object[initCapacity];
		trueSize = 0;
		//constructor
		
		// The initial capacity of the storage should be initCapacity
		
		// Throw IllegalArgumentException if initCapacity < 1
		// Use this _exact_ error message for the exception
		// (quotes are not part of the message):
		//    "Capacity cannot be zero or negative."
	}

	/**
	 * Returns the actual number of elements in the array.
	 * @return the current number of elements
	 */
	public int size() {	
		// Report the current number of elements.
		// O(1)
		return trueSize;
	}  
	
	/**
	 * Returns the current size of the storage array.
	 * @return length of storage
	 */
	public int capacity() {
		// Report the max number of elements before expansion.
		// O(1)
		
		return storage.length;
	}
	
	/**
	 * Changes an element currently in storage to a different value. Returns the old value.
	 * @param index the index to set the new value to.
	 * @param value the new value to set.
	 * @return the original value at storage[index]
	 */
	public T set(int index, T value) {
		if ( index < 0 || index >= trueSize) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!"); 
		}
		T temp = storage[index];
		storage[index] = value;
		return temp;
		// Change the item at the given index to be the given value.
		// Return the old item at that index.
		// Note: You cannot add new items with this method.
		
		// O(1)
		
		// For an invalid index, throw an IndexOutOfBoundsException
		// Use this code to produce the correct error message for
		// the exception (do not use a different message):
		//	  "Index " + index + " out of bounds!"
	}

	/**
	 * Gets the value at a specified index in storage.
	 * @param index the index to collect the value from. 
	 * @return the item at index in storage.
	 */
	public T get(int index) {
		if ( index < 0 || index >= trueSize) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!"); 
		}
		return storage[index];
		// Return the item at the given index
		
		// O(1)
		
		// Use the exception (and error message) described in set()
		// for invalid indicies.
	}
	
	/**
	 * Adds a value to the end of storage. If storage is at its limit, doubles the storage capacity.
	 * @param value the value to append.
	 * @return true if nothing crashes
	 */
	@SuppressWarnings("unchecked")
	public boolean add(T value) {
		// Append an element to the end of the list and return true.

		// Double the capacity if no space available.

		// Amortized O(1)
		if (trueSize == storage.length) {
			T[] newStorage = (T[]) new Object[storage.length * 2];
			for (int i = 0; i < storage.length; ++i) {
				newStorage[i] = storage[i];
			}
			storage = newStorage;
		}
		storage[trueSize++] = value;
		return true;
	}
	
	/**
	 * Adds an item at the given index, shifting preexisting elements to the right.
	 * @param index The index to add the value at.
	 * @param value The value to add at the index.
	 */
	@SuppressWarnings("unchecked")
	public void add(int index, T value) {
		// Insert the given value at the given index. Shift elements if needed,  
		// double capacity if no space available, throw an exception if you cannot
		// insert at the given index. You _can_ append items with this method.
		
		// For the exception, use the same exception and message as set() and
		// get()... however remember that the condition of the exception is
		// different (different indexes are invalid).
		
		// O(N) where N is the number of elements currently in the list
		
		if ( index < 0 || index > trueSize) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!"); 
		}
		
		if (trueSize == storage.length) {
			T[] newStorage = (T[]) new Object[storage.length * 2];
			for (int i = 0; i < storage.length; ++i) {
				newStorage[i] = storage[i];
			}
			storage = newStorage;
		}
		
		for (int i = trueSize++; i > index; --i) {
			storage[i] = storage[i-1];
		}
		
		storage[index] = value;
		
	}
	
	/**
	 * Removes an item at the given index in storage, returns it.
	 * @param index the index from which to remove the item
	 * @return The removed item
	 */
	@SuppressWarnings("unchecked")
	public T remove(int index) {
		// Remove and return the element the given index. Shift elements
		// to remove the gap. Throw an exception when there is an invalid
		// index (see set(), get(), etc. above).
		
		// Halve capacity of the storage if the number of elements falls
		// below 1/3 of the capacity.
		
		// O(N) where N is the number of elements currently in the list
		
		if ( index < 0 || index >= trueSize) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!"); 
		}
		
		T temp = storage[index];
		
		--trueSize;
		for (int i = index; i < trueSize; ++i) {
			storage[i] = storage[i+1];
		}
		
		//A rather clunky method of doing ceiling integer division.
		if (trueSize < (int) Math.ceil(storage.length / 3.0)) {
			T[] newStorage = (T[]) new Object[Math.max(storage.length / 2, 1)];
			for (int i = 0; i < trueSize; ++i) {
				newStorage[i] = storage[i];
			}
			storage = newStorage;
		}
		
		return temp;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Iterator<T> iterator() {
		// Uses an anonymous class style, complete the iterator code
		// below. Note that this uses the "diamond syntax" which is
		// only available for nested classes from Java 9 forward.
		// If you get an error on the next line you can add a <T>
		// betwen the <> or you can (and should) update your 
		// version of the JDK.
		
		return new Iterator<>() {
			private int position = 0;
			//instance variables here
			//only _required_ methods are outlined below
			//the interface also has some optional methods
			//you may implement if you find them helpful
			
			/**
			 * {@inheritDoc}
			 */
			public T next() {
				
				//your code here
				return storage[position++];
			}
			
			/**
			 * {@inheritDoc}
			 */
			public boolean hasNext() {
				//your code here
				return (position < trueSize);
			}
		};
	}

	//******************************************************
	//*******     BELOW THIS LINE IS TESTING CODE    *******
	//*******      Edit it as much as you'd like!    *******
	//******************************************************
	
	/**
	 * Prints description of the Dynamic Array.
	 * @return Description of object capacity, size, and elements.
	 */
	public String toString() {
		//This method is provided for debugging purposes
		//(use/modify as much as you'd like), it just prints
		//out the list for easy viewing.
		StringBuilder s = new StringBuilder("Dynamic array with " + size()
			+ " items and a capacity of " + capacity() + ":");
		for (int i = 0; i < size(); i++) {
			s.append("\n  ["+i+"]: " + get(i));
		}
		return s.toString();
		
	}
	
	//JavaDoc note: How do you document a main? See Simulation.java for an example
	/**
	 * Tests the class.
	 * @param args not used
	 */
	public static void main(String args[]){
		//These are _sample_ tests. If you're seeing all the "yays" that's
		//an excellend first step! But it might not mean your code is 100%
		//working... You may edit this as much as you want, so you can add
		//own tests here, modify these tests, or whatever you need!
		
		/*DynamicArray<Integer> ida = new DynamicArray<>();
		if ((ida.size() == 0) && (ida.capacity() == 2)){
			System.out.println("Yay 1");
		}

		boolean ok = true;
		for (int i=0; i<3;i++)
			ok = ok && ida.add(i*5);
		
		if (ok && ida.size()==3 && ida.get(2) == 10 && ida.capacity() == 4 ){
			System.out.println("Yay 2");
		}
		
		ida.add(1,-10);
		ida.add(4,100);
		if (ida.set(1,-20)==-10 && ida.get(2) == 5 && ida.size() == 5 
			&& ida.capacity() == 8 ){
			System.out.println("Yay 3");
		}
		
		
		if (ida.remove(0) == 0 && ida.remove(0) == -20 && ida.remove(2) == 100 
			&& ida.size() == 2 && ida.capacity() == 4 ){
			System.out.println("Yay 4");
		}
		
		//Uncomment this after doing the iterator for testing
		System.out.print("Printing values: ");
		for(Integer i : ida) {
			System.out.print(i);
			System.out.print(" ");
		}*/
		DynamicArray<String> jeff = new DynamicArray<String>(1);
		jeff.add("My");
		jeff.remove(0);
		System.out.println(jeff.capacity());
		System.out.println(jeff.capacity());
		jeff.add("Jeff Mama");
		jeff.add("Big");
		jeff.add("Gae");
		System.out.println(jeff.capacity());
		System.out.println(jeff.size());

		for (String s : jeff) {
			System.out.print(s + " ");
		}
	}
}