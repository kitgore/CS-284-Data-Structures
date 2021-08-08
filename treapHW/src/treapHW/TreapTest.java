package treapHW;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TreapTest {

	@Test
	void testAdd() {
		Treap test = new Treap();
		test.add(10, 10);
		Treap test2 = new Treap();
		test2.add(10, 10);
		assertEquals(test.toString(), test2.toString());
	}
	
	@Test
	void testAddDuplicate() {
		Treap test = new Treap();
		test.add(10, 10);
		test.add(10,10);
		Treap test2 = new Treap();
		test2.add(10, 10);
		test2.add(11,11);
		assertNotEquals(test.toString().length(), test2.toString().length());
	}
	
	@Test
	void testFind() {
		Treap test = new Treap();
		test.add(9,9);
		test.add(10,10);
		test.add(11,11);
		test.add(12,12);
		test.add(13,13);
		assertTrue(test.find(10));
	}
	
	@Test
	void testDelete() {
		Treap test = new Treap();
		test.add(10, 10);
		test.add(11,11);
		test.delete(11);
		Treap test2 = new Treap();
		test2.add(10, 10);
		assertEquals(test.toString(), test2.toString());
	}
	
	@Test
	void testDeleteAll() {
		Treap test = new Treap();
		test.add(11,11);
		test.delete(11);
		Treap test2 = new Treap();
		assertEquals(test.toString(), "null\n");
	}
	
	@Test
	void testPriority() {
		Treap test = new Treap();
		test.add(11,11);
		test.add(10, 10);
		test.add(9,9);
		
		Treap test2 = new Treap();
		test2.add(10, 10);
		test2.add(9,9);
		test2.add(11,11);
		assertEquals(test.toString(), test2.toString());
	}
	@Test
	void testSeed() {
		Treap test = new Treap(100);
		test.add(9);
		test.add(10);
		test.add(11);
		test.add(12);
		test.add(13);
		
		Treap test2 = new Treap(39);
		test2.add(9);
		test2.add(10);
		test2.add(11);
		test2.add(12);
		test2.add(13);
		assertNotEquals(test.toString(), test2.toString());
	}
	
	@Test
	void testEmptyDelete() {
		Treap test = new Treap();
		test.add(10, 10);
		test.add(11,11);
		test.delete(12);
		Treap test2 = new Treap();
		test2.add(10, 10);
		test2.add(11,11);
		assertEquals(test.toString(), test2.toString());
	}
}
