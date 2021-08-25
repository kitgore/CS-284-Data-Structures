package anagramsHW;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AnagramsTest {
	
	@Test
	void testHashLetter() {
		Anagrams a = new Anagrams();
		int out = (int) a.myHashCode("a");
		assertEquals(out, 2);
	}
	@Test
	void testHashWord() {
		Anagrams a = new Anagrams();
		int out = (int) a.myHashCode("ab");
		assertEquals(out, 6);
	}

	@Test
	void testAnagramInit() {
		Anagrams a = new Anagrams();
		assertEquals(a.anagramTable.size(), 0);
	}
	@Test
	void testLetterTable() {
		Anagrams a = new Anagrams();
		assertEquals(a.letterTable.size(), 26);
	}
	
	@Test
	void testAddWord() {
		Anagrams a = new Anagrams();
		a.addWord("test");
		assertEquals(a.anagramTable.size(), 1);
	}
	
	@Test
	void testAddDupWord() {
		Anagrams a = new Anagrams();
		a.addWord("test");
		assertThrows(IllegalArgumentException.class, () -> { a.addWord("test");});
	}
	
	@Test
	void testKey() {
		Anagrams a = new Anagrams();
		a.addWord("a");
		assertEquals(a.anagramTable.get((long) 2).get(0), "a");
	}
	
	@Test
	void testMaxWord() {
		Anagrams a = new Anagrams();
		a.addWord("test");
		a.addWord("hello");
		a.addWord("hi");
		a.addWord("estt");
		a.addWord("sett");
		assertEquals(a.getMaxEntries().toString(), "[3715217=[test, estt, sett]]");
	}

}
