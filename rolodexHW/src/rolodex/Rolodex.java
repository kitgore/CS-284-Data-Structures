package rolodex;

import java.util.ArrayList;

//NAME: BENJAMIN GRIEPP
//PLEDGE: I PLEDGE MY HONOR I HAVE ABIDED BY THE STEVENS HONOR SYSTEM

public class Rolodex {
	private Entry cursor;
	private final Entry[] index;

	// Constructor

	Rolodex() {
		char[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		
		index = new Entry[26];
		Entry prev = null;
		Entry curr;
		for(int i = 0; i < letters.length; i++) {
			curr = new Separator(prev, null, letters[i]);
			index[i] = curr;
			prev = curr;
		}
		index[0].prev = index[25];
		for(int i = letters.length - 2; i >= 0; i--) {
			index[i].next = index[i+1];
		}
		index[25].next = index[0];
	}

	public Boolean contains(String name) {
		if(name == null) {
			throw new IllegalArgumentException("contains: name is null");
		}
		if(!name.matches("[a-zA-Z]+")) {
			throw new IllegalArgumentException("contains: name contains non alphabetical characters or is empty");
		}
		for(int i = 0; i < index.length; i++) {
			if(index[i].getName().equals(Character.toString(Character.toUpperCase(name.charAt(0))))){
				Entry current = index[i];
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
		int output = 0;
		initializeCursor();
		while(cursor.next != index[0]) {
			output += cursor.size();
			nextEntry();
		}
		return output;
	}

	public ArrayList<String> lookup(String name) {
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
				Entry current = index[i];
				while(!current.next.isSeparator() && (name.compareTo(current.next.getName()) >= 0)){
					Card currCard = (Card) current.next;
					if(name.equals(current.next.getName()) && cell.equals(currCard.getCell())){
						throw new IllegalArgumentException("addCard: duplicate entry");						
					}
					current = current.next;
				}
				Entry temp = current.next;
				current.next = new Card(current, temp, name, cell);
				current.next.next.prev = current.next;
			}
		}
	}

	public void removeCard(String name, String cell) {
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
		throw new IllegalArgumentException("removeCard: cell for that name does not exist");
		}
	}
	
	public void removeAllCards(String name) {
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

	// Cursor operations

	public void initializeCursor() {
		cursor = index[0];
	}

	public void nextSeparator() {
		while(!cursor.next.isSeparator()) {
			cursor = cursor.next;
		}
		cursor = cursor.next;
	}

	public void nextEntry() {
		cursor = cursor.next;
	}

	public String currentEntryToString() {
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
