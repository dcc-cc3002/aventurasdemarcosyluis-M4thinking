import com.example.aventurasdemarcoyluis.controller.linkedlist.ITurnList;
import com.example.aventurasdemarcoyluis.controller.linkedlist.TurnList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class turnListTest {
	private ITurnList l;
	@BeforeEach
	public void setUp() throws Exception {
		l = new TurnList();
	}
	@Test
	public void testDefault() {
		assertTrue(l.isEmpty());
		assertEquals(0, l.size());
	}
	@Test
	public void adding() {
		l.addFirst(42);
		l.addFirst("Happy World");
		assertEquals(2, l.size());
		assertTrue(l.includes("Happy World"));
		assertTrue(l.includes(42));
		assertFalse(l.includes(43));
	}

	@Test
	public void addAndRemove() {
		// ------------  //Index 9 X
		l.addFirst(3);//Index 8 XX
		l.addFirst(2);
		l.addFirst(4);
		l.addFirst(5);
		l.addFirst(7);//Index 4 XXX
		l.addFirst(8);
		l.addFirst(8);
		l.addFirst(10);//Index 1

		assertEquals(8, l.size());
		System.out.println(l.print());

		l.remove(9);
		assertTrue(l.includes(10));

	}
}
