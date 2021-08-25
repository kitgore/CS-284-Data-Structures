package rolodex;

import java.util.ArrayList;

/**
 * @author Benjamin Griepp
 * 
 * Grade: 100/100
 * 
 * Rolodex is a circular list of entries or "Cards" consisting of Separators and Entries in the structure of a double linked-list
 * All Cards have reference to the Card in front and behind of it allowing for circular traversal of Cards
 * Separators hold a letter value and are constructed in alphabetical order with the "Z" Separator behind the "A" Separator
 * Entries hold a name and number
 * Entries are inserted between Separators and other Entries alphabetically (Ex: "Chris" would be inserted between "C" and "D" Separators)
 * 
 * Entries with same name but different numbers are inserted next to each other
 * Entries with same name and same number are thrown a duplicate entry exception
 * 
 * Includes functionality for:
 * 
 * size()                               - returns number of Cards within Rolodex
 * contains(String name)                - returns boolean if name is within Rolodex efficiently (only checks cards that have same first letter)
 * lookup(String name)                  - returns an ArrayList of all numbers for name
 * addCard(String name, String cell)    - adds a new Card with specified info
 * removeCard(String name, String cell) - removes specified Card
 * removeAllCards(String name)          - removes all Cards with specified name
 * 
 * Includes a Cursor to browse Rolodex:
 * 
 * initializeCursor()   - builds Cursor and sets to Separator "A"
 * nextSeparator()      - sets Cursor to next Separator
 * nextEntry()          - sets Cursor to next Entry (Separator or Card)
 * currentEntryToString - returns string of Cursor position
 * 
 * I pledge my honor I have abided by the Stevens Honor System
 */

public class Rolodex {
	private Entry cursor;
	private final Entry[] index;

	Rolodex() {
	//Construct empty Rolodex

		char[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		
		index = new Entry[26];
		Entry prev = null;
		Entry curr;
		for(int i = 0; i < letters.length; i++) {
		//Constructs Separators with reference to previous Separators
			curr = new Separator(prev, null, letters[i]);
			index[i] = curr;
			prev = curr;
		}
		index[0].prev = index[25];
		for(int i = letters.length - 2; i >= 0; i--) {
		//Sets next references for all Separators
			index[i].next = index[i+1];
		}
		index[25].next = index[0];
	}

	public Boolean contains(String name) {
	//Determines if a Card is within Rolodex efficiently (only checks cards that have same first letter)

		if(name == null) {
			//Null name check
			throw new IllegalArgumentException("contains: name is null");
		}
		if(!name.matches("[a-zA-Z]+")) {
			//Non alphabetical name check
			throw new IllegalArgumentException("contains: name contains non alphabetical characters or is empty");
		}
		for(int i = 0; i < index.length; i++) {
			if(index[i].getName().equals(Character.toString(Character.toUpperCase(name.charAt(0))))){
				Entry current = index[i];
				//Sets current to corresponding first letter Separator

				while(!current.next.isSeparator()){
					if(name.equals(current.next.getName())){
						return true;
					}
					current = current.next;
				}
				return false;
			}
		}
		return false;
	}
	
	public int size() {
	//Returns number of Cards within Rolodex

		int output = 0;
		initializeCursor();
		while(cursor.next != index[0]) {
			output += cursor.size();
			nextEntry();
		}
		return output;
	}

	public ArrayList<String> lookup(String name) {
	//Returns an ArrayList of all numbers for name

		if(!contains(name)) {
			throw new IllegalArgumentException("lookup: name not found");
		}
		if(name == null) {
			throw new IllegalArgumentException("lookup: name is null");
		}
		if(!name.matches("[a-zA-Z]+")) {
			throw new IllegalArgumentException("lookup: name contains non alphabetical characters or is empty");
		}
		ArrayList<String> cellList = new ArrayList<String>();
		for(int i = 0; i < index.length; i++) {
			if(index[i].getName().equals(Character.toString(Character.toUpperCase(name.charAt(0))))){
				Entry current = index[i];
				while(!current.next.isSeparator()){
					if(name.equals(current.next.getName())){
						Card currCell = (Card) current.next;
						cellList.add(currCell.getCell());
					}
					current = current.next;
				}
			}
		}
		return cellList;
	}


	public String toString() {
	//Returns String of whole Rolodex
		Entry current = index[0];

		StringBuilder b = new StringBuilder();
		while (current.next!=index[0]) {
			b.append(current.toString()+"\n");
			current=current.next;
		}
		b.append(current.toString()+"\n");		
		return b.toString();
	}


	public void addCard(String name, String cell) {
	//Adds a new Card with specified info

		if(name == null) {
			throw new IllegalArgumentException("addCard: name is null");
		}
		if(cell == null) {
			throw new IllegalArgumentException("addCard: cell is null");
		}
		if(!name.matches("[a-zA-Z]+")) {
			throw new IllegalArgumentException("addCard: name contains non alphabetical characters or is empty");
		}
		for(int i = 0; i < index.length; i++) {
			if(index[i].getName().equals(Character.toString(Character.toUpperCase(name.charAt(0))))){
			//Stop when index is same as first letter of name

				Entry current = index[i];
				while(!current.next.isSeparator() && (name.compareTo(current.next.getName()) >= 0)){
				//Check if next Entry is a Separator or greater String value (further alphabetically)

					Card currCard = (Card) current.next;
					if(name.equals(current.next.getName()) && cell.equals(currCard.getCell())){
						throw new IllegalArgumentException("addCard: duplicate entry");						
					}
					current = current.next;
				}
				//Creates Card with prev and next reference and references for prev/next Cards
				Entry temp = current.next;
				current.next = new Card(current, temp, name, cell);
				current.next.next.prev = current.next;
			}
		}
	}

	public void removeCard(String name, String cell) {
	//Removes specified Card

		if(name == null) {
			throw new IllegalArgumentException("removeCard: name is null");
		}
		if(cell == null) {
			throw new IllegalArgumentException("removeCard: cell is null");
		}
		if(!name.matches("[a-zA-Z]+")) {
			throw new IllegalArgumentException("removeCard: name contains non alphabetical characters or is empty");
		}
		if(!contains(name)) {
			throw new IllegalArgumentException("removeCard: name does not exist");
		}
		Boolean addTF = false;
		for(int i = 0; i < index.length; i++) {
			if(index[i].getName().equals(Character.toString(Character.toUpperCase(name.charAt(0))))){
				Entry current = index[i];
				while(!current.next.isSeparator()){
					if(name.equals(current.next.getName())){
						Card currentCard = (Card) current.next;
						if(cell.equals(currentCard.getCell())) {
						//Change references that skip desired Card

							current.next.next.prev = current.next.prev;
							current.next = current.next.next;
							addTF = true;
							break;
						}
					}
					current = current.next;
				}
			}
		}
		if(!addTF) {
		//Throw exception if Card not removed
		throw new IllegalArgumentException("removeCard: cell for that name does not exist");
		}
	}
	
	public void removeAllCards(String name) {
	//removes all Cards with specified name

		if(name == null) {
			throw new IllegalArgumentException("removeAllCards: name is null");
		}
		if(!name.matches("[a-zA-Z]+")) {
			throw new IllegalArgumentException("removeAllCards: name contains non alphabetical characters or is empty");
		}
		if(!contains(name)){
			throw new IllegalArgumentException("removeAllCards: name does not exist");
		}
		for(int i = 0; i < index.length; i++) {
			if(index[i].getName().equals(Character.toString(Character.toUpperCase(name.charAt(0))))){
				Entry current = index[i];
				while(!current.next.isSeparator()){
					if(name.equals(current.next.getName())){
					//Change references that skip desired Card until next Card is Separator
							current.next.next.prev = current.next.prev;
							current.next = current.next.next;
						}
					else {
					current = current.next;
					}
					}
				}
			}
		}

//Cursor Operations

	public void initializeCursor() {
	//builds Cursor and sets to Separator "A"
		cursor = index[0];
	}

	public void nextSeparator() {
	//sets Cursor to next Separator
		while(!cursor.next.isSeparator()) {
			cursor = cursor.next;
		}
		cursor = cursor.next;
	}

	public void nextEntry() {
	//sets Cursor to next Entry (Separator or Card)
		cursor = cursor.next;
	}

	public String currentEntryToString() {
	//returns string of Cursor position
		return cursor.toString();
	}


	public static void main(String[] args) {

		Rolodex r = new Rolodex();

		r.addCard("Chloe", "123");
		r.addCard("Chad", "23");
		r.addCard("Cris", "3");
		r.addCard("Cris", "4");
		r.addCard("Cris", "5");
		r.addCard("Maddie", "23");
		r.addCard("Z", "123");
		r.addCard("ZZ", "123");
		r.addCard("z", "321");
		
		//r.removeCard("Cris", "5");

		System.out.println(r);
		System.out.println(r.lookup("Cris"));

		System.out.println(r.contains("Albert"));

		r.removeAllCards("Cris");

		System.out.println(r);

		r.removeAllCards("Chad");
		r.removeAllCards("Chloe");

		r.addCard("Chloe", "123");
		r.addCard("Chad", "23");
		r.addCard("Cris", "3");
		r.addCard("Cris", "4");

		System.out.println(r);
	}

}
