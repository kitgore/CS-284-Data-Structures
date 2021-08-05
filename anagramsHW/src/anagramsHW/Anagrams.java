package anagramsHW;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Benjamin Griepp
 * 
 * Grade:
 * 
 * Sorts words into hashtable by their unsorted characters, finds the largest set of anagrams in a dictionary
 * 
 * Sorting is done by assigning prime values to each letter and multiplying all values together to form a HashCode
 * Words are then put into hashtable with this HashCode that is unique to its set of letter
 * Largest hashtable is given as output as it contains the largest set of anagrams from the given dictionary
 * 
 * I pledge my honor I have abided by the Stevens Honor System
 */
public class Anagrams {
	final Integer[] primes =    {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};	
	final Character[] letters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	Map<Character,Integer> letterTable;
	Map<Long,ArrayList<String>> anagramTable;

	//Each letter is added to letterTable with a unique prime number
	public void buildLetterTable() {
		letterTable = new HashMap<>();
		for(int i = 0; i < primes.length; i++) {
			letterTable.put(letters[i], primes[i]);
		}
	}

	Anagrams() {
		buildLetterTable();
		anagramTable = new HashMap<Long,ArrayList<String>>();
	}

	/**
	 * @param s word to add to hashtable
	 */
	public void addWord(String s) {
		//Checks if anagramTable contains a hashtable corresponding to given word
		if(anagramTable.containsKey(myHashCode(s))) {
			
			//Checks for duplicate word and returns an error if true
			if((anagramTable.get(myHashCode(s))).contains(s)) {
				throw new IllegalArgumentException("addWord: duplicate value");
			}
			//Adds word to anagramTable if no duplicate
			(anagramTable.get(myHashCode(s))).add(s);
		}
		
		//anagramTable does not contain the word and creates new hashtable
		else {
			ArrayList<String> words = new ArrayList<String>();
			words.add(s);
			anagramTable.put(myHashCode(s), words);
		}
	}
	
	/**
	 * @param s string to be hashed
	 * @return hashcode nondependent on character order
	 */
	public long myHashCode(String s) {
		//Generates a unique hash recursively
		if(s.equals("")) {
			throw new IllegalArgumentException("myHashCode: empty string");
		}
		else if((s.substring(1,s.length())).equals("")) {
			//Returns corresponding prime if no letters remain to multiply with recursive calls
			return (long) letterTable.get(s.charAt(0));
		}
		//Returns corresponding prime of current letter multiply with a recursive call of myHashCode without current letter
		return ((long) letterTable.get(s.charAt(0))) * this.myHashCode(s.substring(1,s.length()));
	}
	
	
	//Takes file as input and does addWord function with each strLine as input
	public void processFile(String s) throws IOException {
		FileInputStream fstream = new FileInputStream(s);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		while ((strLine = br.readLine()) != null)   {
		  this.addWord(strLine);
		}
		br.close();
	}
	
	/** 
	 * @return the arraylist within the hashmap with the greatest size
	 */
	public ArrayList<Map.Entry<Long,ArrayList<String>>> getMaxEntries() {
		
		//current highest size hashtable (most anagrams)
		int max = 0;
		
		ArrayList<Map.Entry<Long,ArrayList<String>>> out = new ArrayList<Map.Entry<Long,ArrayList<String>>>();
		for(Map.Entry<Long,ArrayList<String>> entry: anagramTable.entrySet()) {
			
			//Current entry is equal to current max, entry is added to output ArrayList
			if(entry.getValue().size() == max) {
				out.add(entry);
			}
			
			//Current entry is greater than currenet max
			else if(entry.getValue().size() > max) {
				
				//max is updated to current entry
				max = entry.getValue().size();
				
				//out is cleared and entry is added
				out.clear();
				out.add(entry);
			}
		}
		return out;
	}
	
	public static void main(String[] args) {
		Anagrams a = new Anagrams();

		final long startTime = System.nanoTime();    
		try {
			a.processFile("words_alpha.txt");
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		ArrayList<Map.Entry<Long,ArrayList<String>>> maxEntries = a.getMaxEntries();
		final long estimatedTime = System.nanoTime() - startTime;
		final double seconds = ((double) estimatedTime/1000000000);
		System.out.println("Time: "+ seconds);
		System.out.println("List of max anagrams: "+ maxEntries);
	}
}
