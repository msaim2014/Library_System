package junitTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controllers.Book;

class JUnitTestBook {
	
	Book b = new Book("testISBN", "testTITLE", "testAUTHOR", "testGENRE", "testAVAILABILITY");

	@Test
	void testISBN() {
		assertEquals("testISBN", b.getIsbn());
	}
	
	@Test
	void testTitle() {
		assertEquals("testTITLE", b.getTitle());
	}
	
	@Test
	void testAuthor() {
		assertEquals("testAUTHOR", b.getAuthor());
	}
	
	@Test
	void testGenre() {
		assertEquals("testGENRE", b.getGenre());
	}
	
	@Test
	void testAvailability() {
		assertEquals("testAVAILABILITY", b.getAvailability());
	}

}
