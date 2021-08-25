package rolodex;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RolodexTest {
	
	@Test
	void testRolodex() {
		Rolodex r = new Rolodex();
		assertEquals(r.size(), 0);
	}
	
	@Test
	void testSize() {
		Rolodex r = new Rolodex();
		r.addCard("Evan", "123");
		r.addCard("Paul", "456");
		assertEquals(r.size(), 2);
	}
	
	@Test
	void testContains() {
		Rolodex r = new Rolodex();
		r.addCard("Evan", "123");
		assertTrue(r.contains("Evan"));
	}
	
	@Test
	void testAdd() {
		Rolodex r = new Rolodex();
		r.addCard("Evan", "123");
		
		Rolodex j = new Rolodex();
		
		assertNotEquals(r.toString(), j.toString());
	}

	@Test
	void testRemoveCard() {
		Rolodex r = new Rolodex();
		r.addCard("Evan", "123");
		r.addCard("Paul", "456");
		r.removeCard("Evan", "123");
		
		Rolodex j = new Rolodex();
		j.addCard("Paul", "456");
		
		assertEquals(r.toString(), j.toString());
	}
	
	@Test
	void testRemoveAllCards() {
		Rolodex r = new Rolodex();
		r.addCard("Evan", "123");
		r.addCard("Evan", "789");
		r.addCard("Paul", "456");
		r.removeAllCards("Evan");
		
		Rolodex j = new Rolodex();
		j.addCard("Paul", "456");
		
		assertEquals(r.toString(), j.toString());
	}
	
	@Test
	void testLookup() {
		Rolodex r = new Rolodex();
		r.addCard("Evan", "123");
		r.addCard("Evan", "456");
		
		ArrayList<String> testAL = new ArrayList<String>();
		testAL.add("123");
		testAL.add("456");

		
		assertEquals(r.lookup("Evan").toString(), testAL.toString());
	}
	
	@Test
	void testToString() {
		Rolodex r = new Rolodex();
		r.addCard("Evan", "123");
		r.addCard("Paul", "456");
		r.removeCard("Evan", "123");
		
		Rolodex j = new Rolodex();
		j.addCard("Paul", "456");
		
		assertEquals(r.toString(), j.toString());
	}
	
	@Test
	void testInitializeCursor() {
		Rolodex r = new Rolodex();
		r.initializeCursor();
		
		assertEquals(r.currentEntryToString(), "Separator A");
	}
	
	@Test
	void testNextSeparator() {
		Rolodex r = new Rolodex();
		r.initializeCursor();
		r.nextSeparator();
		
		assertEquals(r.currentEntryToString(), "Separator B");
	}
	
	@Test
	void testNextEntry() {
		Rolodex r = new Rolodex();
		r.addCard("Adam", "123");
		r.initializeCursor();
		r.nextEntry();
		
		assertEquals(r.currentEntryToString(), "Name: Adam, Cell: 123");
	}
	
	@Test
	void testCurrentEntryToString() {
		Rolodex r = new Rolodex();
		r.initializeCursor();
		
		assertEquals(r.currentEntryToString(), "Separator A");
	}
	
	//THROWS
	
	@Test
	void testAddNameNull() {
		Rolodex r = new Rolodex();
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.addCard(null, "123");});
	}
	
	@Test
	void testAddCellNull() {
		Rolodex r = new Rolodex();
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.addCard("Evan", null);});
	}
	
	@Test
	void testAddNameInvalid() {
		Rolodex r = new Rolodex();
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.addCard("Evan1", "123");});
	}
	
	@Test
	void testRemoveAllCardsNameNull() {
		Rolodex r = new Rolodex();
		r.addCard("Evan", "123");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.removeAllCards(null);});
	}
	
	@Test
	void testRemoveAllCardsNameInvalid() {
		Rolodex r = new Rolodex();
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.removeAllCards("Evan1");});
	}
	
	@Test
	void testRemoveAllCardsNotExist() {
		Rolodex r = new Rolodex();
		r.addCard("Evan", "123");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.removeAllCards("Paul");});
	}
	
	@Test
	void testRemoveCardNameNull() {
		Rolodex r = new Rolodex();
		r.addCard("Evan", "123");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.removeCard(null,"123");});
	}
	
	@Test
	void testRemoveCardCellNull() {
		Rolodex r = new Rolodex();
		r.addCard("Evan", "123");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.removeCard("Evan",null);});
	}
	
	@Test
	void testRemoveCardNameInvalid() {
		Rolodex r = new Rolodex();
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.removeCard("Evan1","123");});
	}
	
	@Test
	void testRemoveCardNotExist() {
		Rolodex r = new Rolodex();
		r.addCard("Evan", "123");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.removeCard("Paul","123");});
	}
	
	@Test
	void testLookupNameNull() {
		Rolodex r = new Rolodex();
		r.addCard("Evan", "123");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.lookup(null);});
	}
	
	@Test
	void testLookupNameInvalid() {
		Rolodex r = new Rolodex();
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.lookup("Evan1");});
	}
	
	@Test
	void testLookupNotExist() {
		Rolodex r = new Rolodex();
		r.addCard("Evan", "123");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.lookup("Paul");});
	}
	
	@Test
	void testContainsNameNull() {
		Rolodex r = new Rolodex();
		r.addCard("Evan", "123");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.contains(null);});
	}
	
	@Test
	void testContainsNameInvalid() {
		Rolodex r = new Rolodex();
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.contains("Evan1");});
	}
	

}
